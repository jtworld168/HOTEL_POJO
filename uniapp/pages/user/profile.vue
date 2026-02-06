<template>
  <view class="profile-container">
    <view class="profile-section">
      <!-- 头像 -->
      <view class="avatar-section">
        <image :src="userInfo.avatar || '/static/avatar.png'" class="avatar" mode="aspectFill"></image>
        <text class="change-avatar">更换头像</text>
      </view>

      <!-- 信息表单 -->
      <view class="form-section">
        <view class="form-item">
          <text class="form-label">用户名</text>
          <text class="form-value">{{ userInfo.username }}</text>
        </view>

        <view class="form-item">
          <text class="form-label">昵称</text>
          <input 
            class="form-input" 
            v-model="formData.nickname" 
            placeholder="请输入昵称"
          />
        </view>

        <view class="form-item">
          <text class="form-label">手机号</text>
          <input 
            class="form-input" 
            v-model="formData.phone" 
            type="number"
            placeholder="请输入手机号"
          />
        </view>

        <view class="form-item">
          <text class="form-label">邮箱</text>
          <input 
            class="form-input" 
            v-model="formData.email" 
            placeholder="请输入邮箱"
          />
        </view>

        <view class="form-item">
          <text class="form-label">角色</text>
          <text class="form-value">{{ getUserRoleText(userInfo.role) }}</text>
        </view>
      </view>

      <!-- 保存按钮 -->
      <view class="button-section">
        <button class="save-btn" @tap="onSave" :disabled="loading">
          {{ loading ? '保存中...' : '保存' }}
        </button>
      </view>
    </view>
  </view>
</template>

<script>
import { getUserInfo as getApiUserInfo, updateUserInfo } from '@/api/user.js'
import { getUserInfo, setUserInfo } from '@/utils/storage.js'
import { getUserRoleText } from '@/utils/util.js'

export default {
  data() {
    return {
      userInfo: {},
      formData: {
        nickname: '',
        phone: '',
        email: ''
      },
      loading: false
    }
  },
  onLoad() {
    this.loadUserInfo()
  },
  methods: {
    async loadUserInfo() {
      try {
        const data = await getApiUserInfo()
        this.userInfo = data
        this.formData = {
          nickname: data.nickname || '',
          phone: data.phone || '',
          email: data.email || ''
        }
      } catch (e) {
        console.error('加载用户信息失败', e)
      }
    },
    
    async onSave() {
      this.loading = true
      
      try {
        await updateUserInfo(this.formData)
        
        // 更新本地用户信息
        const newUserInfo = {
          ...this.userInfo,
          ...this.formData
        }
        setUserInfo(newUserInfo)
        
        uni.showToast({
          title: '保存成功',
          icon: 'success'
        })
        
        setTimeout(() => {
          uni.navigateBack()
        }, 1500)
      } catch (e) {
        console.error('保存失败', e)
      } finally {
        this.loading = false
      }
    },
    
    getUserRoleText
  }
}
</script>

<style scoped>
.profile-container {
  min-height: 100vh;
  background-color: #f5f5f5;
  padding: 20rpx;
}

.profile-section {
  background-color: #fff;
  border-radius: 16rpx;
  padding: 40rpx;
}

.avatar-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 40rpx;
  padding-bottom: 40rpx;
  border-bottom: 1rpx solid #f5f5f5;
}

.avatar {
  width: 160rpx;
  height: 160rpx;
  border-radius: 80rpx;
  margin-bottom: 20rpx;
}

.change-avatar {
  font-size: 26rpx;
  color: #07c160;
}

.form-section {
  margin-bottom: 40rpx;
}

.form-item {
  display: flex;
  align-items: center;
  padding: 30rpx 0;
  border-bottom: 1rpx solid #f5f5f5;
}

.form-label {
  width: 150rpx;
  font-size: 28rpx;
  color: #333;
}

.form-input {
  flex: 1;
  font-size: 28rpx;
  color: #333;
  text-align: right;
}

.form-value {
  flex: 1;
  font-size: 28rpx;
  color: #999;
  text-align: right;
}

.button-section {
  margin-top: 40rpx;
}

.save-btn {
  width: 100%;
  height: 88rpx;
  background-color: #07c160;
  color: #fff;
  border: none;
  border-radius: 12rpx;
  font-size: 32rpx;
}

.save-btn[disabled] {
  opacity: 0.6;
}
</style>
