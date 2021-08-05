package com.lsz.mall.portal.controller;

import com.lsz.mall.base.entity.Member;
import com.lsz.mall.base.entity.Res;
import com.lsz.mall.base.param.UpdateUserInfoParam;
import com.lsz.mall.base.param.UserInfoParam;
import com.lsz.mall.base.param.UserLoginParam;
import com.lsz.mall.base.param.UserRegisterParam;
import com.lsz.mall.portal.service.UserService;
import com.lsz.mall.portal.util.JwtTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@Api(tags = "商城用户管理")
@RequestMapping("/api/v1")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;


    @PostMapping("/user/login")
    @ApiOperation(value = "登录接口")
    public Res<String> login(@RequestBody @Valid UserRegisterParam userLoginParam) {

        String token = userService.login(userLoginParam);
        String wholeToken = jwtTokenUtil.addTokenHead(token);
        return Res.ok(wholeToken);
    }


    @PostMapping("/user/logout")
    @ApiOperation(value = "登出接口")
    public Res<String> logout(UserRegisterParam userLoginParam) {

        Boolean logoutRes = userService.logout(userLoginParam);

        return Res.ok();
    }


    @PostMapping("/user/register")
    @ApiOperation(value = "用户注册")
    public Res register(@RequestBody @Valid UserRegisterParam userRegisterParam) {

        String registerRes = userService.register(userRegisterParam);

        return Res.ok(registerRes);
    }

    @PutMapping("/user/info")
    @ApiOperation(value = "修改用户信息")
    public Res updateInfo(@RequestBody UpdateUserInfoParam userInfoParam) {
        int success = userService.updateInfo(userInfoParam);
        return Res.ok(success);
    }

    @GetMapping("/user/info")
    @ApiOperation(value = "获取用户信息")
    public Res<UserInfoParam> getUserDetail() {
        Member currentMember = userService.getCurrentMember();
        log.debug("currentMember = {}", currentMember);
        return Optional.ofNullable(currentMember).map(m -> new UserInfoParam(m))
                .map(u -> Res.ok(u))
                .orElseGet(() -> Res.error("请先登录！", 401));
    }


}
