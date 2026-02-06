package com.hotel.store.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotel.store.entity.Product;

import java.util.List;

public interface ProductService {
    
    /**
     * 分页查询商品列表
     */
    Page<Product> list(Integer page, Integer size, String category, String keyword);
    
    /**
     * 根据ID查询商品
     */
    Product getById(Long id);
    
    /**
     * 根据条形码查询商品
     */
    Product getByBarcode(String barcode);
    
    /**
     * 创建商品
     */
    void create(Product product);
    
    /**
     * 更新商品
     */
    void update(Product product);
    
    /**
     * 删除商品
     */
    void delete(Long id);
    
    /**
     * 获取商品价格(考虑员工价)
     */
    java.math.BigDecimal getProductPrice(Long productId, String userRole);
}
