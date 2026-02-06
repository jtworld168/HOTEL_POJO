# Hotel Store Backend System

A comprehensive backend system for a hotel lobby unmanned convenience store built with Spring Boot 3, MyBatis-Plus, SaToken, and Knife4j.

## Features

### Core Functionality
- **User Management**: Registration, login, profile management with role-based access
- **Product Management**: Full CRUD operations with employee pricing support
- **Shopping Cart**: Add, update, remove items with real-time stock validation
- **Order Processing**: Create orders, apply coupons, cancel orders with automatic stock management
- **Coupon System**: Create coupons, claim coupons, track usage
- **Banner Management**: Carousel/banner image management for storefront

### Technical Highlights
- **Authentication**: SaToken-based JWT authentication
- **Security**: MD5 password encryption, role-based access control
- **Concurrency Safety**: Atomic database operations for stock and coupon management
- **Employee Pricing**: Special pricing for hotel employees
- **API Documentation**: Comprehensive Swagger/Knife4j documentation
- **Exception Handling**: Global exception handler with proper error responses
- **Transaction Management**: @Transactional support with rollback

## Tech Stack

- **Spring Boot**: 3.2.0
- **MyBatis-Plus**: 3.5.5
- **SaToken**: 1.37.0
- **Knife4j**: 4.4.0
- **MySQL**: 8.0+
- **Redis**: For SaToken session management
- **Hutool**: Utility library
- **Lombok**: Code generation

## Project Structure

```
backend/
├── src/main/java/com/hotel/store/
│   ├── common/              # Common classes
│   │   ├── Result.java      # Unified response wrapper
│   │   └── GlobalExceptionHandler.java
│   ├── config/              # Configuration classes
│   │   ├── MyBatisPlusConfig.java
│   │   ├── SaTokenConfig.java
│   │   ├── CorsConfig.java
│   │   └── Knife4jConfig.java
│   ├── controller/          # REST controllers
│   │   ├── UserController.java
│   │   ├── ProductController.java
│   │   ├── CartController.java
│   │   ├── OrderController.java
│   │   ├── CouponController.java
│   │   └── BannerController.java
│   ├── dto/                 # Data Transfer Objects
│   │   ├── LoginRequest.java
│   │   ├── RegisterRequest.java
│   │   ├── UserVO.java
│   │   ├── CartItemVO.java
│   │   ├── OrderVO.java
│   │   └── UserCouponVO.java
│   ├── entity/              # Database entities
│   │   ├── User.java
│   │   ├── Product.java
│   │   ├── Cart.java
│   │   ├── Order.java
│   │   ├── OrderItem.java
│   │   ├── Coupon.java
│   │   ├── UserCoupon.java
│   │   └── Banner.java
│   ├── mapper/              # MyBatis mappers
│   ├── service/             # Service interfaces
│   │   └── impl/            # Service implementations
│   └── HotelStoreApplication.java
└── src/main/resources/
    └── application.yml
```

## API Endpoints

### User Management
- `POST /api/user/login` - User login
- `POST /api/user/register` - User registration
- `GET /api/user/info` - Get user info
- `PUT /api/user/info` - Update user info
- `POST /api/user/logout` - Logout

### Product Management
- `GET /api/product/list` - List products (pagination, filter by category/keyword)
- `GET /api/product/{id}` - Get product by ID
- `GET /api/product/barcode/{barcode}` - Get product by barcode
- `POST /api/product` - Create product (admin only)
- `PUT /api/product` - Update product (admin only)
- `DELETE /api/product/{id}` - Delete product (admin only)

### Shopping Cart
- `POST /api/cart` - Add to cart
- `PUT /api/cart/{cartId}` - Update cart item
- `DELETE /api/cart/{cartId}` - Remove from cart
- `GET /api/cart/list` - Get cart items
- `DELETE /api/cart/clear` - Clear cart

### Order Management
- `POST /api/order` - Create order
- `GET /api/order/list` - List orders (pagination, filter by status)
- `GET /api/order/{orderId}` - Get order details
- `POST /api/order/{orderId}/cancel` - Cancel order

### Coupon Management
- `GET /api/coupon/list` - List coupons (pagination)
- `POST /api/coupon` - Create coupon (admin only)
- `POST /api/coupon/{couponId}/claim` - Claim coupon
- `GET /api/coupon/my` - Get user's coupons

### Banner Management
- `GET /api/banner/list` - List banners (pagination)
- `GET /api/banner/active` - Get active banners
- `POST /api/banner` - Create banner (admin only)
- `PUT /api/banner` - Update banner (admin only)
- `DELETE /api/banner/{id}` - Delete banner (admin only)

## Configuration

### Database Configuration (application.yml)
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/hotel_store
    username: ${DB_USERNAME:root}
    password: ${DB_PASSWORD:root}
```

### Redis Configuration
```yaml
spring:
  data:
    redis:
      host: ${REDIS_HOST:localhost}
      port: ${REDIS_PORT:6379}
```

### SaToken Configuration
```yaml
sa-token:
  token-name: Authorization
  timeout: 2592000  # 30 days
  is-concurrent: true
```

## Running the Application

### Prerequisites
- JDK 17+
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+

### Steps
1. Create database and run SQL script:
```bash
mysql -u root -p < src/main/resources/db.sql
```

2. Configure environment variables (optional):
```bash
export DB_USERNAME=your_username
export DB_PASSWORD=your_password
export REDIS_HOST=localhost
export REDIS_PORT=6379
```

3. Build and run:
```bash
mvn clean package -DskipTests
java -jar target/hotel-store-1.0.0.jar
```

Or using Maven:
```bash
mvn spring-boot:run
```

4. Access API documentation:
```
http://localhost:8080/doc.html
```

## Business Logic

### Employee Pricing
- Products can have both regular price and employee price
- When an employee (role='employee') makes a purchase, the system automatically applies employee pricing if available
- Applied in cart display and order creation

### Coupon System
- Coupons have limited quantity (totalCount, remainCount)
- Users can claim coupons (one per coupon type)
- Atomic database operations prevent overselling
- Coupons can be discount (percentage) or cash (fixed amount)
- Validation checks for expiry, minimum purchase amount, and availability

### Order Processing
1. Validates cart items and stock availability
2. Calculates total with employee pricing if applicable
3. Applies coupon discount if provided
4. Atomically decrements stock (prevents overselling)
5. Marks coupon as used
6. Clears user's shopping cart
7. Generates unique order number with timestamp + counter

### Order Cancellation
- Only pending orders can be cancelled
- Stock is restored atomically
- Coupon is marked as unused (can be reused)

## Security Features

- **Password Encryption**: MD5 hashing
- **Authentication**: SaToken JWT tokens
- **CORS**: Restricted to localhost origins
- **Role-based Access**: Admin-only endpoints protected
- **Input Validation**: Jakarta Validation annotations
- **SQL Injection Prevention**: MyBatis-Plus parameterized queries
- **Concurrency Safety**: Atomic UPDATE operations with WHERE clauses

## Development

### Adding New Endpoints
1. Create DTO classes in `dto/` package
2. Add service method in service interface
3. Implement in service impl with @Transactional
4. Create controller endpoint with Swagger annotations

### Testing
Access Swagger UI at `http://localhost:8080/doc.html` to test all endpoints interactively.

## License

Copyright © 2024 Hotel Store System
