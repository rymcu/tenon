package com.rymcu.tenon.handler;

import com.rymcu.tenon.handler.event.AccountEvent;
import com.rymcu.tenon.mapper.UserMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created on 2022/8/24 14:44.
 *
 * @author ronger
 * @email ronger-x@outlook.com
 * @packageName com.rymcu.tenon.handler
 */
@Slf4j
@Component
public class AccountHandler {

    @Resource
    private UserMapper userMapper;

    @Async
    @EventListener
    public void processAccountLastOnlineTimeEvent(AccountEvent accountEvent) {
        userMapper.updateLastOnlineTimeByAccount(accountEvent.getAccount());
    }

}
