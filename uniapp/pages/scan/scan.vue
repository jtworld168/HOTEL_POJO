<template>
  <view class="scan-container">
    <view class="scan-header">
      <text class="scan-title">扫描商品条形码</text>
      <text class="scan-tip">将条形码放入框内即可自动扫描</text>
    </view>

    <view class="scan-area">
      <view class="scan-box">
        <view class="scan-line"></view>
      </view>
    </view>

    <view class="scan-actions">
      <button class="scan-btn" @tap="startScan">开始扫描</button>
      <button class="input-btn" @tap="showInputDialog">手动输入条码</button>
    </view>

    <!-- 手动输入弹窗 -->
    <view class="modal-mask" v-if="showInput" @tap="closeInput">
      <view class="modal-content" @tap.stop>
        <view class="modal-header">
          <text class="modal-title">输入条形码</text>
          <text class="modal-close" @tap="closeInput">×</text>
        </view>
        
        <view class="modal-body">
          <input 
            class="barcode-input" 
            v-model="barcodeInput" 
            placeholder="请输入商品条形码"
            type="text"
          />
        </view>
        
        <view class="modal-footer">
          <button class="confirm-btn" @tap="searchByBarcode">确定</button>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
import { getProductByBarcode } from '@/api/product.js'

export default {
  data() {
    return {
      showInput: false,
      barcodeInput: ''
    }
  },
  methods: {
    startScan() {
      // #ifdef APP-PLUS || H5
      uni.scanCode({
        success: (res) => {
          console.log('扫码结果：', res.result)
          this.searchProduct(res.result)
        },
        fail: (err) => {
          console.error('扫码失败', err)
          uni.showToast({
            title: '扫码失败',
            icon: 'none'
          })
        }
      })
      // #endif
      
      // #ifdef MP-WEIXIN
      uni.scanCode({
        onlyFromCamera: true,
        success: (res) => {
          console.log('扫码结果：', res.result)
          this.searchProduct(res.result)
        },
        fail: (err) => {
          console.error('扫码失败', err)
          uni.showToast({
            title: '扫码失败',
            icon: 'none'
          })
        }
      })
      // #endif
    },
    
    async searchProduct(barcode) {
      if (!barcode) {
        uni.showToast({
          title: '条形码不能为空',
          icon: 'none'
        })
        return
      }
      
      uni.showLoading({
        title: '查询中...'
      })
      
      try {
        const product = await getProductByBarcode(barcode)
        
        uni.hideLoading()
        
        if (product) {
          uni.navigateTo({
            url: `/pages/product/detail?id=${product.id}`
          })
        } else {
          uni.showToast({
            title: '未找到该商品',
            icon: 'none'
          })
        }
      } catch (e) {
        uni.hideLoading()
        console.error('查询商品失败', e)
      }
    },
    
    showInputDialog() {
      this.showInput = true
    },
    
    closeInput() {
      this.showInput = false
      this.barcodeInput = ''
    },
    
    searchByBarcode() {
      this.searchProduct(this.barcodeInput)
      this.closeInput()
    }
  }
}
</script>

<style scoped>
.scan-container {
  min-height: 100vh;
  background-color: #000;
  display: flex;
  flex-direction: column;
}

.scan-header {
  padding: 60rpx 40rpx 40rpx;
  text-align: center;
}

.scan-title {
  display: block;
  font-size: 36rpx;
  color: #fff;
  font-weight: bold;
  margin-bottom: 20rpx;
}

.scan-tip {
  display: block;
  font-size: 26rpx;
  color: rgba(255, 255, 255, 0.7);
}

.scan-area {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40rpx;
}

.scan-box {
  width: 500rpx;
  height: 300rpx;
  border: 4rpx solid #07c160;
  border-radius: 16rpx;
  position: relative;
  overflow: hidden;
}

.scan-line {
  width: 100%;
  height: 4rpx;
  background: linear-gradient(to right, transparent, #07c160, transparent);
  position: absolute;
  top: 0;
  animation: scan 2s linear infinite;
}

@keyframes scan {
  0% {
    top: 0;
  }
  100% {
    top: 100%;
  }
}

.scan-actions {
  padding: 40rpx;
}

.scan-btn, .input-btn {
  width: 100%;
  height: 88rpx;
  border-radius: 12rpx;
  font-size: 32rpx;
  border: none;
  margin-bottom: 20rpx;
}

.scan-btn {
  background-color: #07c160;
  color: #fff;
}

.input-btn {
  background-color: rgba(255, 255, 255, 0.1);
  color: #fff;
  border: 1rpx solid rgba(255, 255, 255, 0.3);
}

/* 弹窗样式 */
.modal-mask {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.7);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 999;
}

.modal-content {
  width: 600rpx;
  background-color: #fff;
  border-radius: 24rpx;
  padding: 40rpx;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30rpx;
}

.modal-title {
  font-size: 32rpx;
  font-weight: bold;
  color: #333;
}

.modal-close {
  font-size: 48rpx;
  color: #999;
}

.modal-body {
  padding: 20rpx 0;
}

.barcode-input {
  width: 100%;
  height: 80rpx;
  background-color: #f5f5f5;
  border-radius: 12rpx;
  padding: 0 24rpx;
  font-size: 28rpx;
}

.confirm-btn {
  width: 100%;
  height: 80rpx;
  background-color: #07c160;
  color: #fff;
  border: none;
  border-radius: 12rpx;
  font-size: 32rpx;
  margin-top: 20rpx;
}
</style>
