package com.rymcu.tenon.web;

import com.rymcu.tenon.core.exception.AccountExistsException;
import com.rymcu.tenon.core.exception.ServiceException;
import com.rymcu.tenon.core.result.GlobalResult;
import com.rymcu.tenon.core.result.GlobalResultGenerator;
import com.rymcu.tenon.core.result.GlobalResultMessage;
import com.rymcu.tenon.entity.User;
import com.rymcu.tenon.service.JavaMailService;
import com.rymcu.tenon.service.UserService;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2024/4/18 18:43.
 *
 * @author ronger
 * @email ronger-x@outlook.com
 * @desc : com.rymcu.tenon.web
 */
@RestController
@RequestMapping("/api/v1/common")
public class CommonController {

    @Resource
    private JavaMailService javaMailService;
    @Resource
    private UserService userService;

    @GetMapping("/get-email-code")
    public GlobalResult<String> getEmailCode(@RequestParam("email") String email) throws MessagingException {
        User user = userService.findByAccount(email);
        if (user != null) {
            throw new AccountExistsException("该邮箱已被注册!");
        } else {
            Integer result = javaMailService.sendEmailCode(email);
            if (result == 0) {
               return GlobalResultGenerator.genErrorResult(GlobalResultMessage.SEND_FAIL.getMessage());
            }
        }
        return GlobalResultGenerator.genSuccessResult(GlobalResultMessage.SEND_SUCCESS.getMessage());
    }

    @GetMapping("/get-forget-password-email")
    public GlobalResult<String> getForgetPasswordEmail(@RequestParam("email") String email) throws MessagingException, ServiceException {
        User user = userService.findByAccount(email);
        if (user != null) {
            Integer result = javaMailService.sendForgetPasswordEmail(email);
            if (result == 0) {
                throw new ServiceException(GlobalResultMessage.SEND_FAIL.getMessage());
            }
        } else {
            throw new UnknownAccountException("未知账号");
        }
        return GlobalResultGenerator.genSuccessResult(GlobalResultMessage.SEND_SUCCESS.getMessage());
    }
}