package com.rymcu.tenon.service;

import jakarta.mail.MessagingException;

/**
 * Created on 2024/4/17 22:58.
 *
 * @author ronger
 * @email ronger-x@outlook.com
 * @desc : com.rymcu.tenon.service
 */
public interface JavaMailService {

    /**
     * 发送验证码邮件
     *
     * @param email 收件人邮箱
     * @return 执行结果 0：失败1：成功
     * @throws MessagingException
     */
    Integer sendEmailCode(String email) throws MessagingException;

    /**
     * 发送找回密码邮件
     *
     * @param email 收件人邮箱
     * @return 执行结果 0：失败1：成功
     * @throws MessagingException
     */
    Integer sendForgetPasswordEmail(String email) throws MessagingException;

    /**
     * 发送用户初始密码邮件
     *
     * @param email 收件人邮箱
     * @return 执行结果 0：失败1：成功
     * @throws MessagingException
     */
    Integer sendInitialPassword(String email, String code) throws MessagingException;
}
