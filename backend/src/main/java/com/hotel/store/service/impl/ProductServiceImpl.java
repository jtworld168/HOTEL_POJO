package com.hotel.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotel.store.entity.Product;
import com.hotel.store.mapper.ProductMapper;
import com.hotel.store.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import jakarta.annotation.Resource;
import java.math.BigDecimal;

@Service
public class ProductServiceImpl implements ProductService {
    
    @Resource
    private ProductMapper productMapper;
    
    @Override
    public Page<Product> list(Integer page, Integer size, String category, String keyword) {
        Page<Product> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        
        if (StringUtils.hasText(category)) {
            wrapper.eq(Product::getCategory, category);
        }
        
        if (StringUtils.hasText(keyword)) {
            wrapper.and(w -> w.like(Product::getName, keyword)
                    .or().like(Product::getBarcode, keyword));
        }
        
        wrapper.eq(Product::getStatus, 1);
        wrapper.orderByDesc(Product::getCreateTime);
        
        return productMapper.selectPage(pageParam, wrapper);
    }
    
    @Override
    public Product getById(Long id) {
        Product product = productMapper.selectById(id);
        if (product == null) {
            throw new RuntimeException("商品不存在");
        }
        return product;
    }
    
    @Override
    public Product getByBarcode(String barcode) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Product::getBarcode, barcode);
        wrapper.eq(Product::getStatus, 1);
        
        Product product = productMapper.selectOne(wrapper);
        if (product == null) {
            throw new RuntimeException("商品不存在");
        }
        return product;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Product product) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Product::getBarcode, product.getBarcode());
        
        Product existProduct = productMapper.selectOne(wrapper);
        if (existProduct != null) {
            throw new RuntimeException("条形码已存在");
        }
        
        product.setStatus(1);
        product.setDeleted(0);
        productMapper.insert(product);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Product product) {
        Product existProduct = productMapper.selectById(product.getId());
        if (existProduct == null) {
            throw new RuntimeException("商品不存在");
        }
        
        if (!existProduct.getBarcode().equals(product.getBarcode())) {
            LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Product::getBarcode, product.getBarcode());
            wrapper.ne(Product::getId, product.getId());
            
            Product duplicateProduct = productMapper.selectOne(wrapper);
            if (duplicateProduct != null) {
                throw new RuntimeException("条形码已存在");
            }
        }
        
        productMapper.updateById(product);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        Product product = productMapper.selectById(id);
        if (product == null) {
            throw new RuntimeException("商品不存在");
        }
        
        productMapper.deleteById(id);
    }
    
    @Override
    public BigDecimal getProductPrice(Long productId, String userRole) {
        Product product = getById(productId);
        
        if ("employee".equals(userRole) && product.getEmployeePrice() != null) {
            return product.getEmployeePrice();
        }
        
        return product.getPrice();
    }
}
