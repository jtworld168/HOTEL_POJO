<script>
export default {
  onLaunch: function() {
    console.log('App Launch')
    // 检查登录状态
    const token = uni.getStorageSync('token')
    if (token) {
      // 验证token有效性
      this.checkTokenValidity()
    }
  },
  onShow: function() {
    console.log('App Show')
  },
  onHide: function() {
    console.log('App Hide')
  },
  methods: {
    checkTokenValidity() {
      // 可选：验证token是否仍然有效
      uni.request({
        url: this.globalData.baseUrl + '/api/user/info',
        method: 'GET',
        header: {
          'Authorization': uni.getStorageSync('token')
        },
        fail: () => {
          // token无效，清除
          uni.removeStorageSync('token')
          uni.removeStorageSync('userInfo')
        }
      })
    }
  },
  globalData: {
    baseUrl: 'http://localhost:8080',
    userInfo: null
  }
}
</script>

<style>
/* 全局样式 */
page {
  background-color: #f8f8f8;
  font-size: 28rpx;
  line-height: 1.6;
  color: #333;
}

/* 通用按钮样式 */
.btn-primary {
  background-color: #07c160;
  color: #fff;
  border: none;
  border-radius: 8rpx;
  padding: 24rpx 48rpx;
  font-size: 32rpx;
}

.btn-default {
  background-color: #fff;
  color: #333;
  border: 1px solid #e5e5e5;
  border-radius: 8rpx;
  padding: 24rpx 48rpx;
  font-size: 32rpx;
}

/* 通用容器样式 */
.container {
  padding: 20rpx;
}

.card {
  background-color: #fff;
  border-radius: 16rpx;
  padding: 24rpx;
  margin-bottom: 20rpx;
  box-shadow: 0 2rpx 10rpx rgba(0, 0, 0, 0.05);
}

/* 通用文字样式 */
.text-primary {
  color: #07c160;
}

.text-secondary {
  color: #999;
}

.text-danger {
  color: #ee0a24;
}

.text-center {
  text-align: center;
}

/* flex布局 */
.flex {
  display: flex;
}

.flex-between {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.flex-center {
  display: flex;
  justify-content: center;
  align-items: center;
}

.flex-column {
  display: flex;
  flex-direction: column;
}

/* 间距 */
.mt-10 {
  margin-top: 10rpx;
}

.mt-20 {
  margin-top: 20rpx;
}

.mb-10 {
  margin-bottom: 10rpx;
}

.mb-20 {
  margin-bottom: 20rpx;
}

.ml-10 {
  margin-left: 10rpx;
}

.mr-10 {
  margin-right: 10rpx;
}

/* 图片样式 */
.image-placeholder {
  background-color: #f5f5f5;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
