package com.hotel.store.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotel.store.common.Result;
import com.hotel.store.dto.UserCouponVO;
import com.hotel.store.entity.Coupon;
import com.hotel.store.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

@Tag(name = "优惠券管理")
@RestController
@RequestMapping("/api/coupon")
public class CouponController {
    
    @Resource
    private CouponService couponService;
    
    @Operation(summary = "优惠券列表")
    @GetMapping("/list")
    public Result<Page<Coupon>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Integer status) {
        Page<Coupon> result = couponService.list(page, size, status);
        return Result.success(result);
    }
    
    @Operation(summary = "创建优惠券")
    @SaCheckRole("admin")
    @PostMapping
    public Result<Void> create(@Valid @RequestBody Coupon coupon) {
        couponService.create(coupon);
        return Result.success();
    }
    
    @Operation(summary = "领取优惠券")
    @PostMapping("/{couponId}/claim")
    public Result<Void> claim(@PathVariable Long couponId) {
        Long userId = StpUtil.getLoginIdAsLong();
        couponService.claimCoupon(userId, couponId);
        return Result.success();
    }
    
    @Operation(summary = "用户优惠券列表")
    @GetMapping("/my")
    public Result<Page<UserCouponVO>> listUserCoupons(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String status) {
        Long userId = StpUtil.getLoginIdAsLong();
        Page<UserCouponVO> result = couponService.listUserCoupons(userId, page, size, status);
        return Result.success(result);
    }
}
