<template>
  <view class="detail-container">
    <!-- 商品图片 -->
    <view class="image-section">
      <image :src="getImageUrl(product.image)" mode="aspectFill" class="product-image"></image>
    </view>

    <!-- 商品信息 -->
    <view class="info-section">
      <view class="product-name">{{ product.name }}</view>
      
      <view class="price-box">
        <text class="price">¥{{ formatPrice(product.price) }}</text>
        <text class="employee-price" v-if="userRole === 'employee' && product.employeePrice">
          员工价 ¥{{ formatPrice(product.employeePrice) }}
        </text>
      </view>
      
      <view class="stock-box">
        <text class="stock-label">库存:</text>
        <text class="stock-value">{{ product.stock }}</text>
      </view>
      
      <view class="barcode-box">
        <text class="barcode-label">商品条码:</text>
        <text class="barcode-value">{{ product.barcode }}</text>
      </view>
    </view>

    <!-- 商品描述 -->
    <view class="desc-section">
      <view class="section-title">商品详情</view>
      <view class="desc-content">
        {{ product.description || '暂无描述' }}
      </view>
    </view>

    <!-- 底部操作栏 -->
    <view class="bottom-bar">
      <view class="cart-btn" @tap="onAddCart">
        <image src="/static/icon/cart-white.png" class="cart-icon"></image>
        <text class="cart-text">加入购物车</text>
      </view>
      <view class="buy-btn" @tap="onBuyNow">
        立即购买
      </view>
    </view>

    <!-- 数量选择弹窗 -->
    <view class="modal-mask" v-if="showQuantityModal" @tap="closeModal">
      <view class="modal-content" @tap.stop>
        <view class="modal-header">
          <text class="modal-title">选择数量</text>
          <text class="modal-close" @tap="closeModal">×</text>
        </view>
        
        <view class="modal-body">
          <view class="quantity-selector">
            <view class="quantity-btn" @tap="decreaseQuantity">-</view>
            <input class="quantity-input" v-model.number="quantity" type="number" />
            <view class="quantity-btn" @tap="increaseQuantity">+</view>
          </view>
        </view>
        
        <view class="modal-footer">
          <button class="confirm-btn" @tap="confirmAddCart">确定</button>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
import { getProductById } from '@/api/product.js'
import { addCart } from '@/api/cart.js'
import { getImageUrl, formatPrice } from '@/utils/util.js'
import { getUserInfo, isLogin } from '@/utils/storage.js'

export default {
  data() {
    return {
      product: {},
      quantity: 1,
      showQuantityModal: false,
      userRole: '',
      productId: null
    }
  },
  onLoad(options) {
    this.productId = options.id
    this.loadProduct()
    this.loadUserRole()
  },
  methods: {
    async loadProduct() {
      try {
        const data = await getProductById(this.productId)
        this.product = data
      } catch (e) {
        console.error('加载商品失败', e)
        uni.showToast({
          title: '商品不存在',
          icon: 'none'
        })
        setTimeout(() => {
          uni.navigateBack()
        }, 1500)
      }
    },
    
    loadUserRole() {
      const userInfo = getUserInfo()
      this.userRole = userInfo?.role || ''
    },
    
    onAddCart() {
      if (!isLogin()) {
        uni.showToast({
          title: '请先登录',
          icon: 'none'
        })
        setTimeout(() => {
          uni.navigateTo({
            url: '/pages/login/login'
          })
        }, 1500)
        return
      }
      
      this.showQuantityModal = true
    },
    
    async confirmAddCart() {
      if (this.quantity <= 0) {
        uni.showToast({
          title: '数量必须大于0',
          icon: 'none'
        })
        return
      }
      
      if (this.quantity > this.product.stock) {
        uni.showToast({
          title: '库存不足',
          icon: 'none'
        })
        return
      }
      
      try {
        await addCart({
          productId: this.product.id,
          quantity: this.quantity
        })
        
        uni.showToast({
          title: '已加入购物车',
          icon: 'success'
        })
        
        this.closeModal()
      } catch (e) {
        console.error('加入购物车失败', e)
      }
    },
    
    onBuyNow() {
      // TODO: Implement buy now feature - allow direct checkout without adding to cart
      // Requirements:
      // 1. Create order confirmation page
      // 2. Pass product and quantity directly
      // 3. Skip cart step for faster checkout
      uni.showToast({
        title: '功能开发中，请先加入购物车',
        icon: 'none',
        duration: 2000
      })
    },
    
    closeModal() {
      this.showQuantityModal = false
      this.quantity = 1
    },
    
    increaseQuantity() {
      if (this.quantity < this.product.stock) {
        this.quantity++
      }
    },
    
    decreaseQuantity() {
      if (this.quantity > 1) {
        this.quantity--
      }
    },
    
    getImageUrl,
    formatPrice
  }
}
</script>

<style scoped>
.detail-container {
  min-height: 100vh;
  background-color: #f5f5f5;
  padding-bottom: 120rpx;
}

.image-section {
  width: 100%;
  height: 600rpx;
  background-color: #fff;
}

.product-image {
  width: 100%;
  height: 100%;
}

.info-section {
  background-color: #fff;
  padding: 30rpx;
  margin-bottom: 20rpx;
}

.product-name {
  font-size: 36rpx;
  font-weight: bold;
  color: #333;
  margin-bottom: 20rpx;
}

.price-box {
  margin-bottom: 20rpx;
}

.price {
  font-size: 40rpx;
  color: #ff4444;
  font-weight: bold;
}

.employee-price {
  display: block;
  font-size: 28rpx;
  color: #07c160;
  margin-top: 10rpx;
}

.stock-box, .barcode-box {
  display: flex;
  align-items: center;
  margin-bottom: 10rpx;
}

.stock-label, .barcode-label {
  font-size: 28rpx;
  color: #666;
  margin-right: 10rpx;
}

.stock-value, .barcode-value {
  font-size: 28rpx;
  color: #333;
}

.desc-section {
  background-color: #fff;
  padding: 30rpx;
}

.section-title {
  font-size: 32rpx;
  font-weight: bold;
  color: #333;
  margin-bottom: 20rpx;
}

.desc-content {
  font-size: 28rpx;
  color: #666;
  line-height: 1.8;
}

.bottom-bar {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  display: flex;
  background-color: #fff;
  padding: 20rpx;
  box-shadow: 0 -2rpx 10rpx rgba(0, 0, 0, 0.1);
}

.cart-btn {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 80rpx;
  background-color: #ff9500;
  color: #fff;
  border-radius: 40rpx;
  margin-right: 20rpx;
}

.cart-icon {
  width: 40rpx;
  height: 40rpx;
  margin-right: 10rpx;
}

.cart-text {
  font-size: 28rpx;
}

.buy-btn {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 80rpx;
  background-color: #ff4444;
  color: #fff;
  border-radius: 40rpx;
  font-size: 28rpx;
}

/* 弹窗样式 */
.modal-mask {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: flex-end;
  z-index: 999;
}

.modal-content {
  width: 100%;
  background-color: #fff;
  border-radius: 24rpx 24rpx 0 0;
  padding: 40rpx;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30rpx;
}

.modal-title {
  font-size: 32rpx;
  font-weight: bold;
  color: #333;
}

.modal-close {
  font-size: 48rpx;
  color: #999;
}

.modal-body {
  padding: 40rpx 0;
}

.quantity-selector {
  display: flex;
  justify-content: center;
  align-items: center;
}

.quantity-btn {
  width: 60rpx;
  height: 60rpx;
  border: 1rpx solid #e5e5e5;
  border-radius: 8rpx;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 32rpx;
  color: #333;
}

.quantity-input {
  width: 120rpx;
  height: 60rpx;
  text-align: center;
  border-top: 1rpx solid #e5e5e5;
  border-bottom: 1rpx solid #e5e5e5;
  margin: 0 20rpx;
}

.confirm-btn {
  width: 100%;
  height: 80rpx;
  background-color: #07c160;
  color: #fff;
  border: none;
  border-radius: 40rpx;
  font-size: 32rpx;
}
</style>
