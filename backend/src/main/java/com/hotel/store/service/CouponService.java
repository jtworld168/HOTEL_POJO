package com.hotel.store.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotel.store.dto.UserCouponVO;
import com.hotel.store.entity.Coupon;

public interface CouponService {
    
    /**
     * 优惠券列表
     */
    Page<Coupon> list(Integer page, Integer size, Integer status);
    
    /**
     * 创建优惠券
     */
    void create(Coupon coupon);
    
    /**
     * 领取优惠券
     */
    void claimCoupon(Long userId, Long couponId);
    
    /**
     * 用户优惠券列表
     */
    Page<UserCouponVO> listUserCoupons(Long userId, Integer page, Integer size, String status);
}
