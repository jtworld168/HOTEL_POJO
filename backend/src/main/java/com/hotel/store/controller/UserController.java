package com.hotel.store.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.hotel.store.common.Result;
import com.hotel.store.dto.*;
import com.hotel.store.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

@Tag(name = "用户管理")
@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Resource
    private UserService userService;
    
    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = userService.login(request);
        return Result.success(response);
    }
    
    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Result<Void> register(@Valid @RequestBody RegisterRequest request) {
        userService.register(request);
        return Result.success();
    }
    
    @Operation(summary = "获取用户信息")
    @GetMapping("/info")
    public Result<UserVO> getUserInfo() {
        Long userId = StpUtil.getLoginIdAsLong();
        UserVO userInfo = userService.getUserInfo(userId);
        return Result.success(userInfo);
    }
    
    @Operation(summary = "更新用户信息")
    @PutMapping("/info")
    public Result<Void> updateUser(@Valid @RequestBody UpdateUserRequest request) {
        Long userId = StpUtil.getLoginIdAsLong();
        userService.updateUser(userId, request);
        return Result.success();
    }
    
    @Operation(summary = "退出登录")
    @PostMapping("/logout")
    public Result<Void> logout() {
        StpUtil.logout();
        return Result.success();
    }
}
