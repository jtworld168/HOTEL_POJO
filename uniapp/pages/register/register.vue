<template>
  <view class="register-container">
    <view class="register-box">
      <view class="title-section">
        <text class="title">用户注册</text>
      </view>

      <view class="form-section">
        <view class="input-group">
          <text class="input-label">用户名</text>
          <input 
            class="input-field" 
            v-model="formData.username" 
            placeholder="请输入用户名"
          />
        </view>

        <view class="input-group">
          <text class="input-label">密码</text>
          <input 
            class="input-field" 
            v-model="formData.password" 
            type="password" 
            placeholder="请输入密码"
          />
        </view>

        <view class="input-group">
          <text class="input-label">确认密码</text>
          <input 
            class="input-field" 
            v-model="confirmPassword" 
            type="password" 
            placeholder="请再次输入密码"
          />
        </view>

        <view class="input-group">
          <text class="input-label">昵称</text>
          <input 
            class="input-field" 
            v-model="formData.nickname" 
            placeholder="请输入昵称"
          />
        </view>

        <view class="input-group">
          <text class="input-label">手机号</text>
          <input 
            class="input-field" 
            v-model="formData.phone" 
            type="number" 
            placeholder="请输入手机号"
          />
        </view>

        <view class="input-group">
          <text class="input-label">邮箱</text>
          <input 
            class="input-field" 
            v-model="formData.email" 
            placeholder="请输入邮箱"
          />
        </view>

        <button class="register-btn" @tap="onRegister" :disabled="loading">
          {{ loading ? '注册中...' : '注册' }}
        </button>

        <view class="link-section">
          <text class="link-text" @tap="onLogin">已有账号？立即登录</text>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
import { register } from '@/api/user.js'

export default {
  data() {
    return {
      formData: {
        username: '',
        password: '',
        nickname: '',
        phone: '',
        email: ''
      },
      confirmPassword: '',
      loading: false
    }
  },
  methods: {
    async onRegister() {
      // 验证
      if (!this.formData.username) {
        uni.showToast({
          title: '请输入用户名',
          icon: 'none'
        })
        return
      }
      
      if (!this.formData.password) {
        uni.showToast({
          title: '请输入密码',
          icon: 'none'
        })
        return
      }
      
      if (this.formData.password !== this.confirmPassword) {
        uni.showToast({
          title: '两次密码不一致',
          icon: 'none'
        })
        return
      }
      
      this.loading = true
      
      try {
        await register(this.formData)
        
        uni.showToast({
          title: '注册成功',
          icon: 'success'
        })
        
        setTimeout(() => {
          uni.navigateBack()
        }, 1500)
      } catch (e) {
        console.error('注册失败', e)
      } finally {
        this.loading = false
      }
    },
    
    onLogin() {
      uni.navigateBack()
    }
  }
}
</script>

<style scoped>
.register-container {
  min-height: 100vh;
  background-color: #f5f5f5;
  padding: 40rpx;
}

.register-box {
  background-color: #fff;
  border-radius: 24rpx;
  padding: 40rpx;
}

.title-section {
  text-align: center;
  margin-bottom: 40rpx;
}

.title {
  font-size: 40rpx;
  font-weight: bold;
  color: #333;
}

.input-group {
  margin-bottom: 30rpx;
}

.input-label {
  display: block;
  font-size: 28rpx;
  color: #333;
  margin-bottom: 12rpx;
}

.input-field {
  width: 100%;
  height: 80rpx;
  background-color: #f5f5f5;
  border-radius: 12rpx;
  padding: 0 24rpx;
  font-size: 28rpx;
}

.register-btn {
  width: 100%;
  height: 88rpx;
  background-color: #07c160;
  color: #fff;
  border: none;
  border-radius: 12rpx;
  font-size: 32rpx;
  margin-top: 20rpx;
}

.register-btn[disabled] {
  opacity: 0.6;
}

.link-section {
  text-align: center;
  margin-top: 30rpx;
}

.link-text {
  font-size: 26rpx;
  color: #07c160;
}
</style>
