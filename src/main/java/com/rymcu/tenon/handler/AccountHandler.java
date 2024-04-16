package com.rymcu.tenon.handler;

import com.rymcu.tenon.handler.event.AccountEvent;
import com.rymcu.tenon.mapper.UserMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

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

    @TransactionalEventListener
    public void processAccountLastOnlineTimeEvent(AccountEvent accountEvent) {
        userMapper.updateLastOnlineTimeByAccount(accountEvent.getAccount());
    }

}
