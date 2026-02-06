package com.hotel.store.service;

import com.hotel.store.dto.*;
import com.hotel.store.entity.User;

public interface UserService {
    
    /**
     * 用户登录
     */
    LoginResponse login(LoginRequest request);
    
    /**
     * 用户注册
     */
    void register(RegisterRequest request);
    
    /**
     * 获取用户信息
     */
    UserVO getUserInfo(Long userId);
    
    /**
     * 更新用户信息
     */
    void updateUser(Long userId, UpdateUserRequest request);
    
    /**
     * 根据用户名查询用户
     */
    User getByUsername(String username);
}
