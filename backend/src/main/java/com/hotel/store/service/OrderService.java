package com.hotel.store.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotel.store.dto.CreateOrderRequest;
import com.hotel.store.dto.OrderVO;

public interface OrderService {
    
    /**
     * 创建订单
     */
    OrderVO create(Long userId, CreateOrderRequest request);
    
    /**
     * 订单列表
     */
    Page<OrderVO> list(Long userId, Integer page, Integer size, String status);
    
    /**
     * 获取订单详情
     */
    OrderVO getById(Long userId, Long orderId);
    
    /**
     * 取消订单
     */
    void cancel(Long userId, Long orderId);
}
