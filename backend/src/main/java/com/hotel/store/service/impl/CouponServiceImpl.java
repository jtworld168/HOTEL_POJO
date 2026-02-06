package com.hotel.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotel.store.dto.UserCouponVO;
import com.hotel.store.entity.Coupon;
import com.hotel.store.entity.UserCoupon;
import com.hotel.store.mapper.CouponMapper;
import com.hotel.store.mapper.UserCouponMapper;
import com.hotel.store.service.CouponService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class CouponServiceImpl implements CouponService {
    
    @Resource
    private CouponMapper couponMapper;
    
    @Resource
    private UserCouponMapper userCouponMapper;
    
    @Override
    public Page<Coupon> list(Integer page, Integer size, Integer status) {
        Page<Coupon> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Coupon> wrapper = new LambdaQueryWrapper<>();
        
        if (status != null) {
            wrapper.eq(Coupon::getStatus, status);
        }
        
        wrapper.orderByDesc(Coupon::getCreateTime);
        
        return couponMapper.selectPage(pageParam, wrapper);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Coupon coupon) {
        coupon.setRemainCount(coupon.getTotalCount());
        coupon.setStatus(1);
        coupon.setDeleted(0);
        
        couponMapper.insert(coupon);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void claimCoupon(Long userId, Long couponId) {
        Coupon coupon = couponMapper.selectById(couponId);
        if (coupon == null) {
            throw new RuntimeException("优惠券不存在");
        }
        
        if (coupon.getStatus() != 1) {
            throw new RuntimeException("优惠券已下架");
        }
        
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(coupon.getStartTime())) {
            throw new RuntimeException("优惠券未开始");
        }
        
        if (now.isAfter(coupon.getEndTime())) {
            throw new RuntimeException("优惠券已过期");
        }
        
        if (coupon.getRemainCount() <= 0) {
            throw new RuntimeException("优惠券已被领完");
        }
        
        LambdaQueryWrapper<UserCoupon> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserCoupon::getUserId, userId);
        wrapper.eq(UserCoupon::getCouponId, couponId);
        
        UserCoupon existUserCoupon = userCouponMapper.selectOne(wrapper);
        if (existUserCoupon != null) {
            throw new RuntimeException("已经领取过该优惠券");
        }
        
        int updated = couponMapper.update(null, 
            new UpdateWrapper<Coupon>()
                .eq("id", couponId)
                .gt("remain_count", 0)
                .setSql("remain_count = remain_count - 1"));
        
        if (updated == 0) {
            throw new RuntimeException("优惠券已被领完");
        }
        
        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setUserId(userId);
        userCoupon.setCouponId(couponId);
        userCoupon.setStatus("unused");
        userCoupon.setDeleted(0);
        
        userCouponMapper.insert(userCoupon);
    }
    
    @Override
    public Page<UserCouponVO> listUserCoupons(Long userId, Integer page, Integer size, String status) {
        Page<UserCoupon> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<UserCoupon> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserCoupon::getUserId, userId);
        
        if (status != null && !status.isEmpty()) {
            wrapper.eq(UserCoupon::getStatus, status);
        }
        
        wrapper.orderByDesc(UserCoupon::getCreateTime);
        
        Page<UserCoupon> userCouponPage = userCouponMapper.selectPage(pageParam, wrapper);
        
        Page<UserCouponVO> result = new Page<>();
        result.setCurrent(userCouponPage.getCurrent());
        result.setSize(userCouponPage.getSize());
        result.setTotal(userCouponPage.getTotal());
        
        result.setRecords(userCouponPage.getRecords().stream().map(userCoupon -> {
            Coupon coupon = couponMapper.selectById(userCoupon.getCouponId());
            
            UserCouponVO vo = new UserCouponVO();
            vo.setId(userCoupon.getId());
            vo.setCouponId(userCoupon.getCouponId());
            vo.setStatus(userCoupon.getStatus());
            vo.setCreateTime(userCoupon.getCreateTime());
            vo.setUsedTime(userCoupon.getUsedTime());
            
            if (coupon != null) {
                vo.setName(coupon.getName());
                vo.setType(coupon.getType());
                vo.setDiscountValue(coupon.getDiscountValue());
                vo.setMinAmount(coupon.getMinAmount());
                vo.setStartTime(coupon.getStartTime());
                vo.setEndTime(coupon.getEndTime());
            }
            
            return vo;
        }).toList());
        
        return result;
    }
}
