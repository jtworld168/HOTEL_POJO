package com.hotel.store.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotel.store.common.Result;
import com.hotel.store.dto.CreateOrderRequest;
import com.hotel.store.dto.OrderVO;
import com.hotel.store.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

@Tag(name = "订单管理")
@RestController
@RequestMapping("/api/order")
public class OrderController {
    
    @Resource
    private OrderService orderService;
    
    @Operation(summary = "创建订单")
    @PostMapping
    public Result<OrderVO> create(@Valid @RequestBody CreateOrderRequest request) {
        Long userId = StpUtil.getLoginIdAsLong();
        OrderVO order = orderService.create(userId, request);
        return Result.success(order);
    }
    
    @Operation(summary = "订单列表")
    @GetMapping("/list")
    public Result<Page<OrderVO>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String status) {
        Long userId = StpUtil.getLoginIdAsLong();
        Page<OrderVO> result = orderService.list(userId, page, size, status);
        return Result.success(result);
    }
    
    @Operation(summary = "获取订单详情")
    @GetMapping("/{orderId}")
    public Result<OrderVO> getById(@PathVariable Long orderId) {
        Long userId = StpUtil.getLoginIdAsLong();
        OrderVO order = orderService.getById(userId, orderId);
        return Result.success(order);
    }
    
    @Operation(summary = "取消订单")
    @PostMapping("/{orderId}/cancel")
    public Result<Void> cancel(@PathVariable Long orderId) {
        Long userId = StpUtil.getLoginIdAsLong();
        orderService.cancel(userId, orderId);
        return Result.success();
    }
}
