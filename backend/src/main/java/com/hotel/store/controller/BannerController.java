package com.hotel.store.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotel.store.common.Result;
import com.hotel.store.entity.Banner;
import com.hotel.store.service.BannerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

@Tag(name = "轮播图管理")
@RestController
@RequestMapping("/api/banner")
public class BannerController {
    
    @Resource
    private BannerService bannerService;
    
    @Operation(summary = "轮播图列表")
    @GetMapping("/list")
    public Result<Page<Banner>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Integer status) {
        Page<Banner> result = bannerService.list(page, size, status);
        return Result.success(result);
    }
    
    @Operation(summary = "获取启用的轮播图")
    @GetMapping("/active")
    public Result<List<Banner>> listActive() {
        List<Banner> result = bannerService.listActive();
        return Result.success(result);
    }
    
    @Operation(summary = "创建轮播图")
    @SaCheckRole("admin")
    @PostMapping
    public Result<Void> create(@Valid @RequestBody Banner banner) {
        bannerService.create(banner);
        return Result.success();
    }
    
    @Operation(summary = "更新轮播图")
    @SaCheckRole("admin")
    @PutMapping
    public Result<Void> update(@Valid @RequestBody Banner banner) {
        bannerService.update(banner);
        return Result.success();
    }
    
    @Operation(summary = "删除轮播图")
    @SaCheckRole("admin")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        bannerService.delete(id);
        return Result.success();
    }
}
