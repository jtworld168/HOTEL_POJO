package com.hotel.store.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hotel.store.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单详情Mapper
 */
@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {
}
