package com.hotel.store.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.hotel.store.common.Result;
import com.hotel.store.dto.AddCartRequest;
import com.hotel.store.dto.CartItemVO;
import com.hotel.store.dto.UpdateCartRequest;
import com.hotel.store.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

@Tag(name = "购物车管理")
@RestController
@RequestMapping("/api/cart")
public class CartController {
    
    @Resource
    private CartService cartService;
    
    @Operation(summary = "添加购物车")
    @PostMapping
    public Result<Void> add(@Valid @RequestBody AddCartRequest request) {
        Long userId = StpUtil.getLoginIdAsLong();
        cartService.add(userId, request);
        return Result.success();
    }
    
    @Operation(summary = "更新购物车")
    @PutMapping("/{cartId}")
    public Result<Void> update(@PathVariable Long cartId, @Valid @RequestBody UpdateCartRequest request) {
        Long userId = StpUtil.getLoginIdAsLong();
        cartService.update(userId, cartId, request);
        return Result.success();
    }
    
    @Operation(summary = "删除购物车项")
    @DeleteMapping("/{cartId}")
    public Result<Void> delete(@PathVariable Long cartId) {
        Long userId = StpUtil.getLoginIdAsLong();
        cartService.delete(userId, cartId);
        return Result.success();
    }
    
    @Operation(summary = "获取购物车列表")
    @GetMapping("/list")
    public Result<List<CartItemVO>> list() {
        Long userId = StpUtil.getLoginIdAsLong();
        List<CartItemVO> result = cartService.list(userId);
        return Result.success(result);
    }
    
    @Operation(summary = "清空购物车")
    @DeleteMapping("/clear")
    public Result<Void> clear() {
        Long userId = StpUtil.getLoginIdAsLong();
        cartService.clear(userId);
        return Result.success();
    }
}
