package com.rymcu.tenon.web;

import com.rymcu.tenon.core.result.GlobalResult;
import com.rymcu.tenon.core.result.GlobalResultGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 2024/4/25 10:48.
 *
 * @author ronger
 * @email ronger-x@outlook.com
 * @desc : com.rymcu.tenon.web
 */
@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {

    @GetMapping("/all")
    public GlobalResult all() {
        return GlobalResultGenerator.genSuccessResult();
    }

}
