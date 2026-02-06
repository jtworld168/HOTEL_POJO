package com.hotel.store.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "购物车项")
public class CartItemVO {
    
    @Schema(description = "购物车ID")
    private Long id;
    
    @Schema(description = "商品ID")
    private Long productId;
    
    @Schema(description = "商品名称")
    private String productName;
    
    @Schema(description = "商品图片")
    private String productImage;
    
    @Schema(description = "价格")
    private BigDecimal price;
    
    @Schema(description = "数量")
    private Integer quantity;
    
    @Schema(description = "小计")
    private BigDecimal subtotal;
    
    @Schema(description = "库存")
    private Integer stock;
}
