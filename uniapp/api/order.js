// 订单相关API
import request from '@/utils/request.js'

// 创建订单
export const createOrder = (data) => {
  return request.post('/api/order', data)
}

// 获取订单列表
export const getOrderList = (params) => {
  return request.get('/api/order/list', params)
}

// 根据ID获取订单详情
export const getOrderById = (orderId) => {
  return request.get(`/api/order/${orderId}`)
}

// 取消订单
export const cancelOrder = (orderId) => {
  return request.post(`/api/order/${orderId}/cancel`)
}
