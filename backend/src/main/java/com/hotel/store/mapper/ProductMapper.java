package com.hotel.store.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hotel.store.entity.Product;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品Mapper
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}
