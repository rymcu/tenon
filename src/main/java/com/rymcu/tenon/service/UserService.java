package com.rymcu.tenon.service;

import com.rymcu.tenon.core.service.Service;
import com.rymcu.tenon.entity.User;
import com.rymcu.tenon.model.BindUserRoleInfo;
import com.rymcu.tenon.model.TokenUser;
import com.rymcu.tenon.model.UserInfo;
import com.rymcu.tenon.model.UserSearch;

import java.util.List;
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
     * @param nickname 昵称
     * @param password 密码
     * @param code     验证码
     * @return Boolean 注册成功标志
     */
    Boolean register(String email, String nickname, String password, String code);

    /**
     * 登录接口
     *
     * @param account  邮箱
     * @param password 密码
     * @return TokenUser
     */
    TokenUser login(String account, String password);

    /**
     * 刷新 token 接口
     *
     * @param refreshToken 刷新 token
     * @return TokenUser
     */
    TokenUser refreshToken(String refreshToken);

    /**
     * 查询用户菜单权限
     *
     * @param idUser 用户 ID
     * @return 菜单权限
     */
    Set<String> findUserPermissionsByIdUser(Long idUser);

    /**
     * 获取用户角色
     *
     * @param idUser 用户 ID
     * @return 角色
     */
    Set<String> findUserRoleListByIdUser(Long idUser);

    /**
     * 查询用户信息
     *
     * @param account 账号
     * @return 用户信息
     */
    User findByAccount(String account);

    /**
     * 查询用户
     *
     * @param search 查询条件
     * @return 用户信息列表
     */
    List<UserInfo> findUsers(UserSearch search);

    Boolean forgetPassword(String code, String password);

    UserInfo findUserInfoById(Long idUser);

    Boolean saveUser(UserInfo userInfo);

    Boolean updateUserInfo(UserInfo userInfo);

    Boolean bindUserRole(BindUserRoleInfo bindUserRoleInfo);

    Boolean updateStatus(Long idUser, Integer status);

    String resetPassword(Long idUser);

    Boolean updateDelFlag(Long idUser, Integer delFlag);
}
