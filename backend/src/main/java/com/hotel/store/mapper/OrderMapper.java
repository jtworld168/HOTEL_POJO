package com.hotel.store.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hotel.store.entity.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单Mapper
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
