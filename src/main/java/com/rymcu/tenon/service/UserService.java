package com.rymcu.tenon.service;

import com.rymcu.tenon.core.service.Service;
import com.rymcu.tenon.dto.TokenUser;
import com.rymcu.tenon.entity.User;

/**
 * Created on 2024/4/13 21:25.
 *
 * @author ronger
 * @email ronger-x@outlook.com
 * @desc : com.rymcu.tenon.service
 */
public interface UserService extends Service<User> {

    int updateLastOnlineTimeByAccount(String account);

    /**
     * 注册接口
     *
     * @param email    邮箱
     * @param password 密码
     * @param code     验证码
     * @return Map
     */
    Boolean register(String email, String password, String code);

    /**
     * 登录接口
     *
     * @param account  邮箱
     * @param password 密码
     * @return Map
     */
    TokenUser login(String account, String password);
}
