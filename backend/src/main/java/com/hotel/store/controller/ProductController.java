package com.hotel.store.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotel.store.common.Result;
import com.hotel.store.entity.Product;
import com.hotel.store.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

@Tag(name = "商品管理")
@RestController
@RequestMapping("/api/product")
public class ProductController {
    
    @Resource
    private ProductService productService;
    
    @Operation(summary = "商品列表")
    @GetMapping("/list")
    public Result<Page<Product>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword) {
        Page<Product> result = productService.list(page, size, category, keyword);
        return Result.success(result);
    }
    
    @Operation(summary = "根据ID查询商品")
    @GetMapping("/{id}")
    public Result<Product> getById(@PathVariable Long id) {
        Product product = productService.getById(id);
        return Result.success(product);
    }
    
    @Operation(summary = "根据条形码查询商品")
    @GetMapping("/barcode/{barcode}")
    public Result<Product> getByBarcode(@PathVariable String barcode) {
        Product product = productService.getByBarcode(barcode);
        return Result.success(product);
    }
    
    @Operation(summary = "创建商品")
    @SaCheckRole("admin")
    @PostMapping
    public Result<Void> create(@Valid @RequestBody Product product) {
        productService.create(product);
        return Result.success();
    }
    
    @Operation(summary = "更新商品")
    @SaCheckRole("admin")
    @PutMapping
    public Result<Void> update(@Valid @RequestBody Product product) {
        productService.update(product);
        return Result.success();
    }
    
    @Operation(summary = "删除商品")
    @SaCheckRole("admin")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return Result.success();
    }
}
