<template>
  <view class="detail-container">
    <!-- 订单信息 -->
    <view class="order-section">
      <view class="section-title">订单信息</view>
      <view class="info-row">
        <text class="info-label">订单号:</text>
        <text class="info-value">{{ order.orderNumber }}</text>
      </view>
      <view class="info-row">
        <text class="info-label">下单时间:</text>
        <text class="info-value">{{ formatTime(order.createTime) }}</text>
      </view>
      <view class="info-row">
        <text class="info-label">订单状态:</text>
        <text class="info-value status" :class="getStatusClass(order.status)">
          {{ getOrderStatusText(order.status) }}
        </text>
      </view>
    </view>

    <!-- 商品列表 -->
    <view class="product-section">
      <view class="section-title">商品信息</view>
      <view class="product-item" v-for="item in order.items" :key="item.id">
        <image :src="getImageUrl(item.productImage)" mode="aspectFill" class="product-image"></image>
        <view class="product-info">
          <text class="product-name">{{ item.productName }}</text>
          <view class="product-spec">
            <text class="spec-price">¥{{ formatPrice(item.price) }}</text>
            <text class="spec-quantity">x{{ item.quantity }}</text>
          </view>
        </view>
        <text class="product-total">¥{{ formatPrice(item.price * item.quantity) }}</text>
      </view>
    </view>

    <!-- 费用明细 -->
    <view class="cost-section">
      <view class="section-title">费用明细</view>
      <view class="cost-row">
        <text class="cost-label">商品总额</text>
        <text class="cost-value">¥{{ formatPrice(order.totalAmount) }}</text>
      </view>
      <view class="cost-row" v-if="order.couponAmount > 0">
        <text class="cost-label">优惠券抵扣</text>
        <text class="cost-value discount">-¥{{ formatPrice(order.couponAmount) }}</text>
      </view>
      <view class="cost-row total">
        <text class="cost-label">实付金额</text>
        <text class="cost-value">¥{{ formatPrice(order.totalAmount - (order.couponAmount || 0)) }}</text>
      </view>
    </view>

    <!-- 底部操作栏 -->
    <view class="bottom-bar" v-if="order.status === 'PENDING'">
      <button class="cancel-btn" @tap="onCancelOrder">取消订单</button>
    </view>
  </view>
</template>

<script>
import { getOrderById, cancelOrder } from '@/api/order.js'
import { getImageUrl, formatPrice, formatTime, getOrderStatusText } from '@/utils/util.js'

export default {
  data() {
    return {
      order: {
        items: []
      },
      orderId: null
    }
  },
  onLoad(options) {
    this.orderId = options.id
    this.loadOrderDetail()
  },
  methods: {
    async loadOrderDetail() {
      try {
        const data = await getOrderById(this.orderId)
        this.order = data
      } catch (e) {
        console.error('加载订单详情失败', e)
        uni.showToast({
          title: '订单不存在',
          icon: 'none'
        })
        setTimeout(() => {
          uni.navigateBack()
        }, 1500)
      }
    },
    
    onCancelOrder() {
      uni.showModal({
        title: '提示',
        content: '确定要取消该订单吗？',
        success: async (res) => {
          if (res.confirm) {
            try {
              await cancelOrder(this.orderId)
              uni.showToast({
                title: '订单已取消',
                icon: 'success'
              })
              this.loadOrderDetail()
            } catch (e) {
              console.error('取消订单失败', e)
            }
          }
        }
      })
    },
    
    getStatusClass(status) {
      const classMap = {
        'PENDING': 'status-pending',
        'PAID': 'status-paid',
        'SHIPPED': 'status-shipped',
        'COMPLETED': 'status-completed',
        'CANCELLED': 'status-cancelled'
      }
      return classMap[status] || ''
    },
    
    getImageUrl,
    formatPrice,
    formatTime,
    getOrderStatusText
  }
}
</script>

<style scoped>
.detail-container {
  min-height: 100vh;
  background-color: #f5f5f5;
  padding: 20rpx;
  padding-bottom: 120rpx;
}

.order-section, .product-section, .cost-section {
  background-color: #fff;
  border-radius: 16rpx;
  padding: 30rpx;
  margin-bottom: 20rpx;
}

.section-title {
  font-size: 32rpx;
  font-weight: bold;
  color: #333;
  margin-bottom: 20rpx;
  padding-bottom: 20rpx;
  border-bottom: 1rpx solid #f5f5f5;
}

.info-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20rpx;
}

.info-label {
  font-size: 28rpx;
  color: #666;
}

.info-value {
  font-size: 28rpx;
  color: #333;
}

.info-value.status {
  font-weight: bold;
}

.status-pending { color: #ff9500; }
.status-paid { color: #07c160; }
.status-shipped { color: #10aeff; }
.status-completed { color: #999; }
.status-cancelled { color: #ff4444; }

.product-item {
  display: flex;
  align-items: center;
  margin-bottom: 20rpx;
  padding-bottom: 20rpx;
  border-bottom: 1rpx solid #f5f5f5;
}

.product-item:last-child {
  margin-bottom: 0;
  padding-bottom: 0;
  border-bottom: none;
}

.product-image {
  width: 120rpx;
  height: 120rpx;
  border-radius: 12rpx;
  margin-right: 20rpx;
}

.product-info {
  flex: 1;
}

.product-name {
  font-size: 28rpx;
  color: #333;
  display: block;
  margin-bottom: 10rpx;
}

.product-spec {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.spec-price {
  font-size: 26rpx;
  color: #ff4444;
}

.spec-quantity {
  font-size: 24rpx;
  color: #999;
}

.product-total {
  font-size: 28rpx;
  color: #333;
  font-weight: bold;
  margin-left: 20rpx;
}

.cost-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20rpx;
}

.cost-row:last-child {
  margin-bottom: 0;
}

.cost-label {
  font-size: 28rpx;
  color: #666;
}

.cost-value {
  font-size: 28rpx;
  color: #333;
}

.cost-value.discount {
  color: #ff4444;
}

.cost-row.total {
  padding-top: 20rpx;
  border-top: 1rpx solid #f5f5f5;
  margin-top: 10rpx;
}

.cost-row.total .cost-label,
.cost-row.total .cost-value {
  font-size: 32rpx;
  font-weight: bold;
}

.cost-row.total .cost-value {
  color: #ff4444;
}

.bottom-bar {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  padding: 20rpx;
  background-color: #fff;
  box-shadow: 0 -2rpx 10rpx rgba(0, 0, 0, 0.1);
}

.cancel-btn {
  width: 100%;
  height: 80rpx;
  background-color: #fff;
  color: #ff4444;
  border: 1rpx solid #ff4444;
  border-radius: 40rpx;
  font-size: 28rpx;
}
</style>
