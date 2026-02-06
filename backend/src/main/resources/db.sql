-- 创建数据库
CREATE DATABASE IF NOT EXISTS hotel_store DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE hotel_store;

-- 用户表
CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `password` VARCHAR(100) NOT NULL COMMENT '密码',
  `nickname` VARCHAR(50) COMMENT '昵称',
  `avatar` VARCHAR(255) COMMENT '头像URL',
  `phone` VARCHAR(20) COMMENT '手机号',
  `email` VARCHAR(50) COMMENT '邮箱',
  `role` VARCHAR(20) NOT NULL DEFAULT 'user' COMMENT '角色：admin-管理员，employee-员工，user-普通用户',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 商品表
CREATE TABLE IF NOT EXISTS `product` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` VARCHAR(100) NOT NULL COMMENT '商品名称',
  `barcode` VARCHAR(50) NOT NULL COMMENT '商品条形码',
  `category` VARCHAR(50) COMMENT '商品分类',
  `description` TEXT COMMENT '商品描述',
  `price` DECIMAL(10,2) NOT NULL COMMENT '商品价格',
  `employee_price` DECIMAL(10,2) COMMENT '员工价格',
  `stock` INT NOT NULL DEFAULT 0 COMMENT '库存数量',
  `image` VARCHAR(255) COMMENT '商品图片URL',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-下架，1-上架',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_barcode` (`barcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- 购物车表
CREATE TABLE IF NOT EXISTS `cart` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `product_id` BIGINT NOT NULL COMMENT '商品ID',
  `quantity` INT NOT NULL DEFAULT 1 COMMENT '数量',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车表';

-- 订单表
CREATE TABLE IF NOT EXISTS `orders` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_no` VARCHAR(50) NOT NULL COMMENT '订单号',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `total_amount` DECIMAL(10,2) NOT NULL COMMENT '订单总金额',
  `discount_amount` DECIMAL(10,2) DEFAULT 0 COMMENT '优惠金额',
  `final_amount` DECIMAL(10,2) NOT NULL COMMENT '实付金额',
  `coupon_id` BIGINT COMMENT '使用的优惠券ID',
  `status` VARCHAR(20) NOT NULL DEFAULT 'pending' COMMENT '订单状态：pending-待支付，paid-已支付，completed-已完成，cancelled-已取消',
  `remark` VARCHAR(255) COMMENT '备注',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  INDEX `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 订单详情表
CREATE TABLE IF NOT EXISTS `order_item` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_id` BIGINT NOT NULL COMMENT '订单ID',
  `product_id` BIGINT NOT NULL COMMENT '商品ID',
  `product_name` VARCHAR(100) NOT NULL COMMENT '商品名称',
  `product_image` VARCHAR(255) COMMENT '商品图片',
  `price` DECIMAL(10,2) NOT NULL COMMENT '商品单价',
  `quantity` INT NOT NULL COMMENT '购买数量',
  `subtotal` DECIMAL(10,2) NOT NULL COMMENT '小计金额',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  INDEX `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单详情表';

-- 优惠券表
CREATE TABLE IF NOT EXISTS `coupon` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` VARCHAR(100) NOT NULL COMMENT '优惠券名称',
  `type` VARCHAR(20) NOT NULL COMMENT '优惠券类型：discount-折扣券，cash-代金券',
  `discount_value` DECIMAL(10,2) NOT NULL COMMENT '优惠值：折扣券为折扣比例(0-1)，代金券为金额',
  `min_amount` DECIMAL(10,2) DEFAULT 0 COMMENT '最低使用金额',
  `total_count` INT NOT NULL COMMENT '发行总数',
  `remain_count` INT NOT NULL COMMENT '剩余数量',
  `start_time` DATETIME NOT NULL COMMENT '开始时间',
  `end_time` DATETIME NOT NULL COMMENT '结束时间',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-停用，1-启用',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠券表';

-- 用户优惠券关联表
CREATE TABLE IF NOT EXISTS `user_coupon` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `coupon_id` BIGINT NOT NULL COMMENT '优惠券ID',
  `status` VARCHAR(20) NOT NULL DEFAULT 'unused' COMMENT '状态：unused-未使用，used-已使用，expired-已过期',
  `used_time` DATETIME COMMENT '使用时间',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_user_id` (`user_id`),
  INDEX `idx_coupon_id` (`coupon_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户优惠券关联表';

-- 轮播图表
CREATE TABLE IF NOT EXISTS `banner` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` VARCHAR(100) COMMENT '标题',
  `image` VARCHAR(255) NOT NULL COMMENT '图片URL',
  `link` VARCHAR(255) COMMENT '链接地址',
  `sort` INT NOT NULL DEFAULT 0 COMMENT '排序',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='轮播图表';

-- 插入默认管理员用户 (密码: 123456)
INSERT INTO `sys_user` (`username`, `password`, `nickname`, `role`, `avatar`) 
VALUES ('admin', 'e10adc3949ba59abbe56e057f20f883e', '管理员', 'admin', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png');

-- 插入测试商品数据
INSERT INTO `product` (`name`, `barcode`, `category`, `description`, `price`, `employee_price`, `stock`, `image`) VALUES
('矿泉水', '6901285991004', '饮料', '农夫山泉矿泉水550ml', 2.00, 1.50, 100, 'https://img.alicdn.com/imgextra/i1/2206970884533/O1CN01KGrYKH1RLZ5F6VL5M_!!2206970884533.jpg'),
('可口可乐', '6922255451007', '饮料', '可口可乐330ml', 3.50, 2.80, 80, 'https://img.alicdn.com/imgextra/i2/6000000004052/O1CN01YZF0xO1jGn7iVLx5P_!!6000000004052-0-lubanu-s.jpg'),
('薯片', '6901939612347', '零食', '乐事薯片原味70g', 8.00, 6.50, 60, 'https://img.alicdn.com/imgextra/i3/2206631453571/O1CN01YH2W5r1RLZ5F6W9wL_!!2206631453571.jpg'),
('奥利奥', '6901668001214', '零食', '奥利奥夹心饼干', 10.00, 8.00, 50, 'https://img.alicdn.com/imgextra/i4/2206970884533/O1CN01H3G0Sp1RLZ5F6VNMR_!!2206970884533.jpg'),
('康师傅方便面', '6920152400012', '方便食品', '康师傅红烧牛肉面', 4.50, 3.50, 70, 'https://img.alicdn.com/imgextra/i1/6000000003984/O1CN01Tv0pzh1kD6YZF3l5P_!!6000000003984-0-lubanu-s.jpg');

-- 插入轮播图数据
INSERT INTO `banner` (`title`, `image`, `link`, `sort`) VALUES
('新品上市', 'https://img.alicdn.com/imgextra/i4/6000000003984/O1CN01Tv0pzh1kD6YZF3l5P_!!6000000003984-0-lubanu-s.jpg', '/product/1', 1),
('优惠活动', 'https://img.alicdn.com/imgextra/i2/6000000004052/O1CN01YZF0xO1jGn7iVLx5P_!!6000000004052-0-lubanu-s.jpg', '/product/2', 2),
('热门商品', 'https://img.alicdn.com/imgextra/i3/2206631453571/O1CN01YH2W5r1RLZ5F6W9wL_!!2206631453571.jpg', '/product/3', 3);
