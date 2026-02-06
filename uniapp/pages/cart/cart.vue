<template>
  <view class="cart-container">
    <view class="cart-content" v-if="cartList.length > 0">
      <!-- 购物车列表 -->
      <view class="cart-list">
        <view class="cart-item" v-for="item in cartList" :key="item.id">
          <checkbox class="cart-checkbox" :checked="item.checked" @tap="onCheckItem(item)"></checkbox>
          
          <image :src="getImageUrl(item.productImage)" mode="aspectFill" class="item-image"></image>
          
          <view class="item-info">
            <text class="item-name">{{ item.productName }}</text>
            <view class="item-price-box">
              <text class="item-price">¥{{ formatPrice(item.price) }}</text>
            </view>
            
            <view class="item-quantity">
              <view class="quantity-btn" @tap="decreaseQuantity(item)">-</view>
              <text class="quantity-value">{{ item.quantity }}</text>
              <view class="quantity-btn" @tap="increaseQuantity(item)">+</view>
            </view>
          </view>
          
          <view class="item-delete" @tap="onDeleteItem(item)">
            <image src="/static/icon/delete.png" class="delete-icon"></image>
          </view>
        </view>
      </view>
    </view>

    <!-- 空购物车 -->
    <view class="empty-cart" v-else>
      <image src="/static/empty-cart.png" class="empty-image" mode="aspectFit"></image>
      <text class="empty-text">购物车空空如也</text>
      <button class="go-shopping-btn" @tap="goShopping">去逛逛</button>
    </view>

    <!-- 底部结算栏 -->
    <view class="bottom-bar" v-if="cartList.length > 0">
      <checkbox class="all-checkbox" :checked="allChecked" @tap="onCheckAll">全选</checkbox>
      
      <view class="price-info">
        <text class="total-label">合计:</text>
        <text class="total-price">¥{{ formatPrice(totalPrice) }}</text>
      </view>
      
      <view class="checkout-btn" @tap="onCheckout">
        结算({{ checkedCount }})
      </view>
    </view>
  </view>
</template>

<script>
import { getCartList, updateCart, deleteCart, clearCart } from '@/api/cart.js'
import { getImageUrl, formatPrice } from '@/utils/util.js'

export default {
  data() {
    return {
      cartList: []
    }
  },
  computed: {
    allChecked() {
      return this.cartList.length > 0 && this.cartList.every(item => item.checked)
    },
    
    checkedCount() {
      return this.cartList.filter(item => item.checked).length
    },
    
    totalPrice() {
      return this.cartList
        .filter(item => item.checked)
        .reduce((total, item) => total + item.price * item.quantity, 0)
    }
  },
  onShow() {
    this.loadCart()
  },
  methods: {
    async loadCart() {
      try {
        const data = await getCartList()
        this.cartList = (data || []).map(item => ({
          ...item,
          checked: false
        }))
      } catch (e) {
        console.error('加载购物车失败', e)
      }
    },
    
    onCheckItem(item) {
      item.checked = !item.checked
      this.$forceUpdate()
    },
    
    onCheckAll() {
      const checked = !this.allChecked
      this.cartList.forEach(item => {
        item.checked = checked
      })
      this.$forceUpdate()
    },
    
    async increaseQuantity(item) {
      if (item.quantity >= item.stock) {
        uni.showToast({
          title: '库存不足',
          icon: 'none'
        })
        return
      }
      
      try {
        await updateCart(item.id, {
          quantity: item.quantity + 1
        })
        item.quantity++
        this.$forceUpdate()
      } catch (e) {
        console.error('更新数量失败', e)
      }
    },
    
    async decreaseQuantity(item) {
      if (item.quantity <= 1) {
        return
      }
      
      try {
        await updateCart(item.id, {
          quantity: item.quantity - 1
        })
        item.quantity--
        this.$forceUpdate()
      } catch (e) {
        console.error('更新数量失败', e)
      }
    },
    
    onDeleteItem(item) {
      uni.showModal({
        title: '提示',
        content: '确定要删除该商品吗？',
        success: async (res) => {
          if (res.confirm) {
            try {
              await deleteCart(item.id)
              this.loadCart()
              uni.showToast({
                title: '删除成功',
                icon: 'success'
              })
            } catch (e) {
              console.error('删除失败', e)
            }
          }
        }
      })
    },
    
    onCheckout() {
      const checkedItems = this.cartList.filter(item => item.checked)
      
      if (checkedItems.length === 0) {
        uni.showToast({
          title: '请选择商品',
          icon: 'none'
        })
        return
      }
      
      // TODO: 跳转到结算页面
      uni.showToast({
        title: '功能开发中',
        icon: 'none'
      })
    },
    
    goShopping() {
      uni.switchTab({
        url: '/pages/index/index'
      })
    },
    
    getImageUrl,
    formatPrice
  }
}
</script>

<style scoped>
.cart-container {
  min-height: 100vh;
  background-color: #f5f5f5;
  padding-bottom: 120rpx;
}

.cart-list {
  padding: 20rpx;
}

.cart-item {
  display: flex;
  align-items: center;
  background-color: #fff;
  border-radius: 16rpx;
  padding: 20rpx;
  margin-bottom: 20rpx;
}

.cart-checkbox {
  margin-right: 20rpx;
}

.item-image {
  width: 160rpx;
  height: 160rpx;
  border-radius: 12rpx;
  margin-right: 20rpx;
}

.item-info {
  flex: 1;
}

.item-name {
  font-size: 28rpx;
  color: #333;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  overflow: hidden;
  margin-bottom: 10rpx;
}

.item-price-box {
  margin-bottom: 20rpx;
}

.item-price {
  font-size: 32rpx;
  color: #ff4444;
  font-weight: bold;
}

.item-quantity {
  display: flex;
  align-items: center;
}

.quantity-btn {
  width: 50rpx;
  height: 50rpx;
  border: 1rpx solid #e5e5e5;
  border-radius: 8rpx;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 28rpx;
  color: #333;
}

.quantity-value {
  width: 80rpx;
  text-align: center;
  font-size: 28rpx;
  color: #333;
}

.item-delete {
  margin-left: 20rpx;
}

.delete-icon {
  width: 48rpx;
  height: 48rpx;
}

/* 空购物车 */
.empty-cart {
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
  margin-bottom: 40rpx;
}

.go-shopping-btn {
  width: 400rpx;
  height: 80rpx;
  background-color: #07c160;
  color: #fff;
  border: none;
  border-radius: 40rpx;
  font-size: 28rpx;
}

/* 底部结算栏 */
.bottom-bar {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  display: flex;
  align-items: center;
  background-color: #fff;
  padding: 20rpx;
  box-shadow: 0 -2rpx 10rpx rgba(0, 0, 0, 0.1);
}

.all-checkbox {
  margin-right: 20rpx;
}

.price-info {
  flex: 1;
  text-align: right;
  margin-right: 20rpx;
}

.total-label {
  font-size: 28rpx;
  color: #333;
}

.total-price {
  font-size: 36rpx;
  color: #ff4444;
  font-weight: bold;
  margin-left: 10rpx;
}

.checkout-btn {
  width: 200rpx;
  height: 80rpx;
  background-color: #ff4444;
  color: #fff;
  border-radius: 40rpx;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 28rpx;
}
</style>
