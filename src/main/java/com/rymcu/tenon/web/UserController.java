package com.rymcu.tenon.web;

import com.rymcu.tenon.core.result.GlobalResult;
import com.rymcu.tenon.core.result.GlobalResultGenerator;
import com.rymcu.tenon.entity.User;
import com.rymcu.tenon.model.UserInfo;
import com.rymcu.tenon.service.UserService;
import com.rymcu.tenon.util.UserUtils;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * Created on 2024/5/3 12:06.
 *
 * @author ronger
 * @email ronger-x@outlook.com
 * @desc : com.rymcu.tenon.web
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/info")
    public GlobalResult<UserInfo> userInfo() {
        User user = UserUtils.getCurrentUserByToken();
        return GlobalResultGenerator.genSuccessResult(userService.findUserInfoById(user.getIdUser()));
    }

    @PostMapping("/update-info")
    public GlobalResult<Boolean> updateInfo(@RequestBody UserInfo userInfo) {
        User user = UserUtils.getCurrentUserByToken();
        userInfo.setIdUser(user.getIdUser());
        return GlobalResultGenerator.genSuccessResult(userService.updateUserInfo(userInfo));
    }
}
