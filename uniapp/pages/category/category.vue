<template>
  <view class="category-container">
    <!-- 分类列表 -->
    <view class="category-sidebar">
      <view 
        class="category-item" 
        :class="{ 'active': currentCategory === category }"
        v-for="category in categories" 
        :key="category"
        @tap="onCategoryChange(category)"
      >
        {{ category }}
      </view>
    </view>

    <!-- 商品列表 -->
    <view class="product-list">
      <view class="product-item" v-for="product in productList" :key="product.id" @tap="onProductClick(product)">
        <image :src="getImageUrl(product.image)" mode="aspectFill" class="product-image"></image>
        <view class="product-info">
          <text class="product-name">{{ product.name }}</text>
          <view class="product-price">¥{{ formatPrice(product.price) }}</view>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
import { getProductList } from '@/api/product.js'
import { getImageUrl, formatPrice } from '@/utils/util.js'

export default {
  data() {
    return {
      categories: ['全部', '饮料', '零食', '日用品', '其他'],
      currentCategory: '全部',
      productList: [],
      page: 1,
      pageSize: 20
    }
  },
  onLoad() {
    this.loadProducts()
  },
  methods: {
    async loadProducts() {
      try {
        const params = {
          page: this.page,
          size: this.pageSize
        }
        
        if (this.currentCategory !== '全部') {
          params.category = this.currentCategory
        }
        
        const data = await getProductList(params)
        this.productList = data.records || []
      } catch (e) {
        console.error('加载商品失败', e)
      }
    },
    
    onCategoryChange(category) {
      this.currentCategory = category
      this.page = 1
      this.loadProducts()
    },
    
    onProductClick(product) {
      uni.navigateTo({
        url: `/pages/product/detail?id=${product.id}`
      })
    },
    
    getImageUrl,
    formatPrice
  }
}
</script>

<style scoped>
.category-container {
  display: flex;
  min-height: 100vh;
  background-color: #f5f5f5;
}

.category-sidebar {
  width: 180rpx;
  background-color: #fff;
  border-right: 1rpx solid #e5e5e5;
}

.category-item {
  height: 100rpx;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 28rpx;
  color: #333;
  border-left: 4rpx solid transparent;
}

.category-item.active {
  background-color: #f5f5f5;
  border-left-color: #07c160;
  color: #07c160;
  font-weight: bold;
}

.product-list {
  flex: 1;
  padding: 20rpx;
}

.product-item {
  display: flex;
  background-color: #fff;
  border-radius: 12rpx;
  padding: 20rpx;
  margin-bottom: 20rpx;
}

.product-image {
  width: 160rpx;
  height: 160rpx;
  border-radius: 12rpx;
  margin-right: 20rpx;
}

.product-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.product-name {
  font-size: 28rpx;
  color: #333;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  overflow: hidden;
}

.product-price {
  font-size: 32rpx;
  color: #ff4444;
  font-weight: bold;
}
</style>
