<template>
  <view class="my-coupon-container">
    <!-- Tab切换 -->
    <view class="tab-bar">
      <view 
        class="tab-item" 
        :class="{ 'active': currentTab === 'available' }"
        @tap="switchTab('available')"
      >
        可用
      </view>
      <view 
        class="tab-item" 
        :class="{ 'active': currentTab === 'used' }"
        @tap="switchTab('used')"
      >
        已使用
      </view>
      <view 
        class="tab-item" 
        :class="{ 'active': currentTab === 'expired' }"
        @tap="switchTab('expired')"
      >
        已过期
      </view>
    </view>

    <!-- 优惠券列表 -->
    <view class="coupon-list" v-if="couponList.length > 0">
      <view class="coupon-item" :class="{ 'disabled': currentTab !== 'available' }" v-for="coupon in couponList" :key="coupon.id">
        <view class="coupon-left">
          <view class="coupon-amount" v-if="coupon.type === 'VOUCHER'">
            <text class="amount-symbol">¥</text>
            <text class="amount-value">{{ coupon.amount }}</text>
          </view>
          <view class="coupon-amount" v-else>
            <text class="amount-value">{{ coupon.discount * 10 }}</text>
            <text class="amount-unit">折</text>
          </view>
          <view class="coupon-condition">
            {{ coupon.minAmount > 0 ? `满${coupon.minAmount}可用` : '无门槛' }}
          </view>
        </view>
        
        <view class="coupon-right">
          <text class="coupon-name">{{ coupon.name }}</text>
          <text class="coupon-time">有效期至: {{ formatDate(coupon.endTime) }}</text>
          <text class="coupon-status" v-if="currentTab === 'used'">已使用</text>
          <text class="coupon-status" v-if="currentTab === 'expired'">已过期</text>
        </view>
      </view>
    </view>

    <!-- 空状态 -->
    <view class="empty-coupon" v-else>
      <image src="/static/empty-coupon.png" class="empty-image" mode="aspectFit"></image>
      <text class="empty-text">{{ getEmptyText() }}</text>
    </view>
  </view>
</template>

<script>
import { getUserCouponList } from '@/api/coupon.js'
import { formatDate } from '@/utils/util.js'

export default {
  data() {
    return {
      currentTab: 'available',
      couponList: [],
      page: 1,
      pageSize: 20
    }
  },
  onLoad() {
    this.loadCoupons()
  },
  methods: {
    async loadCoupons() {
      try {
        const data = await getUserCouponList({
          page: this.page,
          size: this.pageSize,
          status: this.currentTab
        })
        this.couponList = data.records || []
      } catch (e) {
        console.error('加载我的优惠券失败', e)
      }
    },
    
    switchTab(tab) {
      this.currentTab = tab
      this.page = 1
      this.loadCoupons()
    },
    
    getEmptyText() {
      const textMap = {
        'available': '暂无可用优惠券',
        'used': '暂无已使用优惠券',
        'expired': '暂无已过期优惠券'
      }
      return textMap[this.currentTab] || '暂无优惠券'
    },
    
    formatDate
  }
}
</script>

<style scoped>
.my-coupon-container {
  min-height: 100vh;
  background-color: #f5f5f5;
}

.tab-bar {
  display: flex;
  background-color: #fff;
  padding: 0 20rpx;
}

.tab-item {
  flex: 1;
  height: 88rpx;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 28rpx;
  color: #666;
  position: relative;
}

.tab-item.active {
  color: #ff6b6b;
  font-weight: bold;
}

.tab-item.active::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 60rpx;
  height: 4rpx;
  background-color: #ff6b6b;
}

.coupon-list {
  padding: 20rpx;
}

.coupon-item {
  display: flex;
  background-color: #fff;
  border-radius: 16rpx;
  overflow: hidden;
  margin-bottom: 20rpx;
}

.coupon-item.disabled {
  opacity: 0.5;
}

.coupon-left {
  width: 200rpx;
  background: linear-gradient(135deg, #ff6b6b 0%, #ff8e53 100%);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 30rpx 20rpx;
  position: relative;
}

.coupon-item.disabled .coupon-left {
  background: linear-gradient(135deg, #999 0%, #bbb 100%);
}

.coupon-left::after {
  content: '';
  position: absolute;
  right: -10rpx;
  top: 50%;
  transform: translateY(-50%);
  width: 20rpx;
  height: 20rpx;
  background-color: #f5f5f5;
  border-radius: 50%;
}

.coupon-amount {
  display: flex;
  align-items: baseline;
  color: #fff;
  margin-bottom: 10rpx;
}

.amount-symbol {
  font-size: 28rpx;
}

.amount-value {
  font-size: 60rpx;
  font-weight: bold;
}

.amount-unit {
  font-size: 32rpx;
  margin-left: 5rpx;
}

.coupon-condition {
  font-size: 22rpx;
  color: rgba(255, 255, 255, 0.9);
}

.coupon-right {
  flex: 1;
  padding: 30rpx;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.coupon-name {
  font-size: 32rpx;
  font-weight: bold;
  color: #333;
  margin-bottom: 10rpx;
}

.coupon-time {
  font-size: 24rpx;
  color: #999;
}

.coupon-status {
  font-size: 26rpx;
  color: #999;
  margin-top: 10rpx;
}

/* 空状态 */
.empty-coupon {
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
