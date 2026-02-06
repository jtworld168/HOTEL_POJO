package com.hotel.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotel.store.entity.Banner;
import com.hotel.store.mapper.BannerMapper;
import com.hotel.store.service.BannerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {
    
    @Resource
    private BannerMapper bannerMapper;
    
    @Override
    public Page<Banner> list(Integer page, Integer size, Integer status) {
        Page<Banner> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Banner> wrapper = new LambdaQueryWrapper<>();
        
        if (status != null) {
            wrapper.eq(Banner::getStatus, status);
        }
        
        wrapper.orderByAsc(Banner::getSort);
        wrapper.orderByDesc(Banner::getCreateTime);
        
        return bannerMapper.selectPage(pageParam, wrapper);
    }
    
    @Override
    public List<Banner> listActive() {
        LambdaQueryWrapper<Banner> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Banner::getStatus, 1);
        wrapper.orderByAsc(Banner::getSort);
        
        return bannerMapper.selectList(wrapper);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Banner banner) {
        banner.setStatus(1);
        banner.setDeleted(0);
        
        bannerMapper.insert(banner);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Banner banner) {
        Banner existBanner = bannerMapper.selectById(banner.getId());
        if (existBanner == null) {
            throw new RuntimeException("轮播图不存在");
        }
        
        bannerMapper.updateById(banner);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        Banner banner = bannerMapper.selectById(id);
        if (banner == null) {
            throw new RuntimeException("轮播图不存在");
        }
        
        bannerMapper.deleteById(id);
    }
}
