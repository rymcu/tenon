package com.rymcu.tenon.service;

import com.rymcu.tenon.core.service.Service;
import com.rymcu.tenon.model.TokenUser;
import com.rymcu.tenon.entity.User;

import java.util.Set;

/**
 * Created on 2024/4/13 21:25.
 *
 * @author ronger
 * @email ronger-x@outlook.com
 * @desc : com.rymcu.tenon.service
 */
public interface UserService extends Service<User> {

    /**
     * @param account 用户账号
     * @return 更新成功数量
     */
    int updateLastOnlineTimeByAccount(String account);

    /**
     * 注册接口
     *
     * @param email    邮箱
     * @param password 密码
     * @param code     验证码
     * @return Boolean 注册成功标志
     */
    Boolean register(String email, String password, String code);

    /**
     * 登录接口
     *
     * @param account  邮箱
     * @param password 密码
     * @return TokenUser
     */
    TokenUser login(String account, String password);

    TokenUser refreshToken(String refreshToken);

    Set<String> findUserPermissionsByIdUser(Long idUser);

    Set<String> findUserRoleListByIdUser(Long idUser);

    User findByAccount(String account);
}
