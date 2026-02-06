package com.hotel.store.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "用户优惠券")
public class UserCouponVO {
    
    @Schema(description = "用户优惠券ID")
    private Long id;
    
    @Schema(description = "优惠券ID")
    private Long couponId;
    
    @Schema(description = "优惠券名称")
    private String name;
    
    @Schema(description = "优惠券类型")
    private String type;
    
    @Schema(description = "优惠值")
    private BigDecimal discountValue;
    
    @Schema(description = "最低消费金额")
    private BigDecimal minAmount;
    
    @Schema(description = "状态")
    private String status;
    
    @Schema(description = "开始时间")
    private LocalDateTime startTime;
    
    @Schema(description = "结束时间")
    private LocalDateTime endTime;
    
    @Schema(description = "领取时间")
    private LocalDateTime createTime;
    
    @Schema(description = "使用时间")
    private LocalDateTime usedTime;
}
