package com.rymcu.tenon.handler;

import com.rymcu.tenon.entity.Role;
import com.rymcu.tenon.handler.event.RegisterEvent;
import com.rymcu.tenon.mapper.RoleMapper;
import com.rymcu.tenon.mapper.UserMapper;
import com.rymcu.tenon.service.JavaMailService;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Created on 2024/4/18 8:10.
 *
 * @author ronger
 * @email ronger-x@outlook.com
 * @desc : com.rymcu.tenon.handler
 */
@Slf4j
@Component
public class RegisterHandler {

    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private JavaMailService javaMailService;

    @TransactionalEventListener
    public void processRegisterEvent(RegisterEvent registerEvent) {
        Role role = roleMapper.selectRoleByPermission("user");
        userMapper.insertUserRole(registerEvent.getIdUser(), role.getIdRole());
        try {
            javaMailService.sendInitialPassword(registerEvent.getAccount(), registerEvent.getCode());
        } catch (MessagingException e) {
            log.error("发送用户初始密码邮件失败！", e);
        }
    }

}
