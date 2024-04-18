package com.rymcu.tenon.web;

import com.alibaba.fastjson2.JSONObject;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.starter.annotation.LogRecord;
import com.rymcu.tenon.auth.TokenManager;
import com.rymcu.tenon.core.exception.AccountExistsException;
import com.rymcu.tenon.core.exception.ServiceException;
import com.rymcu.tenon.core.result.GlobalResult;
import com.rymcu.tenon.core.result.GlobalResultGenerator;
import com.rymcu.tenon.core.result.GlobalResultMessage;
import com.rymcu.tenon.model.Link;
import com.rymcu.tenon.model.TokenUser;
import com.rymcu.tenon.entity.User;
import com.rymcu.tenon.model.UserInfo;
import com.rymcu.tenon.service.JavaMailService;
import com.rymcu.tenon.service.MenuService;
import com.rymcu.tenon.service.UserService;
import com.rymcu.tenon.util.BeanCopierUtil;
import com.rymcu.tenon.util.UserUtils;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author ronger
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Resource
    private MenuService menuService;
    @Resource
    private UserService userService;
    @Resource
    TokenManager tokenManager;
    @Resource
    private JavaMailService javaMailService;



    @GetMapping("/get-email-code")
    public GlobalResult<Map<String, String>> getEmailCode(@RequestParam("email") String email) throws MessagingException {
        Map<String, String> map = new HashMap<>(1);
        map.put("message", GlobalResultMessage.SEND_SUCCESS.getMessage());
        User user = userService.findByAccount(email);
        if (user != null) {
            throw new AccountExistsException("该邮箱已被注册!");
        } else {
            Integer result = javaMailService.sendEmailCode(email);
            if (result == 0) {
                map.put("message", GlobalResultMessage.SEND_FAIL.getMessage());
            }
        }
        return GlobalResultGenerator.genSuccessResult(map);
    }

    @GetMapping("/get-forget-password-email")
    public GlobalResult getForgetPasswordEmail(@RequestParam("email") String email) throws MessagingException, ServiceException {
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

    @GetMapping("/menus")
    public GlobalResult<List<Link>> menus() {
        User user = UserUtils.getCurrentUserByToken();
        if (Objects.isNull(user)) {
            throw new UnauthenticatedException();
        }
        List<Link> menus = menuService.findLinksByIdUser(user.getIdUser());
        return GlobalResultGenerator.genSuccessResult(menus);
    }

    @PostMapping("/login")
    @LogRecord(success = "提交成功", type = "系统", subType = "账号登录", bizNo = "{\"account\": {{#user.account}}}",
            fail = "提交失败，失败原因：「{{#_errorMsg ? #_errorMsg : #result.message }}」", extra = "{\"account\": {{#user.account}}}",
            successCondition = "{{#result.code==200}}")
    public GlobalResult<TokenUser> login(@RequestBody User user) {
        TokenUser tokenUser = userService.login(user.getAccount(), user.getPassword());
        LogRecordContext.putVariable("idUser", tokenUser.getIdUser());
        tokenUser.setIdUser(null);
        GlobalResult<TokenUser> tokenUserGlobalResult = GlobalResultGenerator.genSuccessResult(tokenUser);
        LogRecordContext.putVariable("result", tokenUserGlobalResult);
        return tokenUserGlobalResult;
    }

    @PostMapping("/refresh-token")
    public GlobalResult<TokenUser> refreshToken(@RequestBody TokenUser tokenUser) {
        tokenUser = userService.refreshToken(tokenUser.getRefreshToken());
        return GlobalResultGenerator.genSuccessResult(tokenUser);
    }

    @PostMapping("/logout")
    public GlobalResult logout() {
        User user = UserUtils.getCurrentUserByToken();
        if (Objects.nonNull(user)) {
            tokenManager.deleteToken(user.getAccount());
        }
        return GlobalResultGenerator.genSuccessResult();
    }

    @GetMapping("/user")
    public GlobalResult<JSONObject> user() {
        User user = UserUtils.getCurrentUserByToken();
        UserInfo userInfo = new UserInfo();
        BeanCopierUtil.copy(user, userInfo);
        userInfo.setScope(userService.findUserPermissionsByIdUser(user.getIdUser()));
        userInfo.setRole(userService.findUserRoleListByIdUser(user.getIdUser()));
        userInfo.setLinks(menuService.findLinksByIdUser(user.getIdUser()));
        JSONObject object = new JSONObject();
        object.put("user", userInfo);
        return GlobalResultGenerator.genSuccessResult(object);
    }

}
