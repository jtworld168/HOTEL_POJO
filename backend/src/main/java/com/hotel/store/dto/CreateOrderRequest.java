package com.hotel.store.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "创建订单请求")
public class CreateOrderRequest {
    
    @Schema(description = "优惠券ID")
    private Long couponId;
    
    @Schema(description = "备注")
    private String remark;
}
