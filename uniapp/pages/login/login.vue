<template>
  <view class="login-container">
    <view class="login-box">
      <view class="logo-section">
        <image src="/static/logo.png" class="logo" mode="aspectFit"></image>
        <text class="app-name">酒店无人超市</text>
      </view>

      <view class="form-section">
        <view class="input-group">
          <input 
            class="input-field" 
            v-model="username" 
            placeholder="请输入用户名" 
            placeholder-class="input-placeholder"
          />
        </view>

        <view class="input-group">
          <input 
            class="input-field" 
            v-model="password" 
            type="password" 
            placeholder="请输入密码" 
            placeholder-class="input-placeholder"
          />
        </view>

        <button class="login-btn" @tap="onLogin" :disabled="loading">
          {{ loading ? '登录中...' : '登录' }}
        </button>

        <view class="link-section">
          <text class="link-text" @tap="onRegister">还没有账号？立即注册</text>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
import { login } from '@/api/user.js'
import { setToken, setUserInfo } from '@/utils/storage.js'

export default {
  data() {
    return {
      username: '',
      password: '',
      loading: false
    }
  },
  methods: {
    async onLogin() {
      // 验证
      if (!this.username) {
        uni.showToast({
          title: '请输入用户名',
          icon: 'none'
        })
        return
      }
      
      if (!this.password) {
        uni.showToast({
          title: '请输入密码',
          icon: 'none'
        })
        return
      }
      
      this.loading = true
      
      try {
        const data = await login({
          username: this.username,
          password: this.password
        })
        
        // 保存token和用户信息
        setToken(data.token)
        setUserInfo(data.userInfo)
        
        uni.showToast({
          title: '登录成功',
          icon: 'success'
        })
        
        // 跳转到首页
        setTimeout(() => {
          uni.switchTab({
            url: '/pages/index/index'
          })
        }, 1500)
      } catch (e) {
        console.error('登录失败', e)
      } finally {
        this.loading = false
      }
    },
    
    onRegister() {
      uni.navigateTo({
        url: '/pages/register/register'
      })
    }
  }
}
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40rpx;
}

.login-box {
  width: 100%;
  background-color: #fff;
  border-radius: 24rpx;
  padding: 60rpx 40rpx;
  box-shadow: 0 10rpx 40rpx rgba(0, 0, 0, 0.1);
}

.logo-section {
  text-align: center;
  margin-bottom: 60rpx;
}

.logo {
  width: 160rpx;
  height: 160rpx;
  margin-bottom: 20rpx;
}

.app-name {
  display: block;
  font-size: 40rpx;
  font-weight: bold;
  color: #333;
}

.form-section {
  width: 100%;
}

.input-group {
  margin-bottom: 30rpx;
}

.input-field {
  width: 100%;
  height: 88rpx;
  background-color: #f5f5f5;
  border-radius: 12rpx;
  padding: 0 30rpx;
  font-size: 28rpx;
}

.input-placeholder {
  color: #999;
}

.login-btn {
  width: 100%;
  height: 88rpx;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #fff;
  border: none;
  border-radius: 12rpx;
  font-size: 32rpx;
  margin-top: 20rpx;
}

.login-btn[disabled] {
  opacity: 0.6;
}

.link-section {
  text-align: center;
  margin-top: 30rpx;
}

.link-text {
  font-size: 26rpx;
  color: #667eea;
}
</style>
