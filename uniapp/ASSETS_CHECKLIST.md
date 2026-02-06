# 图片资源清单

本项目需要以下图片资源，请根据实际需求添加到对应目录。

## 底部导航图标 (static/tabbar/)

建议尺寸：64x64 px

- [ ] `home.png` - 首页图标（未选中）
- [ ] `home-active.png` - 首页图标（选中）
- [ ] `category.png` - 分类图标（未选中）
- [ ] `category-active.png` - 分类图标（选中）
- [ ] `cart.png` - 购物车图标（未选中）
- [ ] `cart-active.png` - 购物车图标（选中）
- [ ] `user.png` - 我的图标（未选中）
- [ ] `user-active.png` - 我的图标（选中）

**颜色建议：**
- 未选中：灰色 #7A7E83
- 选中：绿色 #3cc51f

## 功能图标 (static/icon/)

建议尺寸：48x48 px 或 96x96 px

- [ ] `scan.png` - 扫码图标
- [ ] `coupon.png` - 优惠券图标
- [ ] `order.png` - 订单图标
- [ ] `cart-white.png` - 白色购物车图标（用于按钮）
- [ ] `delete.png` - 删除图标
- [ ] `profile.png` - 个人信息图标
- [ ] `logout.png` - 退出登录图标

## 应用图片 (static/)

### Logo 和头像
- [ ] `logo.png` - 应用 Logo (建议 160x160 px)
- [ ] `avatar.png` - 默认头像 (建议 120x120 px)

### 占位图
- [ ] `placeholder.png` - 商品占位图 (建议 300x300 px)

### 空状态图片
- [ ] `empty-cart.png` - 购物车空状态图 (建议 300x300 px)
- [ ] `empty-order.png` - 订单空状态图 (建议 300x300 px)
- [ ] `empty-coupon.png` - 优惠券空状态图 (建议 300x300 px)

## 轮播图示例 (可选)

如果需要测试轮播图功能，可以准备：
- [ ] `banner1.jpg` - 轮播图1 (建议 750x360 px)
- [ ] `banner2.jpg` - 轮播图2 (建议 750x360 px)
- [ ] `banner3.jpg` - 轮播图3 (建议 750x360 px)

## 图标资源获取建议

### 1. 阿里巴巴矢量图标库 (iconfont)
- 网址：https://www.iconfont.cn/
- 免费、可商用
- 支持 SVG、PNG 格式

### 2. IconFont (字节跳动)
- 网址：https://iconpark.oceanengine.com/
- 丰富的图标资源
- 支持多种格式

### 3. flaticon
- 网址：https://www.flaticon.com/
- 高质量图标
- 部分免费

### 4. 自己设计
- 使用 Figma / Sketch / Adobe XD 设计
- 保持风格统一
- 注意导出尺寸

## 图片格式建议

- **图标**：PNG 格式（支持透明背景）
- **照片**：JPG 格式
- **Logo**：PNG 格式（支持透明背景）
- **空状态**：PNG 或 SVG 格式

## 颜色方案建议

### 主色调
- 主色：#07c160（绿色）
- 辅助色：#667eea（紫色）
- 警告色：#ff4444（红色）
- 信息色：#10aeff（蓝色）
- 成功色：#07c160（绿色）

### 中性色
- 文字主色：#333333
- 文字辅色：#666666
- 文字浅色：#999999
- 边框色：#e5e5e5
- 背景色：#f5f5f5 或 #f8f8f8

## 注意事项

1. **版权问题**
   - 确保使用的图片有合法授权
   - 商用项目避免使用未授权资源

2. **文件大小**
   - 尽量压缩图片大小
   - 建议单张图片不超过 100KB
   - 使用工具：TinyPNG、ImageOptim

3. **命名规范**
   - 使用小写字母
   - 使用连字符分隔
   - 语义化命名

4. **目录结构**
   ```
   static/
   ├── tabbar/          # 底部导航图标
   ├── icon/            # 功能图标
   ├── logo.png         # Logo
   ├── avatar.png       # 默认头像
   ├── placeholder.png  # 占位图
   ├── empty-cart.png   # 空购物车
   ├── empty-order.png  # 空订单
   └── empty-coupon.png # 空优惠券
   ```

5. **临时方案**
   如果暂时没有图片资源，可以：
   - 使用纯色背景代替
   - 使用文字图标
   - 使用 emoji 表情
   - 使用占位图服务（如 placeholder.com）

## 快速开始

如果需要快速测试，可以使用纯色背景：

```css
/* 临时图标样式 */
.icon-placeholder {
  width: 48rpx;
  height: 48rpx;
  background-color: #07c160;
  border-radius: 8rpx;
}
```

或使用 emoji：
- 🏠 首页
- 📋 分类
- 🛒 购物车
- 👤 我的
- 📸 扫码
- 🎫 优惠券
- 📦 订单

---

**提示**: 图片资源不是必需的，应用可以在没有图片的情况下运行，只是视觉效果会受影响。建议在开发初期使用临时方案，产品上线前再替换为正式图片。
