# 酒店大堂无人售货超市系统

一个完整的前后端分离项目，用于酒店大堂无人售货超市管理。

## 🎯 项目概述

本项目是一个现代化的无人售货超市系统，专为酒店大堂设计，支持三种用户角色（管理员、员工、普通用户），提供完整的商品购买、优惠券、订单管理等功能。

### 技术栈

**后端:**
- Spring Boot 3.2.0
- MyBatis-Plus 3.5.5
- SaToken 1.37.0 (认证授权)
- Knife4j 4.4.0 (API文档)
- MySQL 8.0
- Redis

**前端:**
- Vue 3.4.0
- TypeScript 5.3.0
- Vite 5.0.0
- Pinia (状态管理)
- Vue Router 4.2.5
- Element Plus 2.5.0 (UI组件库)
- Axios 1.12.0
- Vue I18n 11.1.2 (国际化)
- ZXing (条形码扫描)

## ✨ 核心功能

### 1. 用户系统
- ✅ 完整的登录注册功能
- ✅ 三种用户角色：管理员(admin)、员工(employee)、普通用户(user)
- ✅ 用户头像展示
- ✅ 个人信息管理
- ✅ 基于SaToken的JWT认证

### 2. 商品管理
- ✅ 商品列表展示
- ✅ 商品详情页面
- ✅ 每个商品唯一条形码标识
- ✅ 商品分类
- ✅ 库存管理
- ✅ 商品图片展示

### 3. 员工内部价
- ✅ 部分商品提供员工专享价格
- ✅ 员工登录后自动显示内部价
- ✅ 结算时自动应用员工价

### 4. 电子优惠券系统
- ✅ 管理员发放和管理优惠券
- ✅ 用户领取优惠券
- ✅ 支持折扣券和代金券两种类型
- ✅ 优惠券使用限制和有效期管理
- ✅ 防止优惠券超领(原子操作)

### 5. 购物车功能
- ✅ 添加商品到购物车
- ✅ 修改购物车商品数量
- ✅ 移除购物车商品
- ✅ 清空购物车
- ✅ 购物车数量徽章显示

### 6. 订单系统
- ✅ 从购物车生成订单
- ✅ 订单列表查看
- ✅ 订单详情展示
- ✅ 订单状态管理
- ✅ 订单取消功能(自动恢复库存)

### 7. 轮播图功能
- ✅ 首页轮播图展示
- ✅ 管理员管理轮播图
- ✅ 点击跳转商品详情

### 8. 扫码功能
- ✅ 调用摄像头扫描条形码
- ✅ 扫码后自动跳转商品详情
- ✅ 支持多种条形码格式

### 9. 中英文切换
- ✅ 完整的国际化支持
- ✅ 中英文一键切换
- ✅ 所有界面文字都支持切换

### 10. 美观界面
- ✅ 简约现代的UI设计
- ✅ 响应式布局
- ✅ Element Plus组件库
- ✅ 流畅的交互体验

## 📁 项目结构

```
HOTEL_POJO/
├── backend/                    # 后端Spring Boot项目
│   ├── src/
│   │   └── main/
│   │       ├── java/com/hotel/store/
│   │       │   ├── HotelStoreApplication.java   # 主启动类
│   │       │   ├── entity/                      # 实体类
│   │       │   ├── mapper/                      # MyBatis Mapper
│   │       │   ├── service/                     # 服务接口
│   │       │   │   └── impl/                    # 服务实现
│   │       │   ├── controller/                  # 控制器
│   │       │   ├── config/                      # 配置类
│   │       │   ├── common/                      # 公共类
│   │       │   └── dto/                         # 数据传输对象
│   │       └── resources/
│   │           ├── application.yml              # 配置文件
│   │           └── db.sql                       # 数据库脚本
│   └── pom.xml                                  # Maven配置
│
├── frontend/                   # 前端Vue 3项目
│   ├── src/
│   │   ├── api/                # API接口
│   │   ├── assets/             # 静态资源
│   │   ├── components/         # 公共组件
│   │   ├── locales/            # 国际化文件
│   │   ├── router/             # 路由配置
│   │   ├── store/              # Pinia状态管理
│   │   ├── utils/              # 工具函数
│   │   ├── views/              # 页面组件
│   │   ├── App.vue             # 根组件
│   │   └── main.ts             # 入口文件
│   ├── index.html              # HTML模板
│   ├── vite.config.ts          # Vite配置
│   ├── tsconfig.json           # TypeScript配置
│   └── package.json            # 依赖配置
│
└── README.md                   # 项目说明
```

## 🚀 快速开始

### 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+
- Node.js 18+
- npm 9+

### 后端启动

1. **创建数据库**
```bash
# 使用MySQL客户端执行
mysql -u root -p < backend/src/main/resources/db.sql
```

2. **配置数据库**
编辑 `backend/src/main/resources/application.yml`
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/hotel_store
    username: root
    password: your_password
```

3. **启动Redis**
```bash
redis-server
```

4. **启动后端**
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

后端启动后访问：
- API文档: http://localhost:8080/doc.html
- 服务端口: http://localhost:8080

### 前端启动

1. **安装依赖**
```bash
cd frontend
npm install
```

2. **启动开发服务器**
```bash
npm run dev
```

前端访问地址: http://localhost:3000

3. **构建生产版本**
```bash
npm run build
```

## 👤 默认账户

**管理员账户:**
- 用户名: `admin`
- 密码: `123456`

## 📡 API文档

启动后端后，访问 http://localhost:8080/doc.html 查看完整API文档。

### 主要接口

**用户模块**
- POST `/user/login` - 用户登录
- POST `/user/register` - 用户注册
- GET `/user/info` - 获取用户信息
- PUT `/user/update` - 更新用户信息

**商品模块**
- GET `/product/list` - 商品列表
- GET `/product/{id}` - 商品详情
- GET `/product/barcode/{barcode}` - 通过条形码查询
- POST `/product/create` - 创建商品(管理员)
- PUT `/product/update` - 更新商品(管理员)
- DELETE `/product/{id}` - 删除商品(管理员)

**购物车模块**
- POST `/cart/add` - 添加到购物车
- GET `/cart/list` - 购物车列表
- PUT `/cart/update` - 更新购物车
- DELETE `/cart/{id}` - 删除购物车项
- DELETE `/cart/clear` - 清空购物车

**订单模块**
- POST `/order/create` - 创建订单
- GET `/order/list` - 订单列表
- GET `/order/{id}` - 订单详情
- PUT `/order/cancel/{id}` - 取消订单

**优惠券模块**
- GET `/coupon/list` - 优惠券列表
- POST `/coupon/create` - 创建优惠券(管理员)
- POST `/coupon/claim/{id}` - 领取优惠券
- GET `/coupon/user` - 我的优惠券

**轮播图模块**
- GET `/banner/list` - 轮播图列表
- POST `/banner/create` - 创建轮播图(管理员)

## 🔒 安全特性

1. **密码加密**: MD5加密存储
2. **JWT认证**: 使用SaToken进行用户认证
3. **角色权限**: 基于角色的访问控制
4. **并发安全**: 原子操作防止超卖
5. **CORS配置**: 仅允许localhost访问
6. **输入验证**: Jakarta Validation参数校验
7. **异常处理**: 全局异常处理器

## 🎨 界面预览

- **首页**: 轮播图 + 商品网格 + 用户头像
- **登录页**: 简洁的登录表单
- **商品详情**: 商品信息 + 条形码 + 加入购物车
- **购物车**: 商品列表 + 数量调整 + 结算
- **订单页**: 订单历史 + 订单详情
- **优惠券**: 可领取优惠券 + 我的优惠券

## 📝 开发说明

### 后端开发

- 使用MyBatis-Plus进行数据库操作
- 统一使用Result类包装响应
- 所有时间字段自动填充
- 逻辑删除配置
- 分页插件支持

### 前端开发

- 使用Composition API
- TypeScript严格模式
- Pinia状态管理
- Vue Router路由守卫
- Axios请求拦截
- Element Plus组件库

## 🐛 已知问题

无

## 📄 License

MIT License

## 👥 作者

HOTEL_POJO Team

---

**祝您使用愉快！如有问题，请提交Issue。**
