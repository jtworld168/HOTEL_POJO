package com.hotel.store.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.hotel.store.entity.User;
import com.hotel.store.mapper.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class SaTokenConfig implements WebMvcConfigurer {
    
    @Resource
    private UserMapper userMapper;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor(handle -> {
            SaRouter
                .match("/**")
                .notMatch("/api/user/login")
                .notMatch("/api/user/register")
                .notMatch("/api/product/list")
                .notMatch("/api/product/{id}")
                .notMatch("/api/product/barcode/{barcode}")
                .notMatch("/api/banner/active")
                .notMatch("/doc.html")
                .notMatch("/swagger-ui/**")
                .notMatch("/v3/api-docs/**")
                .notMatch("/webjars/**")
                .notMatch("/favicon.ico")
                .check(r -> StpUtil.checkLogin());
        })).addPathPatterns("/**");
    }
    
    @Bean
    public StpInterface stpInterface() {
        return new StpInterface() {
            @Override
            public List<String> getPermissionList(Object loginId, String loginType) {
                return new ArrayList<>();
            }
            
            @Override
            public List<String> getRoleList(Object loginId, String loginType) {
                List<String> roles = new ArrayList<>();
                User user = userMapper.selectById(Long.parseLong(loginId.toString()));
                if (user != null && user.getRole() != null) {
                    roles.add(user.getRole());
                }
                return roles;
            }
        };
    }
}
