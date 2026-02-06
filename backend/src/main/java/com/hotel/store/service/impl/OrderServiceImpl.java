package com.hotel.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotel.store.dto.CreateOrderRequest;
import com.hotel.store.dto.OrderVO;
import com.hotel.store.entity.*;
import com.hotel.store.mapper.*;
import com.hotel.store.service.OrderService;
import com.hotel.store.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import jakarta.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class OrderServiceImpl implements OrderService {
    
    @Resource
    private OrderMapper orderMapper;
    
    @Resource
    private OrderItemMapper orderItemMapper;
    
    @Resource
    private CartMapper cartMapper;
    
    @Resource
    private ProductMapper productMapper;
    
    @Resource
    private UserMapper userMapper;
    
    @Resource
    private CouponMapper couponMapper;
    
    @Resource
    private UserCouponMapper userCouponMapper;
    
    @Resource
    private ProductService productService;
    
    private static final AtomicLong orderNoCounter = new AtomicLong(0);
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderVO create(Long userId, CreateOrderRequest request) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        LambdaQueryWrapper<Cart> cartWrapper = new LambdaQueryWrapper<>();
        cartWrapper.eq(Cart::getUserId, userId);
        List<Cart> carts = cartMapper.selectList(cartWrapper);
        
        if (carts.isEmpty()) {
            throw new RuntimeException("购物车为空");
        }
        
        BigDecimal totalAmount = BigDecimal.ZERO;
        
        for (Cart cart : carts) {
            Product product = productMapper.selectById(cart.getProductId());
            if (product == null) {
                throw new RuntimeException("商品不存在: " + cart.getProductId());
            }
            
            if (product.getStock() < cart.getQuantity()) {
                throw new RuntimeException("商品库存不足: " + product.getName());
            }
            
            BigDecimal price = productService.getProductPrice(product.getId(), user.getRole());
            totalAmount = totalAmount.add(price.multiply(new BigDecimal(cart.getQuantity())));
        }
        
        BigDecimal discountAmount = BigDecimal.ZERO;
        
        if (request.getCouponId() != null) {
            LambdaQueryWrapper<UserCoupon> userCouponWrapper = new LambdaQueryWrapper<>();
            userCouponWrapper.eq(UserCoupon::getUserId, userId);
            userCouponWrapper.eq(UserCoupon::getCouponId, request.getCouponId());
            userCouponWrapper.eq(UserCoupon::getStatus, "unused");
            
            UserCoupon userCoupon = userCouponMapper.selectOne(userCouponWrapper);
            if (userCoupon == null) {
                throw new RuntimeException("优惠券不可用");
            }
            
            Coupon coupon = couponMapper.selectById(request.getCouponId());
            if (coupon == null) {
                throw new RuntimeException("优惠券不存在");
            }
            
            LocalDateTime now = LocalDateTime.now();
            if (now.isBefore(coupon.getStartTime()) || now.isAfter(coupon.getEndTime())) {
                throw new RuntimeException("优惠券不在有效期内");
            }
            
            if (totalAmount.compareTo(coupon.getMinAmount()) < 0) {
                throw new RuntimeException("未达到优惠券使用条件");
            }
            
            if ("discount".equals(coupon.getType())) {
                discountAmount = totalAmount.multiply(BigDecimal.ONE.subtract(coupon.getDiscountValue()));
            } else if ("cash".equals(coupon.getType())) {
                discountAmount = coupon.getDiscountValue();
            }
            
            userCoupon.setStatus("used");
            userCoupon.setUsedTime(now);
            userCouponMapper.updateById(userCoupon);
        }
        
        BigDecimal finalAmount = totalAmount.subtract(discountAmount);
        if (finalAmount.compareTo(BigDecimal.ZERO) < 0) {
            finalAmount = BigDecimal.ZERO;
        }
        
        Order order = new Order();
        order.setOrderNo(generateOrderNo());
        order.setUserId(userId);
        order.setTotalAmount(totalAmount);
        order.setDiscountAmount(discountAmount);
        order.setFinalAmount(finalAmount);
        order.setCouponId(request.getCouponId());
        order.setStatus("pending");
        order.setRemark(request.getRemark());
        order.setDeleted(0);
        
        orderMapper.insert(order);
        
        for (Cart cart : carts) {
            Product product = productMapper.selectById(cart.getProductId());
            BigDecimal price = productService.getProductPrice(product.getId(), user.getRole());
            
            OrderItem item = new OrderItem();
            item.setOrderId(order.getId());
            item.setProductId(product.getId());
            item.setProductName(product.getName());
            item.setProductImage(product.getImage());
            item.setPrice(price);
            item.setQuantity(cart.getQuantity());
            item.setSubtotal(price.multiply(new BigDecimal(cart.getQuantity())));
            
            orderItemMapper.insert(item);
            
            int updated = productMapper.update(null,
                new UpdateWrapper<Product>()
                    .eq("id", product.getId())
                    .ge("stock", cart.getQuantity())
                    .setSql("stock = stock - " + cart.getQuantity()));
            
            if (updated == 0) {
                throw new RuntimeException("商品库存不足: " + product.getName());
            }
        }
        
        cartMapper.delete(cartWrapper);
        
        return getById(userId, order.getId());
    }
    
    @Override
    public Page<OrderVO> list(Long userId, Integer page, Integer size, String status) {
        Page<Order> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getUserId, userId);
        
        if (StringUtils.hasText(status)) {
            wrapper.eq(Order::getStatus, status);
        }
        
        wrapper.orderByDesc(Order::getCreateTime);
        
        Page<Order> orderPage = orderMapper.selectPage(pageParam, wrapper);
        
        Page<OrderVO> result = new Page<>();
        result.setCurrent(orderPage.getCurrent());
        result.setSize(orderPage.getSize());
        result.setTotal(orderPage.getTotal());
        
        List<OrderVO> records = orderPage.getRecords().stream().map(order -> {
            OrderVO vo = new OrderVO();
            BeanUtils.copyProperties(order, vo);
            
            LambdaQueryWrapper<OrderItem> itemWrapper = new LambdaQueryWrapper<>();
            itemWrapper.eq(OrderItem::getOrderId, order.getId());
            List<OrderItem> items = orderItemMapper.selectList(itemWrapper);
            vo.setItems(items);
            
            return vo;
        }).toList();
        
        result.setRecords(records);
        
        return result;
    }
    
    @Override
    public OrderVO getById(Long userId, Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null || !order.getUserId().equals(userId)) {
            throw new RuntimeException("订单不存在");
        }
        
        OrderVO vo = new OrderVO();
        BeanUtils.copyProperties(order, vo);
        
        LambdaQueryWrapper<OrderItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderItem::getOrderId, orderId);
        List<OrderItem> items = orderItemMapper.selectList(wrapper);
        vo.setItems(items);
        
        return vo;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancel(Long userId, Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null || !order.getUserId().equals(userId)) {
            throw new RuntimeException("订单不存在");
        }
        
        if (!"pending".equals(order.getStatus())) {
            throw new RuntimeException("订单状态不允许取消");
        }
        
        LambdaQueryWrapper<OrderItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderItem::getOrderId, orderId);
        List<OrderItem> items = orderItemMapper.selectList(wrapper);
        
        for (OrderItem item : items) {
            productMapper.update(null,
                new UpdateWrapper<Product>()
                    .eq("id", item.getProductId())
                    .setSql("stock = stock + " + item.getQuantity()));
        }
        
        if (order.getCouponId() != null) {
            LambdaQueryWrapper<UserCoupon> userCouponWrapper = new LambdaQueryWrapper<>();
            userCouponWrapper.eq(UserCoupon::getUserId, userId);
            userCouponWrapper.eq(UserCoupon::getCouponId, order.getCouponId());
            userCouponWrapper.eq(UserCoupon::getStatus, "used");
            
            UserCoupon userCoupon = userCouponMapper.selectOne(userCouponWrapper);
            if (userCoupon != null) {
                userCoupon.setStatus("unused");
                userCoupon.setUsedTime(null);
                userCouponMapper.updateById(userCoupon);
            }
        }
        
        order.setStatus("cancelled");
        orderMapper.updateById(order);
    }
    
    private String generateOrderNo() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        long counter = orderNoCounter.incrementAndGet() % 100000;
        return String.format("ORD%s%05d", timestamp, counter);
    }
}
