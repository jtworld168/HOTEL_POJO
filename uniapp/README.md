# UniApp 酒店无人超市移动应用

## 项目简介

这是一个基于 uni-app 开发的酒店大堂无人售货超市移动应用，支持多平台（微信小程序、H5、App等）。

## 功能特性

### 1. 用户系统
- ✅ 用户登录/注册
- ✅ 个人信息管理
- ✅ 三种角色支持（管理员、员工、普通用户）
- ✅ 员工专享价格展示

### 2. 商品管理
- ✅ 首页商品展示
- ✅ 商品分类浏览
- ✅ 商品详情查看
- ✅ 条形码扫描购物
- ✅ 商品搜索

### 3. 购物车
- ✅ 添加/删除商品
- ✅ 数量调整
- ✅ 商品选择
- ✅ 价格计算

### 4. 订单管理
- ✅ 创建订单
- ✅ 订单列表
- ✅ 订单详情
- ✅ 取消订单

### 5. 优惠券系统
- ✅ 优惠券列表
- ✅ 领取优惠券
- ✅ 我的优惠券
- ✅ 折扣券/代金券

### 6. 其他功能
- ✅ 首页轮播图
- ✅ 下拉刷新
- ✅ 上拉加载
- ✅ 扫码功能

## 技术栈

- **框架**: uni-app
- **语言**: Vue.js + JavaScript
- **UI**: 自定义组件
- **状态管理**: 本地存储
- **网络请求**: uni.request 封装
- **支持平台**: 
  - 微信小程序
  - H5
  - App (Android/iOS)
  - 支付宝小程序
  - 百度小程序
  - 头条小程序

## 项目结构

```
uniapp/
├── api/                    # API接口
│   ├── banner.js          # 轮播图接口
│   ├── cart.js            # 购物车接口
│   ├── coupon.js          # 优惠券接口
│   ├── order.js           # 订单接口
│   ├── product.js         # 商品接口
│   └── user.js            # 用户接口
├── pages/                  # 页面
│   ├── index/             # 首页
│   ├── category/          # 分类页
│   ├── cart/              # 购物车
│   ├── user/              # 个人中心
│   ├── login/             # 登录
│   ├── register/          # 注册
│   ├── product/           # 商品详情
│   ├── order/             # 订单
│   ├── coupon/            # 优惠券
│   └── scan/              # 扫码
├── static/                 # 静态资源
│   ├── tabbar/            # 底部导航图标
│   └── icon/              # 功能图标
├── utils/                  # 工具函数
│   ├── request.js         # 网络请求封装
│   ├── storage.js         # 本地存储
│   └── util.js            # 通用工具
├── App.vue                # 应用入口
├── main.js                # 主入口文件
├── manifest.json          # 应用配置
└── pages.json             # 页面配置
```

## 快速开始

### 环境要求

- Node.js 12+
- HBuilderX (推荐) 或 uni-app CLI
- 微信开发者工具（开发小程序时）

### 安装步骤

1. **使用 HBuilderX**
   - 打开 HBuilderX
   - 文件 -> 导入 -> 从本地目录导入
   - 选择 `uniapp` 目录
   - 运行 -> 运行到浏览器/小程序/手机

2. **使用 CLI**
   ```bash
   # 进入项目目录
   cd uniapp
   
   # 安装依赖（如果使用 CLI 创建项目）
   npm install
   
   # 运行到H5
   npm run dev:h5
   
   # 运行到微信小程序
   npm run dev:mp-weixin
   
   # 运行到App
   npm run dev:app
   ```

### 配置后端接口

修改 `utils/request.js` 中的 `BASE_URL` 为你的后端地址：

```javascript
const BASE_URL = 'http://your-backend-url:8080'
```

或者在 `manifest.json` 中配置代理（H5开发时）：

```json
"h5": {
  "devServer": {
    "proxy": {
      "/api": {
        "target": "http://localhost:8080",
        "changeOrigin": true,
        "secure": false
      }
    }
  }
}
```

## 主要页面说明

### 首页 (pages/index/index)
- 轮播图展示
- 功能快捷入口（扫码、优惠券、订单）
- 热门商品推荐
- 下拉刷新
- 上拉加载更多

### 商品分类 (pages/category/category)
- 左侧分类导航
- 右侧商品列表
- 分类筛选

### 购物车 (pages/cart/cart)
- 商品列表
- 全选/单选
- 数量调整
- 价格计算
- 结算功能

### 个人中心 (pages/user/user)
- 用户信息展示
- 功能菜单（订单、优惠券、个人信息）
- 退出登录

### 商品详情 (pages/product/detail)
- 商品图片
- 商品信息
- 价格展示（普通价/员工价）
- 库存显示
- 加入购物车
- 立即购买

### 扫码购物 (pages/scan/scan)
- 扫描条形码
- 手动输入条码
- 自动跳转商品详情

### 订单列表 (pages/order/list)
- 订单历史
- 订单状态
- 订单详情
- 取消订单

### 优惠券 (pages/coupon/list & my)
- 可领取优惠券
- 我的优惠券
- 优惠券状态（可用、已使用、已过期）

## API 接口说明

所有接口统一返回格式：

```json
{
  "code": 200,
  "msg": "success",
  "data": {}
}
```

### 认证方式

使用 Token 认证，登录后将 token 保存到本地存储，每次请求自动在 header 中添加：

```
Authorization: <token>
```

### 主要接口

- **用户**
  - POST `/api/user/login` - 登录
  - POST `/api/user/register` - 注册
  - GET `/api/user/info` - 获取用户信息
  - PUT `/api/user/info` - 更新用户信息

- **商品**
  - GET `/api/product/list` - 商品列表
  - GET `/api/product/{id}` - 商品详情
  - GET `/api/product/barcode/{barcode}` - 根据条形码查询

- **购物车**
  - POST `/api/cart` - 添加购物车
  - GET `/api/cart/list` - 购物车列表
  - PUT `/api/cart/{id}` - 更新购物车
  - DELETE `/api/cart/{id}` - 删除购物车项

- **订单**
  - POST `/api/order` - 创建订单
  - GET `/api/order/list` - 订单列表
  - GET `/api/order/{id}` - 订单详情
  - POST `/api/order/{id}/cancel` - 取消订单

- **优惠券**
  - GET `/api/coupon/list` - 优惠券列表
  - POST `/api/coupon/{id}/claim` - 领取优惠券
  - GET `/api/coupon/my` - 我的优惠券

- **轮播图**
  - GET `/api/banner/active` - 获取启用的轮播图

## 开发说明

### 添加新页面

1. 在 `pages` 目录下创建页面文件夹和 `.vue` 文件
2. 在 `pages.json` 中注册页面
3. 使用 `uni.navigateTo` 跳转

### 样式规范

- 使用 rpx 作为尺寸单位（750rpx = 屏幕宽度）
- 遵循移动端设计规范
- 保持风格统一

### 图标资源

项目需要以下图标资源（需自行添加到 `static` 目录）：

**底部导航图标** (static/tabbar/)
- home.png / home-active.png
- category.png / category-active.png
- cart.png / cart-active.png
- user.png / user-active.png

**功能图标** (static/icon/)
- scan.png
- coupon.png
- order.png
- cart-white.png
- delete.png
- profile.png
- logout.png

**其他图片**
- logo.png
- avatar.png
- placeholder.png
- empty-cart.png
- empty-order.png
- empty-coupon.png

## 注意事项

1. **权限配置**
   - 扫码功能需要相机权限
   - 不同平台需要在对应平台配置权限

2. **小程序配置**
   - 微信小程序需要在 `manifest.json` 中配置 appid
   - 需要配置服务器域名白名单

3. **跨域问题**
   - H5 开发时使用代理解决跨域
   - 生产环境需要后端配置 CORS

4. **图片资源**
   - 图片路径使用相对路径或绝对路径
   - 网络图片需要配置合法域名

## 构建发布

### H5
```bash
npm run build:h5
```
生成的文件在 `unpackage/dist/build/h5` 目录

### 微信小程序
```bash
npm run build:mp-weixin
```
生成的文件在 `unpackage/dist/build/mp-weixin` 目录
使用微信开发者工具打开该目录，上传代码

### App
使用 HBuilderX 云打包或本地打包

## 常见问题

### Q: 扫码功能不可用？
A: 
- H5: 需要 HTTPS 环境
- 小程序: 需要用户授权
- App: 需要相机权限

### Q: 网络请求失败？
A: 
- 检查后端服务是否启动
- 检查 BASE_URL 配置是否正确
- 小程序需要配置合法域名

### Q: 图片不显示？
A: 
- 检查图片路径是否正确
- 网络图片需要配置合法域名
- 使用占位图处理加载失败

## 后续优化

- [ ] 添加支付功能
- [ ] 完善订单结算流程
- [ ] 添加商品收藏功能
- [ ] 添加商品评价功能
- [ ] 优化性能和体验
- [ ] 添加多语言支持
- [ ] 添加暗黑模式

## 技术支持

如有问题，请提交 Issue 或联系开发团队。

## 开源协议

MIT License

---

**祝您使用愉快！**
