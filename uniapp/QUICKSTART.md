# UniApp 快速开始指南

## 方式一：使用 HBuilderX（推荐）

### 1. 下载 HBuilderX
访问 [HBuilderX官网](https://www.dcloud.io/hbuilderx.html) 下载并安装 HBuilderX

### 2. 导入项目
1. 打开 HBuilderX
2. 选择菜单：文件 -> 导入 -> 从本地目录导入
3. 选择 `uniapp` 目录
4. 点击确定

### 3. 运行项目

#### 运行到浏览器（H5）
1. 点击工具栏：运行 -> 运行到浏览器 -> Chrome
2. 或使用快捷键：Ctrl + R (Windows) / Cmd + R (Mac)

#### 运行到微信小程序
1. 安装微信开发者工具
2. 点击：运行 -> 运行到小程序模拟器 -> 微信开发者工具
3. 首次运行需要配置微信开发者工具路径

#### 运行到真机
1. 连接手机（需开启USB调试）
2. 点击：运行 -> 运行到手机或模拟器
3. 选择设备

## 方式二：使用命令行

### 1. 安装依赖
```bash
cd uniapp
npm install
```

### 2. 运行开发服务器

#### H5
```bash
npm run dev:h5
```
访问 http://localhost:8080

#### 微信小程序
```bash
npm run dev:mp-weixin
```
用微信开发者工具打开 `uniapp/unpackage/dist/dev/mp-weixin` 目录

#### 支付宝小程序
```bash
npm run dev:mp-alipay
```

#### 百度小程序
```bash
npm run dev:mp-baidu
```

### 3. 构建生产版本

#### H5
```bash
npm run build:h5
```

#### 微信小程序
```bash
npm run build:mp-weixin
```

## 配置后端接口

### 修改 API 地址

编辑 `utils/request.js`：
```javascript
const BASE_URL = 'http://your-backend-url:8080'
```

### 配置 H5 代理（开发环境）

编辑 `manifest.json`：
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

## 小程序配置

### 微信小程序

1. **配置 AppID**
   - 编辑 `manifest.json`
   - 找到 `mp-weixin` -> `appid`
   - 填入你的小程序 AppID

2. **配置服务器域名**
   - 登录微信公众平台
   - 开发 -> 开发管理 -> 开发设置
   - 服务器域名 -> 配置合法域名
   - 添加后端服务器域名

### 支付宝小程序

1. 编辑 `manifest.json`
2. 配置 `mp-alipay` 相关信息

## 准备静态资源

项目需要以下图标资源，请将图标放到对应目录：

### 底部导航图标 (static/tabbar/)
- home.png (64x64)
- home-active.png (64x64)
- category.png (64x64)
- category-active.png (64x64)
- cart.png (64x64)
- cart-active.png (64x64)
- user.png (64x64)
- user-active.png (64x64)

### 功能图标 (static/icon/)
- scan.png
- coupon.png
- order.png
- cart-white.png
- delete.png
- profile.png
- logout.png

### 其他图片 (static/)
- logo.png
- avatar.png
- placeholder.png
- empty-cart.png
- empty-order.png
- empty-coupon.png

## 测试账号

使用后端提供的默认账号：

**管理员**
- 用户名: admin
- 密码: 123456

**员工**
- 用户名: employee
- 密码: 123456

**普通用户**
- 用户名: user
- 密码: 123456

或者自行注册新账号。

## 开发建议

### 1. 开发环境
- 推荐使用 HBuilderX
- 安装必要的插件
- 配置代码提示

### 2. 调试技巧
- 使用 console.log 调试
- 查看网络请求
- 使用开发者工具

### 3. 常见问题
- 确保后端服务已启动
- 检查网络配置
- 查看控制台错误信息

## 功能测试流程

### 1. 用户注册登录
1. 打开应用
2. 点击"我的"
3. 点击"点击登录"
4. 点击"还没有账号？立即注册"
5. 填写信息注册
6. 返回登录

### 2. 浏览商品
1. 查看首页商品
2. 点击商品查看详情
3. 切换到"分类"查看分类商品

### 3. 扫码购物
1. 点击首页"扫码购物"
2. 允许相机权限
3. 扫描商品条形码
4. 查看商品详情

### 4. 购物车
1. 在商品详情点击"加入购物车"
2. 切换到"购物车"
3. 调整商品数量
4. 选择商品
5. 点击结算

### 5. 订单
1. 点击"我的"
2. 点击"我的订单"
3. 查看订单列表
4. 点击订单查看详情
5. 测试取消订单

### 6. 优惠券
1. 点击首页"优惠券"
2. 浏览可领取优惠券
3. 领取优惠券
4. 点击"我的优惠券"查看

## 发布部署

### H5 部署
1. 构建生产版本：`npm run build:h5`
2. 将 `unpackage/dist/build/h5` 目录部署到服务器
3. 配置 Nginx 或其他 Web 服务器

### 小程序发布
1. 构建生产版本
2. 使用开发者工具打开构建目录
3. 点击"上传"
4. 提交审核
5. 等待审核通过后发布

### App 打包
1. 使用 HBuilderX 云打包
2. 配置签名证书
3. 选择平台（Android/iOS）
4. 提交打包
5. 下载安装包

## 下一步

- 查看 [README.md](README.md) 了解项目功能
- 查看 [DEVELOPMENT.md](DEVELOPMENT.md) 了解开发详情
- 查看后端 API 文档了解接口详情

## 获取帮助

- 查看 [uni-app 官方文档](https://uniapp.dcloud.io/)
- 提交 Issue
- 联系开发团队

---

**祝开发顺利！**
