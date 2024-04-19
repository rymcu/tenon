package com.rymcu.tenon.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rymcu.tenon.core.result.GlobalResult;
import com.rymcu.tenon.core.result.GlobalResultGenerator;
import com.rymcu.tenon.model.UserInfo;
import com.rymcu.tenon.model.UserSearch;
import com.rymcu.tenon.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created on 2024/4/19 8:44.
 *
 * @author ronger
 * @email ronger-x@outlook.com
 * @desc : com.rymcu.tenon.web
 */
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Resource
    private UserService userService;

    @GetMapping("/users")
    public GlobalResult<PageInfo<UserInfo>> users(UserSearch search) {
        PageHelper.startPage(search.getPageNum(), search.getPageSize());
        List<UserInfo> list = userService.findUsers(search);
        PageInfo<UserInfo> pageInfo = new PageInfo<>(list);
        return GlobalResultGenerator.genSuccessResult(pageInfo);
    }


}
