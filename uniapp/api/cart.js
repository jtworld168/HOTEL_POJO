// 购物车相关API
import request from '@/utils/request.js'

// 添加购物车
export const addCart = (data) => {
  return request.post('/api/cart', data)
}

// 更新购物车
export const updateCart = (cartId, data) => {
  return request.put(`/api/cart/${cartId}`, data)
}

// 删除购物车项
export const deleteCart = (cartId) => {
  return request.del(`/api/cart/${cartId}`)
}

// 获取购物车列表
export const getCartList = () => {
  return request.get('/api/cart/list')
}

// 清空购物车
export const clearCart = () => {
  return request.del('/api/cart/clear')
}
