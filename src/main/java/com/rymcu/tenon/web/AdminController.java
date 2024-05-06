package com.rymcu.tenon.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rymcu.tenon.core.result.GlobalResult;
import com.rymcu.tenon.core.result.GlobalResultGenerator;
import com.rymcu.tenon.entity.Menu;
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

    @PostMapping("/user/bind-role")
    public GlobalResult<Boolean> bindUserRole(@RequestBody BindUserRoleInfo bindUserRoleInfo) {
        return GlobalResultGenerator.genSuccessResult(userService.bindUserRole(bindUserRoleInfo));
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

    @GetMapping("/role/bind-menu")
    public GlobalResult<Boolean> bindRoleMenu(@RequestBody BindRoleMenuInfo bindRoleMenuInfo) {
        return GlobalResultGenerator.genSuccessResult(roleService.bindRoleMenu(bindRoleMenuInfo));
    }

    @GetMapping("/menus")
    public GlobalResult<List<Link>> menus(MenuSearch search) {
        List<Link> list = menuService.findMenus(search);
        return GlobalResultGenerator.genSuccessResult(list);
    }

    @GetMapping("/children-menus")
    public GlobalResult<PageInfo<Link>> childrenMenus(MenuSearch search) {
        PageHelper.startPage(search.getPageNum(), search.getPageSize());
        List<Link> list = menuService.findChildrenMenus(search);
        PageInfo<Link> pageInfo = new PageInfo<>(list);
        return GlobalResultGenerator.genSuccessResult(pageInfo);
    }

    @GetMapping("/menu/{idMenu}")
    public GlobalResult<Menu> menu(@PathVariable Long idMenu) {
        return GlobalResultGenerator.genSuccessResult(menuService.findById(String.valueOf(idMenu)));
    }

    @PostMapping("/menu/post")
    public GlobalResult<Boolean> postMenu(@RequestBody Menu menu) {
        return GlobalResultGenerator.genSuccessResult(menuService.postMenu(menu));
    }


}
