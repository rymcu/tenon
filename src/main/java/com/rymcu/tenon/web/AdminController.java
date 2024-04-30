package com.rymcu.tenon.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rymcu.tenon.core.result.GlobalResult;
import com.rymcu.tenon.core.result.GlobalResultGenerator;
import com.rymcu.tenon.entity.Role;
import com.rymcu.tenon.model.*;
import com.rymcu.tenon.service.MenuService;
import com.rymcu.tenon.service.RoleService;
import com.rymcu.tenon.service.UserService;
import jakarta.annotation.Resource;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

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
@RequiresRoles("admin")
public class AdminController {

    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private MenuService menuService;

    @GetMapping("/users")
    public GlobalResult<PageInfo<UserInfo>> users(UserSearch search) {
        PageHelper.startPage(search.getPageNum(), search.getPageSize());
        List<UserInfo> list = userService.findUsers(search);
        PageInfo<UserInfo> pageInfo = new PageInfo<>(list);
        return GlobalResultGenerator.genSuccessResult(pageInfo);
    }

    @GetMapping("/user/{idUser}")
    public GlobalResult<UserInfo> user(@PathVariable Long idUser) {
        return GlobalResultGenerator.genSuccessResult(userService.findUserInfoById(idUser));
    }

    @PostMapping("/user/post")
    public GlobalResult<Boolean> postUser(@RequestBody UserInfo userInfo) {
        return GlobalResultGenerator.genSuccessResult(userService.postUser(userInfo));
    }

    @GetMapping("/roles")
    public GlobalResult<PageInfo<Role>> roles(RoleSearch search) {
        PageHelper.startPage(search.getPageNum(), search.getPageSize());
        List<Role> list = roleService.findRoles(search);
        PageInfo<Role> pageInfo = new PageInfo<>(list);
        return GlobalResultGenerator.genSuccessResult(pageInfo);
    }

    @GetMapping("/role/{idRole}")
    public GlobalResult<Role> role(@PathVariable Long idRole) {
        return GlobalResultGenerator.genSuccessResult(roleService.findById(String.valueOf(idRole)));
    }

    @PostMapping("/role/post")
    public GlobalResult<Boolean> postRole(@RequestBody Role role) {
        return GlobalResultGenerator.genSuccessResult(roleService.postRole(role));
    }

    @GetMapping("/menus")
    public GlobalResult<List<Link>> menus(MenuSearch search) {
        return GlobalResultGenerator.genSuccessResult(menuService.findMenus(search));
    }


}
