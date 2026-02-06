<template>
  <view class="coupon-container">
    <!-- 优惠券列表 -->
    <view class="coupon-list" v-if="couponList.length > 0">
      <view class="coupon-item" v-for="coupon in couponList" :key="coupon.id">
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
          <button 
            class="claim-btn" 
            :disabled="coupon.stock <= 0"
            @tap="onClaimCoupon(coupon)"
          >
            {{ coupon.stock > 0 ? '立即领取' : '已领完' }}
          </button>
        </view>
      </view>
    </view>

    <!-- 空状态 -->
    <view class="empty-coupon" v-else>
      <image src="/static/empty-coupon.png" class="empty-image" mode="aspectFit"></image>
      <text class="empty-text">暂无优惠券</text>
    </view>
  </view>
</template>

<script>
import { getCouponList, claimCoupon } from '@/api/coupon.js'
import { formatDate } from '@/utils/util.js'
import { isLogin } from '@/utils/storage.js'

export default {
  data() {
    return {
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
        const data = await getCouponList({
          page: this.page,
          size: this.pageSize,
          status: 1
        })
        this.couponList = data.records || []
      } catch (e) {
        console.error('加载优惠券失败', e)
      }
    },
    
    async onClaimCoupon(coupon) {
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
      
      try {
        await claimCoupon(coupon.id)
        uni.showToast({
          title: '领取成功',
          icon: 'success'
        })
        this.loadCoupons()
      } catch (e) {
        console.error('领取优惠券失败', e)
      }
    },
    
    formatDate
  }
}
</script>

<style scoped>
.coupon-container {
  min-height: 100vh;
  background-color: #f5f5f5;
  padding: 20rpx;
}

.coupon-list {
  padding-bottom: 20rpx;
}

.coupon-item {
  display: flex;
  background-color: #fff;
  border-radius: 16rpx;
  overflow: hidden;
  margin-bottom: 20rpx;
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
  margin-bottom: 20rpx;
}

.claim-btn {
  width: 160rpx;
  height: 60rpx;
  background-color: #ff6b6b;
  color: #fff;
  border: none;
  border-radius: 30rpx;
  font-size: 26rpx;
  align-self: flex-end;
}

.claim-btn[disabled] {
  background-color: #ccc;
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
