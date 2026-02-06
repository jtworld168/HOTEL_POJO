package com.hotel.store.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig {
    
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("酒店大堂无人售货超市系统API")
                        .description("酒店大堂无人售货超市系统接口文档")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Hotel Store")
                                .email("support@hotelstore.com")))
                .addSecurityItem(new SecurityRequirement().addList("SaToken"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("SaToken",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .in(SecurityScheme.In.HEADER)
                                        .name("satoken")));
    }
    
    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("用户模块")
                .pathsToMatch("/api/user/**")
                .build();
    }
    
    @Bean
    public GroupedOpenApi productApi() {
        return GroupedOpenApi.builder()
                .group("商品模块")
                .pathsToMatch("/api/product/**")
                .build();
    }
    
    @Bean
    public GroupedOpenApi cartApi() {
        return GroupedOpenApi.builder()
                .group("购物车模块")
                .pathsToMatch("/api/cart/**")
                .build();
    }
    
    @Bean
    public GroupedOpenApi orderApi() {
        return GroupedOpenApi.builder()
                .group("订单模块")
                .pathsToMatch("/api/order/**")
                .build();
    }
    
    @Bean
    public GroupedOpenApi couponApi() {
        return GroupedOpenApi.builder()
                .group("优惠券模块")
                .pathsToMatch("/api/coupon/**")
                .build();
    }
    
    @Bean
    public GroupedOpenApi bannerApi() {
        return GroupedOpenApi.builder()
                .group("轮播图模块")
                .pathsToMatch("/api/banner/**")
                .build();
    }
}
