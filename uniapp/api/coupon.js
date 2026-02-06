// 优惠券相关API
import request from '@/utils/request.js'

// 获取优惠券列表
export const getCouponList = (params) => {
  return request.get('/api/coupon/list', params)
}

// 创建优惠券（管理员）
export const createCoupon = (data) => {
  return request.post('/api/coupon', data)
}

// 领取优惠券
export const claimCoupon = (couponId) => {
  return request.post(`/api/coupon/${couponId}/claim`)
}

// 获取用户优惠券列表
export const getUserCouponList = (params) => {
  return request.get('/api/coupon/my', params)
}
