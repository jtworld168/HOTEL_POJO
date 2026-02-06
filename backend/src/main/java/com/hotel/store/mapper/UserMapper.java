package com.hotel.store.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hotel.store.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
