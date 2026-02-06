<template>
  <view class="order-container">
    <!-- 订单列表 -->
    <view class="order-list" v-if="orderList.length > 0">
      <view class="order-item" v-for="order in orderList" :key="order.id" @tap="onOrderClick(order)">
        <view class="order-header">
          <text class="order-number">订单号: {{ order.orderNumber }}</text>
          <text class="order-status" :class="getStatusClass(order.status)">
            {{ getOrderStatusText(order.status) }}
          </text>
        </view>
        
        <view class="order-products">
          <view class="product-item" v-for="item in order.items" :key="item.id">
            <image :src="getImageUrl(item.productImage)" mode="aspectFill" class="product-image"></image>
            <view class="product-info">
              <text class="product-name">{{ item.productName }}</text>
              <text class="product-spec">x{{ item.quantity }}</text>
            </view>
            <text class="product-price">¥{{ formatPrice(item.price) }}</text>
          </view>
        </view>
        
        <view class="order-footer">
          <view class="order-total">
            <text class="total-label">合计:</text>
            <text class="total-price">¥{{ formatPrice(order.totalAmount) }}</text>
          </view>
          
          <view class="order-actions">
            <button 
              class="action-btn cancel-btn" 
              v-if="order.status === 'PENDING'" 
              @tap.stop="onCancelOrder(order)"
            >
              取消订单
            </button>
          </view>
        </view>
      </view>
    </view>

    <!-- 空订单 -->
    <view class="empty-order" v-else>
      <image src="/static/empty-order.png" class="empty-image" mode="aspectFit"></image>
      <text class="empty-text">暂无订单</text>
    </view>
  </view>
</template>

<script>
import { getOrderList, cancelOrder } from '@/api/order.js'
import { getImageUrl, formatPrice, getOrderStatusText } from '@/utils/util.js'

export default {
  data() {
    return {
      orderList: [],
      page: 1,
      pageSize: 10
    }
  },
  onLoad() {
    this.loadOrders()
  },
  onPullDownRefresh() {
    this.page = 1
    this.loadOrders()
    setTimeout(() => {
      uni.stopPullDownRefresh()
    }, 1000)
  },
  methods: {
    async loadOrders() {
      try {
        const data = await getOrderList({
          page: this.page,
          size: this.pageSize
        })
        this.orderList = data.records || []
      } catch (e) {
        console.error('加载订单失败', e)
      }
    },
    
    onOrderClick(order) {
      uni.navigateTo({
        url: `/pages/order/detail?id=${order.id}`
      })
    },
    
    onCancelOrder(order) {
      uni.showModal({
        title: '提示',
        content: '确定要取消该订单吗？',
        success: async (res) => {
          if (res.confirm) {
            try {
              await cancelOrder(order.id)
              uni.showToast({
                title: '订单已取消',
                icon: 'success'
              })
              this.loadOrders()
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
    getOrderStatusText
  }
}
</script>

<style scoped>
.order-container {
  min-height: 100vh;
  background-color: #f5f5f5;
  padding: 20rpx;
}

.order-list {
  padding-bottom: 20rpx;
}

.order-item {
  background-color: #fff;
  border-radius: 16rpx;
  padding: 24rpx;
  margin-bottom: 20rpx;
}

.order-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-bottom: 20rpx;
  border-bottom: 1rpx solid #f5f5f5;
  margin-bottom: 20rpx;
}

.order-number {
  font-size: 24rpx;
  color: #999;
}

.order-status {
  font-size: 26rpx;
  font-weight: bold;
}

.status-pending { color: #ff9500; }
.status-paid { color: #07c160; }
.status-shipped { color: #10aeff; }
.status-completed { color: #999; }
.status-cancelled { color: #ff4444; }

.order-products {
  margin-bottom: 20rpx;
}

.product-item {
  display: flex;
  align-items: center;
  margin-bottom: 20rpx;
}

.product-item:last-child {
  margin-bottom: 0;
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
  font-size: 24rpx;
  color: #999;
}

.product-price {
  font-size: 28rpx;
  color: #333;
  font-weight: bold;
}

.order-footer {
  padding-top: 20rpx;
  border-top: 1rpx solid #f5f5f5;
}

.order-total {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  margin-bottom: 20rpx;
}

.total-label {
  font-size: 28rpx;
  color: #333;
  margin-right: 10rpx;
}

.total-price {
  font-size: 32rpx;
  color: #ff4444;
  font-weight: bold;
}

.order-actions {
  display: flex;
  justify-content: flex-end;
}

.action-btn {
  padding: 12rpx 40rpx;
  border-radius: 40rpx;
  font-size: 26rpx;
  border: none;
}

.cancel-btn {
  background-color: #fff;
  color: #ff4444;
  border: 1rpx solid #ff4444;
}

/* 空订单 */
.empty-order {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 200rpx 40rpx;
}

.empty-image {
  width: 300rpx;
  height: 300rpx;
  margin-bottom: 40rpx;
}

.empty-text {
  font-size: 28rpx;
  color: #999;
}
</style>
