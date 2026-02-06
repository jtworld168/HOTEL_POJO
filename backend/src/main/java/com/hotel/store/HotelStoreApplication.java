package com.hotel.store;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 酒店无人售货超市主启动类
 */
@SpringBootApplication
@MapperScan("com.hotel.store.mapper")
public class HotelStoreApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(HotelStoreApplication.class, args);
        System.out.println("=================================");
        System.out.println("酒店无人售货超市系统启动成功！");
        System.out.println("接口文档地址: http://localhost:8080/doc.html");
        System.out.println("=================================");
    }
}
