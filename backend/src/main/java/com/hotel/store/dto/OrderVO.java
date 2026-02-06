package com.hotel.store.dto;

import com.hotel.store.entity.OrderItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "订单信息")
public class OrderVO {
    
    @Schema(description = "订单ID")
    private Long id;
    
    @Schema(description = "订单号")
    private String orderNo;
    
    @Schema(description = "用户ID")
    private Long userId;
    
    @Schema(description = "总金额")
    private BigDecimal totalAmount;
    
    @Schema(description = "优惠金额")
    private BigDecimal discountAmount;
    
    @Schema(description = "实付金额")
    private BigDecimal finalAmount;
    
    @Schema(description = "优惠券ID")
    private Long couponId;
    
    @Schema(description = "状态")
    private String status;
    
    @Schema(description = "备注")
    private String remark;
    
    @Schema(description = "订单项")
    private List<OrderItem> items;
    
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
