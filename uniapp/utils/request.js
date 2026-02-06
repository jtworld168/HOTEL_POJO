// HTTP请求封装
import { BASE_URL } from '../config.js'

// 请求拦截器
const request = (options) => {
  return new Promise((resolve, reject) => {
    // 获取token
    const token = uni.getStorageSync('token')
    
    // 设置默认配置
    const config = {
      url: BASE_URL + options.url,
      method: options.method || 'GET',
      data: options.data || {},
      header: {
        'Content-Type': 'application/json',
        ...options.header
      }
    }
    
    // 添加token到header
    if (token) {
      config.header['Authorization'] = token
    }
    
    // 发起请求
    uni.request({
      ...config,
      success: (res) => {
        // 请求成功
        if (res.statusCode === 200) {
          const data = res.data
          if (data.code === 200) {
            // 业务成功
            resolve(data.data)
          } else {
            // 业务失败
            uni.showToast({
              title: data.msg || '请求失败',
              icon: 'none',
              duration: 2000
            })
            reject(data)
          }
        } else if (res.statusCode === 401) {
          // 未授权，清除token并跳转登录页
          uni.removeStorageSync('token')
          uni.removeStorageSync('userInfo')
          uni.showToast({
            title: '请先登录',
            icon: 'none'
          })
          setTimeout(() => {
            uni.navigateTo({
              url: '/pages/login/login'
            })
          }, 1500)
          reject(res)
        } else {
          // 其他错误
          uni.showToast({
            title: '请求失败',
            icon: 'none'
          })
          reject(res)
        }
      },
      fail: (err) => {
        // 请求失败
        uni.showToast({
          title: '网络错误',
          icon: 'none'
        })
        reject(err)
      }
    })
  })
}

// GET请求
const get = (url, data, options = {}) => {
  return request({
    url,
    method: 'GET',
    data,
    ...options
  })
}

// POST请求
const post = (url, data, options = {}) => {
  return request({
    url,
    method: 'POST',
    data,
    ...options
  })
}

// PUT请求
const put = (url, data, options = {}) => {
  return request({
    url,
    method: 'PUT',
    data,
    ...options
  })
}

// DELETE请求
const del = (url, data, options = {}) => {
  return request({
    url,
    method: 'DELETE',
    data,
    ...options
  })
}

export default {
  request,
  get,
  post,
  put,
  del,
  BASE_URL
}
