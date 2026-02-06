package com.hotel.store.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hotel.store.entity.Coupon;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券Mapper
 */
@Mapper
public interface CouponMapper extends BaseMapper<Coupon> {
}
