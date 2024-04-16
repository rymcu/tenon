package com.rymcu.tenon.service.impl;

import com.github.f4b6a3.ulid.UlidCreator;
import com.rymcu.tenon.auth.JwtConstants;
import com.rymcu.tenon.auth.TokenManager;
import com.rymcu.tenon.core.exception.AccountExistsException;
import com.rymcu.tenon.core.exception.CaptchaException;
import com.rymcu.tenon.core.service.AbstractService;
import com.rymcu.tenon.dto.TokenUser;
import com.rymcu.tenon.entity.Role;
import com.rymcu.tenon.entity.User;
import com.rymcu.tenon.mapper.RoleMapper;
import com.rymcu.tenon.mapper.UserMapper;
import com.rymcu.tenon.service.UserService;
import com.rymcu.tenon.util.Utils;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
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
    @Autowired
    private StringRedisTemplate redisTemplate;

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
                    user.setCreatedTime(new Date());
                    user.setAvatar(DEFAULT_AVATAR);
                    userMapper.insertSelective(user);
                    user = userMapper.selectByAccount(email);
                    Role role = roleMapper.selectRoleByPermission("user");
                    userMapper.insertUserRole(user.getIdUser(), role.getIdRole());
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
    public TokenUser login(String account, String password) {
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
        throw new AuthorizationException();
    }
}
