package com.hotel.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hotel.store.dto.AddCartRequest;
import com.hotel.store.dto.CartItemVO;
import com.hotel.store.dto.UpdateCartRequest;
import com.hotel.store.entity.Cart;
import com.hotel.store.entity.Product;
import com.hotel.store.entity.User;
import com.hotel.store.mapper.CartMapper;
import com.hotel.store.mapper.ProductMapper;
import com.hotel.store.mapper.UserMapper;
import com.hotel.store.service.CartService;
import com.hotel.store.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    
    @Resource
    private CartMapper cartMapper;
    
    @Resource
    private ProductMapper productMapper;
    
    @Resource
    private UserMapper userMapper;
    
    @Resource
    private ProductService productService;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Long userId, AddCartRequest request) {
        Product product = productMapper.selectById(request.getProductId());
        if (product == null) {
            throw new RuntimeException("商品不存在");
        }
        
        if (product.getStock() < request.getQuantity()) {
            throw new RuntimeException("库存不足");
        }
        
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId, userId);
        wrapper.eq(Cart::getProductId, request.getProductId());
        
        Cart existCart = cartMapper.selectOne(wrapper);
        
        if (existCart != null) {
            existCart.setQuantity(existCart.getQuantity() + request.getQuantity());
            
            if (product.getStock() < existCart.getQuantity()) {
                throw new RuntimeException("库存不足");
            }
            
            cartMapper.updateById(existCart);
        } else {
            Cart cart = new Cart();
            cart.setUserId(userId);
            cart.setProductId(request.getProductId());
            cart.setQuantity(request.getQuantity());
            cart.setDeleted(0);
            
            cartMapper.insert(cart);
        }
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long userId, Long cartId, UpdateCartRequest request) {
        Cart cart = cartMapper.selectById(cartId);
        if (cart == null || !cart.getUserId().equals(userId)) {
            throw new RuntimeException("购物车项不存在");
        }
        
        Product product = productMapper.selectById(cart.getProductId());
        if (product == null) {
            throw new RuntimeException("商品不存在");
        }
        
        if (product.getStock() < request.getQuantity()) {
            throw new RuntimeException("库存不足");
        }
        
        cart.setQuantity(request.getQuantity());
        cartMapper.updateById(cart);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long userId, Long cartId) {
        Cart cart = cartMapper.selectById(cartId);
        if (cart == null || !cart.getUserId().equals(userId)) {
            throw new RuntimeException("购物车项不存在");
        }
        
        cartMapper.deleteById(cartId);
    }
    
    @Override
    public List<CartItemVO> list(Long userId) {
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId, userId);
        wrapper.orderByDesc(Cart::getCreateTime);
        
        List<Cart> carts = cartMapper.selectList(wrapper);
        List<CartItemVO> result = new ArrayList<>();
        
        User user = userMapper.selectById(userId);
        String userRole = user != null ? user.getRole() : "user";
        
        for (Cart cart : carts) {
            Product product = productMapper.selectById(cart.getProductId());
            if (product == null || product.getStatus() != 1) {
                continue;
            }
            
            CartItemVO vo = new CartItemVO();
            vo.setId(cart.getId());
            vo.setProductId(product.getId());
            vo.setProductName(product.getName());
            vo.setProductImage(product.getImage());
            vo.setQuantity(cart.getQuantity());
            vo.setStock(product.getStock());
            
            BigDecimal price = productService.getProductPrice(product.getId(), userRole);
            vo.setPrice(price);
            vo.setSubtotal(price.multiply(new BigDecimal(cart.getQuantity())));
            
            result.add(vo);
        }
        
        return result;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clear(Long userId) {
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId, userId);
        
        cartMapper.delete(wrapper);
    }
}
