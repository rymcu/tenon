package com.rymcu.tenon.util;

import com.rymcu.tenon.auth.JwtConstants;
import com.rymcu.tenon.auth.JwtUtils;
import com.rymcu.tenon.auth.TokenManager;
import com.rymcu.tenon.auth.TokenModel;
import com.rymcu.tenon.model.TokenUser;
import com.rymcu.tenon.entity.User;
import com.rymcu.tenon.mapper.UserMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthenticatedException;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Created on 2024/4/13 15:28.
 *
 * @author ronger
 * @email ronger-x@outlook.com
 * @desc : com.rymcu.tenon.util
 */
public class UserUtils {

    private static final UserMapper userMapper = SpringContextHolder.getBean(UserMapper.class);

    /**
     * 通过token获取当前用户的信息
     *
     * @return User
     */
    public static User getCurrentUserByToken() {
        String authHeader = ContextHolderUtils.getRequest().getHeader(JwtConstants.AUTHORIZATION);
        TokenModel tokenModel = JwtUtils.getTokenModel(authHeader);
        User user = userMapper.selectByAccount(tokenModel.getUsername());
        if (Objects.nonNull(user)) {
            return user;
        }
        throw new UnauthenticatedException();
    }

    public static TokenUser getTokenUser(String token) {
        TokenModel tokenModel = JwtUtils.getTokenModel(token);
        User user = userMapper.selectByAccount(tokenModel.getUsername());
        if (Objects.nonNull(user)) {
            TokenUser tokenUser = new TokenUser();
            BeanCopierUtil.copy(user, tokenUser);
            tokenUser.setAccount(user.getEmail());
            tokenUser.setToken(token);
            return tokenUser;
        }
        throw new UnauthenticatedException();
    }
}
