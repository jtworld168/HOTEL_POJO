# UniApp 项目开发指南

## 项目简介

本项目是一个基于 uni-app 开发的酒店大堂无人售货超市移动应用，完整实现了商品浏览、购物车、订单管理、优惠券系统等核心功能，可编译为微信小程序、H5、App等多个平台。

## 核心功能模块

### 1. 用户模块
- **登录页** (`pages/login/login.vue`)
  - 用户名密码登录
  - Token认证
  - 登录状态持久化

- **注册页** (`pages/register/register.vue`)
  - 用户信息注册
  - 表单验证
  - 密码确认

- **个人中心** (`pages/user/user.vue`)
  - 用户信息展示
  - 功能菜单
  - 退出登录

- **个人信息** (`pages/user/profile.vue`)
  - 信息编辑
  - 头像更换（预留）

### 2. 商品模块
- **首页** (`pages/index/index.vue`)
  - 轮播图展示
  - 功能快捷入口
  - 商品推荐列表
  - 下拉刷新
  - 上拉加载

- **分类页** (`pages/category/category.vue`)
  - 左侧分类导航
  - 右侧商品列表
  - 分类筛选

- **商品详情** (`pages/product/detail.vue`)
  - 商品图片展示
  - 价格信息（普通价/员工价）
  - 库存展示
  - 加入购物车
  - 数量选择

- **扫码购物** (`pages/scan/scan.vue`)
  - 条形码扫描
  - 手动输入条码
  - 自动跳转详情

### 3. 购物车模块
- **购物车** (`pages/cart/cart.vue`)
  - 商品列表
  - 全选/单选
  - 数量调整
  - 删除商品
  - 价格计算
  - 结算功能

### 4. 订单模块
- **订单列表** (`pages/order/list.vue`)
  - 订单历史
  - 订单状态筛选
  - 下拉刷新
  - 取消订单

- **订单详情** (`pages/order/detail.vue`)
  - 订单信息
  - 商品列表
  - 费用明细
  - 订单操作

### 5. 优惠券模块
- **优惠券列表** (`pages/coupon/list.vue`)
  - 可领取优惠券
  - 优惠券信息展示
  - 领取功能

- **我的优惠券** (`pages/coupon/my.vue`)
  - 优惠券状态分类
  - 可用/已使用/已过期

## 技术架构

### 网络请求层
**文件**: `utils/request.js`

封装了 uni.request，提供统一的请求方法：
- 自动添加 Token
- 统一错误处理
- 请求/响应拦截
- 401自动跳转登录

```javascript
import request from '@/utils/request.js'

// GET请求
request.get('/api/product/list', { page: 1 })

// POST请求
request.post('/api/user/login', { username, password })
```

### 本地存储层
**文件**: `utils/storage.js`

提供本地存储的统一接口：
- setStorage / getStorage
- setToken / getToken
- setUserInfo / getUserInfo
- isLogin / logout

### 工具函数层
**文件**: `utils/util.js`

提供常用工具函数：
- formatTime - 时间格式化
- formatDate - 日期格式化
- formatPrice - 价格格式化
- getImageUrl - 图片路径处理
- getOrderStatusText - 订单状态文本
- getCouponTypeText - 优惠券类型文本

### API服务层
**目录**: `api/`

按模块划分的API接口：
- `user.js` - 用户相关接口
- `product.js` - 商品相关接口
- `cart.js` - 购物车接口
- `order.js` - 订单接口
- `coupon.js` - 优惠券接口
- `banner.js` - 轮播图接口

## 目录结构说明

```
uniapp/
├── api/                    # API接口层
│   ├── banner.js
│   ├── cart.js
│   ├── coupon.js
│   ├── order.js
│   ├── product.js
│   └── user.js
├── pages/                  # 页面
│   ├── cart/              # 购物车
│   ├── category/          # 分类
│   ├── coupon/            # 优惠券
│   ├── index/             # 首页
│   ├── login/             # 登录
│   ├── order/             # 订单
│   ├── product/           # 商品
│   ├── register/          # 注册
│   ├── scan/              # 扫码
│   └── user/              # 用户
├── static/                 # 静态资源
│   ├── icon/              # 图标
│   └── tabbar/            # 底部导航图标
├── utils/                  # 工具函数
│   ├── request.js         # 网络请求
│   ├── storage.js         # 本地存储
│   └── util.js            # 工具函数
├── App.vue                # 应用入口
├── main.js                # 主入口
├── manifest.json          # 应用配置
├── pages.json             # 页面配置
├── package.json           # 依赖配置
└── README.md              # 项目说明
```

## 开发规范

### 1. 命名规范
- 页面文件：小写字母，如 `index.vue`
- 组件文件：大驼峰，如 `ProductCard.vue`
- API文件：小写字母，如 `user.js`
- 工具文件：小写字母，如 `util.js`

### 2. 样式规范
- 使用 rpx 单位（750rpx = 屏幕宽度）
- 统一使用 scoped 样式
- 颜色使用变量或统一配置

### 3. 代码规范
- 使用 ES6+ 语法
- 统一使用 async/await 处理异步
- 错误统一使用 try-catch 处理
- 必要的地方添加注释

### 4. 接口调用规范
```javascript
// 1. 引入API
import { getProductList } from '@/api/product.js'

// 2. 在methods中调用
async loadProducts() {
  try {
    const data = await getProductList({ page: 1 })
    this.productList = data.records || []
  } catch (e) {
    console.error('加载失败', e)
  }
}
```

## 数据流转

### 登录流程
1. 用户输入账号密码
2. 调用 `/api/user/login` 接口
3. 后端返回 token 和用户信息
4. 保存到本地存储
5. 跳转到首页

### 购物流程
1. 浏览商品列表
2. 点击商品查看详情
3. 加入购物车
4. 在购物车页面调整数量
5. 选择商品结算
6. 创建订单
7. 完成支付（待实现）

### 优惠券流程
1. 浏览优惠券列表
2. 领取优惠券
3. 在我的优惠券查看
4. 下单时使用优惠券（待实现）

## 平台差异处理

### 扫码功能
```javascript
// 使用条件编译
// #ifdef APP-PLUS || H5
uni.scanCode({ ... })
// #endif

// #ifdef MP-WEIXIN
uni.scanCode({ onlyFromCamera: true, ... })
// #endif
```

### 权限处理
不同平台的权限申请方式不同：
- App: 需要在 manifest.json 配置
- 小程序: 运行时动态申请
- H5: 浏览器自动申请

## 调试技巧

### 1. 控制台调试
```javascript
console.log('数据:', data)
console.error('错误:', error)
```

### 2. 网络请求调试
- 使用浏览器开发者工具
- 微信开发者工具的网络面板
- uni-app 的调试面板

### 3. 样式调试
- 使用浏览器审查元素
- 调整 rpx 值
- 检查响应式布局

## 常见问题

### Q1: 如何配置后端地址？
A: 修改 `utils/request.js` 中的 `BASE_URL`

### Q2: 图标不显示怎么办？
A: 检查图片路径，确保图片文件存在

### Q3: 网络请求失败？
A: 
- 检查后端服务是否启动
- 检查 URL 配置是否正确
- 小程序需配置合法域名

### Q4: 页面跳转失败？
A: 检查页面是否在 `pages.json` 中注册

### Q5: 样式不生效？
A: 
- 检查是否使用了 scoped
- 检查选择器优先级
- 清除缓存重新编译

## 性能优化建议

### 1. 图片优化
- 使用合适尺寸的图片
- 启用图片懒加载
- 使用 WebP 格式

### 2. 请求优化
- 实现请求缓存
- 减少不必要的请求
- 使用防抖节流

### 3. 渲染优化
- 使用虚拟列表
- 避免频繁的数据更新
- 合理使用 v-if 和 v-show

### 4. 包体积优化
- 按需引入组件
- 删除未使用的代码
- 压缩图片资源

## 后续开发计划

- [ ] 完善支付功能
- [ ] 添加商品收藏
- [ ] 添加评价系统
- [ ] 优化用户体验
- [ ] 添加多语言支持
- [ ] 实现消息推送
- [ ] 添加分享功能

## 贡献指南

1. Fork 项目
2. 创建功能分支
3. 提交代码
4. 发起 Pull Request

## 联系方式

如有问题，请提交 Issue 或联系开发团队。

---

**Happy Coding!**
