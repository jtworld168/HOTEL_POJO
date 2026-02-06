package com.hotel.store.service;

import com.hotel.store.dto.AddCartRequest;
import com.hotel.store.dto.CartItemVO;
import com.hotel.store.dto.UpdateCartRequest;

import java.util.List;

public interface CartService {
    
    /**
     * 添加购物车
     */
    void add(Long userId, AddCartRequest request);
    
    /**
     * 更新购物车
     */
    void update(Long userId, Long cartId, UpdateCartRequest request);
    
    /**
     * 删除购物车项
     */
    void delete(Long userId, Long cartId);
    
    /**
     * 获取购物车列表
     */
    List<CartItemVO> list(Long userId);
    
    /**
     * 清空购物车
     */
    void clear(Long userId);
}
