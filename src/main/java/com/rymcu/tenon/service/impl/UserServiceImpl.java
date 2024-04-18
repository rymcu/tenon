package com.rymcu.tenon.service.impl;

import com.github.f4b6a3.ulid.UlidCreator;
import com.rymcu.tenon.auth.JwtConstants;
import com.rymcu.tenon.auth.TokenManager;
import com.rymcu.tenon.core.exception.AccountExistsException;
import com.rymcu.tenon.core.exception.CaptchaException;
import com.rymcu.tenon.core.service.AbstractService;
import com.rymcu.tenon.entity.Menu;
import com.rymcu.tenon.handler.event.RegisterEvent;
import com.rymcu.tenon.mapper.MenuMapper;
import com.rymcu.tenon.model.TokenUser;
import com.rymcu.tenon.entity.Role;
import com.rymcu.tenon.entity.User;
import com.rymcu.tenon.mapper.RoleMapper;
import com.rymcu.tenon.mapper.UserMapper;
import com.rymcu.tenon.service.UserService;
import com.rymcu.tenon.util.Utils;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2024/4/13 21:25.
 *
 * @author ronger
 * @email ronger-x@outlook.com
 * @desc : com.rymcu.tenon.service.impl
 */
@Service
public class UserServiceImpl extends AbstractService<User> implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private TokenManager tokenManager;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private MenuMapper menuMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    private final static String DEFAULT_AVATAR = "https://static.rymcu.com/article/1578475481946.png";
    private final static String DEFAULT_ACCOUNT = "1411780000";
    private final static String CURRENT_ACCOUNT_KEY = "current:account";

    @Override
    public int updateLastOnlineTimeByAccount(String account) {
        return userMapper.updateLastOnlineTimeByAccount(account);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean register(String email, String password, String code) {
        String validateCode = redisTemplate.boundValueOps(email).get();
        if (StringUtils.isNotBlank(validateCode)) {
            if (validateCode.equals(code)) {
                User user = userMapper.selectByAccount(email);
                if (user != null) {
                    throw new AccountExistsException("该邮箱已被注册！");
                } else {
                    user = new User();
                    String nickname = email.split("@")[0];
                    user.setNickname(checkNickname(nickname));
                    user.setAccount(nextAccount());
                    user.setEmail(email);
                    user.setPassword(Utils.encryptPassword(password));
                    user.setAvatar(DEFAULT_AVATAR);
                    userMapper.insertSelective(user);
                    // 注册成功后执行相关初始化事件
                    applicationEventPublisher.publishEvent(new RegisterEvent(user.getIdUser(), user.getAccount()));
                    redisTemplate.delete(email);
                    return true;
                }
            }
        }
        throw new CaptchaException();
    }


    private String checkNickname(String nickname) {
        nickname = formatNickname(nickname);
        Integer result = userMapper.selectCountByNickname(nickname);
        if (result > 0) {
            StringBuilder stringBuilder = new StringBuilder(nickname);
            return checkNickname(stringBuilder.append("_").append(System.currentTimeMillis()).toString());
        }
        return nickname;
    }

    private String formatNickname(String nickname) {
        return nickname.replaceAll("\\.", "");
    }

    private String nextAccount() {
        // 获取当前账号
        String currentAccount = redisTemplate.boundValueOps(CURRENT_ACCOUNT_KEY).get();
        BigDecimal account;
        if (StringUtils.isNotBlank(currentAccount)) {
            account = BigDecimal.valueOf(Long.parseLong(currentAccount));
        } else {
            // 查询数据库
            currentAccount = userMapper.selectMaxAccount();
            if (StringUtils.isNotBlank(currentAccount)) {
                account = BigDecimal.valueOf(Long.parseLong(currentAccount));
            } else {
                account = BigDecimal.valueOf(Long.parseLong(DEFAULT_ACCOUNT));
            }
        }
        currentAccount = account.add(BigDecimal.ONE).toString();
        redisTemplate.boundValueOps(CURRENT_ACCOUNT_KEY).set(currentAccount);
        return currentAccount;
    }

    @Override
    public TokenUser login(@NotBlank String account, @NotBlank String password) {
        User user = userMapper.selectByAccount(account);
        if (Objects.nonNull(user)) {
            if (Utils.comparePassword(password, user.getPassword())) {
                userMapper.updateLastLoginTime(user.getIdUser());
                userMapper.updateLastOnlineTimeByAccount(user.getAccount());
                TokenUser tokenUser = new TokenUser();
                tokenUser.setToken(tokenManager.createToken(user.getAccount()));
                tokenUser.setRefreshToken(UlidCreator.getUlid().toString());
                redisTemplate.boundValueOps(tokenUser.getRefreshToken()).set(account, JwtConstants.REFRESH_TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
                // 保存登录日志
//                loginRecordService.saveLoginRecord(user.getIdUser());
                return tokenUser;
            }
        }
        throw new UnknownAccountException();
    }

    @Override
    public TokenUser refreshToken(String refreshToken) {
        return null;
    }

    @Override
    public Set<String> findUserPermissionsByIdUser(Long idUser) {
        Set<String> permissions = new HashSet<>();
        List<Menu> menus = menuMapper.selectMenuListByIdUser(idUser);
        for (Menu menu : menus) {
            if (StringUtils.isNotBlank(menu.getPermission())) {
                permissions.add(menu.getPermission());
            }
        }
        permissions.add("user");
        return permissions;
    }

    @Override
    public Set<String> findUserRoleListByIdUser(Long idUser) {
        List<Role> roles = roleMapper.selectRolesByIdUser(idUser);
        Set<String> permissions = new HashSet<>();
        for (Role role : roles) {
            if (StringUtils.isNotBlank(role.getPermission())) {
                permissions.add(role.getPermission());
            }
        }
        return permissions;
    }

    @Override
    public User findByAccount(String account) {
        return userMapper.selectByAccount(account);
    }
}
