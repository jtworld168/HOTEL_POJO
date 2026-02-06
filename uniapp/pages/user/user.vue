<template>
  <view class="user-container">
    <!-- 用户信息区 -->
    <view class="user-header">
      <view class="user-info" v-if="isLogin">
        <image :src="userInfo.avatar || '/static/avatar.png'" class="avatar" mode="aspectFill"></image>
        <view class="user-detail">
          <text class="username">{{ userInfo.nickname || userInfo.username }}</text>
          <text class="user-role">{{ getUserRoleText(userInfo.role) }}</text>
        </view>
      </view>
      <view class="login-prompt" v-else @tap="onLogin">
        <image src="/static/avatar.png" class="avatar" mode="aspectFill"></image>
        <text class="login-text">点击登录</text>
      </view>
    </view>

    <!-- 功能菜单 -->
    <view class="menu-section">
      <view class="menu-group">
        <view class="menu-item" @tap="navigateTo('/pages/order/list')">
          <view class="menu-left">
            <image src="/static/icon/order.png" class="menu-icon"></image>
            <text class="menu-text">我的订单</text>
          </view>
          <text class="menu-arrow">></text>
        </view>

        <view class="menu-item" @tap="navigateTo('/pages/coupon/my')">
          <view class="menu-left">
            <image src="/static/icon/coupon.png" class="menu-icon"></image>
            <text class="menu-text">我的优惠券</text>
          </view>
          <text class="menu-arrow">></text>
        </view>

        <view class="menu-item" @tap="navigateTo('/pages/user/profile')" v-if="isLogin">
          <view class="menu-left">
            <image src="/static/icon/profile.png" class="menu-icon"></image>
            <text class="menu-text">个人信息</text>
          </view>
          <text class="menu-arrow">></text>
        </view>
      </view>

      <view class="menu-group" v-if="isLogin">
        <view class="menu-item" @tap="onLogout">
          <view class="menu-left">
            <image src="/static/icon/logout.png" class="menu-icon"></image>
            <text class="menu-text">退出登录</text>
          </view>
          <text class="menu-arrow">></text>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
import { getUserInfo as getApiUserInfo, logout as apiLogout } from '@/api/user.js'
import { getUserInfo, isLogin, logout } from '@/utils/storage.js'
import { getUserRoleText } from '@/utils/util.js'

export default {
  data() {
    return {
      userInfo: {},
      isLogin: false
    }
  },
  onShow() {
    this.loadUserInfo()
  },
  methods: {
    async loadUserInfo() {
      this.isLogin = isLogin()
      
      if (this.isLogin) {
        const localUserInfo = getUserInfo()
        this.userInfo = localUserInfo || {}
        
        // 从服务器获取最新信息
        try {
          const apiUserInfo = await getApiUserInfo()
          this.userInfo = apiUserInfo
        } catch (e) {
          console.error('获取用户信息失败', e)
        }
      }
    },
    
    onLogin() {
      uni.navigateTo({
        url: '/pages/login/login'
      })
    },
    
    async onLogout() {
      uni.showModal({
        title: '提示',
        content: '确定要退出登录吗？',
        success: async (res) => {
          if (res.confirm) {
            try {
              await apiLogout()
            } catch (e) {
              console.error('退出登录失败', e)
            }
            
            // 清除本地数据
            logout()
            
            uni.showToast({
              title: '已退出登录',
              icon: 'success'
            })
            
            this.loadUserInfo()
          }
        }
      })
    },
    
    navigateTo(url) {
      if (!this.isLogin) {
        uni.showToast({
          title: '请先登录',
          icon: 'none'
        })
        setTimeout(() => {
          this.onLogin()
        }, 1500)
        return
      }
      
      uni.navigateTo({ url })
    },
    
    getUserRoleText
  }
}
</script>

<style scoped>
.user-container {
  min-height: 100vh;
  background-color: #f5f5f5;
}

.user-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 60rpx 40rpx;
}

.user-info, .login-prompt {
  display: flex;
  align-items: center;
}

.avatar {
  width: 120rpx;
  height: 120rpx;
  border-radius: 60rpx;
  margin-right: 30rpx;
  border: 4rpx solid #fff;
}

.user-detail {
  flex: 1;
}

.username {
  display: block;
  font-size: 36rpx;
  font-weight: bold;
  color: #fff;
  margin-bottom: 10rpx;
}

.user-role {
  font-size: 24rpx;
  color: rgba(255, 255, 255, 0.8);
}

.login-text {
  font-size: 32rpx;
  color: #fff;
}

.menu-section {
  padding: 20rpx;
}

.menu-group {
  background-color: #fff;
  border-radius: 16rpx;
  margin-bottom: 20rpx;
  overflow: hidden;
}

.menu-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 30rpx 24rpx;
  border-bottom: 1rpx solid #f5f5f5;
}

.menu-item:last-child {
  border-bottom: none;
}

.menu-left {
  display: flex;
  align-items: center;
}

.menu-icon {
  width: 48rpx;
  height: 48rpx;
  margin-right: 20rpx;
}

.menu-text {
  font-size: 28rpx;
  color: #333;
}

.menu-arrow {
  font-size: 28rpx;
  color: #999;
}
</style>
