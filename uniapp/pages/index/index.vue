<template>
  <view class="container">
    <!-- 轮播图 -->
    <view class="banner-section">
      <swiper class="banner-swiper" indicator-dots autoplay circular :interval="3000">
        <swiper-item v-for="(banner, index) in bannerList" :key="index" @tap="onBannerClick(banner)">
          <image :src="getImageUrl(banner.image)" mode="aspectFill" class="banner-image"></image>
        </swiper-item>
      </swiper>
    </view>

    <!-- 功能区 -->
    <view class="function-section">
      <view class="function-item" @tap="onScan">
        <image src="/static/icon/scan.png" class="function-icon"></image>
        <text class="function-text">扫码购物</text>
      </view>
      <view class="function-item" @tap="onCoupon">
        <image src="/static/icon/coupon.png" class="function-icon"></image>
        <text class="function-text">优惠券</text>
      </view>
      <view class="function-item" @tap="onOrder">
        <image src="/static/icon/order.png" class="function-icon"></image>
        <text class="function-text">我的订单</text>
      </view>
    </view>

    <!-- 商品推荐 -->
    <view class="product-section">
      <view class="section-header">
        <text class="section-title">热门商品</text>
      </view>

      <view class="product-grid">
        <view class="product-item" v-for="product in productList" :key="product.id" @tap="onProductClick(product)">
          <image :src="getImageUrl(product.image)" mode="aspectFill" class="product-image"></image>
          <view class="product-info">
            <text class="product-name">{{ product.name }}</text>
            <view class="product-price-box">
              <text class="product-price">¥{{ formatPrice(product.price) }}</text>
              <text class="employee-price" v-if="userRole === 'employee' && product.employeePrice">
                员工价 ¥{{ formatPrice(product.employeePrice) }}
              </text>
            </view>
            <view class="product-stock">
              <text class="stock-text">库存: {{ product.stock }}</text>
            </view>
          </view>
        </view>
      </view>

      <!-- 加载更多 -->
      <view class="load-more" v-if="hasMore">
        <text class="load-more-text" @tap="loadMore">加载更多</text>
      </view>
    </view>
  </view>
</template>

<script>
import { getActiveBanners } from '@/api/banner.js'
import { getProductList } from '@/api/product.js'
import { getImageUrl, formatPrice } from '@/utils/util.js'
import { getUserInfo } from '@/utils/storage.js'

export default {
  data() {
    return {
      bannerList: [],
      productList: [],
      page: 1,
      pageSize: 10,
      hasMore: true,
      userRole: ''
    }
  },
  onLoad() {
    this.loadBanners()
    this.loadProducts()
    this.loadUserRole()
  },
  onPullDownRefresh() {
    this.page = 1
    this.hasMore = true
    this.loadBanners()
    this.loadProducts()
    setTimeout(() => {
      uni.stopPullDownRefresh()
    }, 1000)
  },
  methods: {
    // 加载轮播图
    async loadBanners() {
      try {
        const data = await getActiveBanners()
        this.bannerList = data || []
      } catch (e) {
        console.error('加载轮播图失败', e)
      }
    },
    
    // 加载商品列表
    async loadProducts() {
      try {
        const data = await getProductList({
          page: this.page,
          size: this.pageSize
        })
        
        if (this.page === 1) {
          this.productList = data.records || []
        } else {
          this.productList = [...this.productList, ...(data.records || [])]
        }
        
        this.hasMore = data.records && data.records.length === this.pageSize
      } catch (e) {
        console.error('加载商品失败', e)
        this.hasMore = false
      }
    },
    
    // 加载用户角色
    loadUserRole() {
      const userInfo = getUserInfo()
      this.userRole = userInfo?.role || ''
    },
    
    // 加载更多
    loadMore() {
      if (!this.hasMore) return
      this.page++
      this.loadProducts()
    },
    
    // 轮播图点击
    onBannerClick(banner) {
      if (banner.productId) {
        uni.navigateTo({
          url: `/pages/product/detail?id=${banner.productId}`
        })
      }
    },
    
    // 商品点击
    onProductClick(product) {
      uni.navigateTo({
        url: `/pages/product/detail?id=${product.id}`
      })
    },
    
    // 扫码购物
    onScan() {
      uni.navigateTo({
        url: '/pages/scan/scan'
      })
    },
    
    // 优惠券
    onCoupon() {
      uni.navigateTo({
        url: '/pages/coupon/list'
      })
    },
    
    // 订单
    onOrder() {
      uni.navigateTo({
        url: '/pages/order/list'
      })
    },
    
    getImageUrl,
    formatPrice
  }
}
</script>

<style scoped>
.container {
  padding-bottom: 20rpx;
}

/* 轮播图 */
.banner-section {
  width: 100%;
  margin-bottom: 20rpx;
}

.banner-swiper {
  width: 100%;
  height: 360rpx;
}

.banner-image {
  width: 100%;
  height: 100%;
}

/* 功能区 */
.function-section {
  display: flex;
  justify-content: space-around;
  padding: 30rpx 0;
  background-color: #fff;
  margin-bottom: 20rpx;
}

.function-item {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.function-icon {
  width: 96rpx;
  height: 96rpx;
  margin-bottom: 10rpx;
}

.function-text {
  font-size: 24rpx;
  color: #666;
}

/* 商品推荐 */
.product-section {
  background-color: #fff;
  padding: 20rpx;
}

.section-header {
  display: flex;
  align-items: center;
  margin-bottom: 20rpx;
}

.section-title {
  font-size: 32rpx;
  font-weight: bold;
  color: #333;
}

.product-grid {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
}

.product-item {
  width: 48%;
  background-color: #f8f8f8;
  border-radius: 12rpx;
  overflow: hidden;
  margin-bottom: 20rpx;
}

.product-image {
  width: 100%;
  height: 300rpx;
}

.product-info {
  padding: 16rpx;
}

.product-name {
  font-size: 28rpx;
  color: #333;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  overflow: hidden;
  height: 80rpx;
}

.product-price-box {
  margin-top: 10rpx;
}

.product-price {
  font-size: 32rpx;
  color: #ff4444;
  font-weight: bold;
}

.employee-price {
  display: block;
  font-size: 24rpx;
  color: #07c160;
  margin-top: 5rpx;
}

.product-stock {
  margin-top: 10rpx;
}

.stock-text {
  font-size: 24rpx;
  color: #999;
}

.load-more {
  text-align: center;
  padding: 30rpx 0;
}

.load-more-text {
  color: #07c160;
  font-size: 28rpx;
}
</style>
