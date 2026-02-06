package com.hotel.store.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotel.store.entity.Banner;

import java.util.List;

public interface BannerService {
    
    /**
     * 轮播图列表
     */
    Page<Banner> list(Integer page, Integer size, Integer status);
    
    /**
     * 获取所有启用的轮播图
     */
    List<Banner> listActive();
    
    /**
     * 创建轮播图
     */
    void create(Banner banner);
    
    /**
     * 更新轮播图
     */
    void update(Banner banner);
    
    /**
     * 删除轮播图
     */
    void delete(Long id);
}
