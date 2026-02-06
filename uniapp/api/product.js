// 商品相关API
import request from '@/utils/request.js'

// 获取商品列表
export const getProductList = (params) => {
  return request.get('/api/product/list', params)
}

// 根据ID获取商品详情
export const getProductById = (id) => {
  return request.get(`/api/product/${id}`)
}

// 根据条形码获取商品
export const getProductByBarcode = (barcode) => {
  return request.get(`/api/product/barcode/${barcode}`)
}

// 创建商品（管理员）
export const createProduct = (data) => {
  return request.post('/api/product', data)
}

// 更新商品（管理员）
export const updateProduct = (data) => {
  return request.put('/api/product', data)
}

// 删除商品（管理员）
export const deleteProduct = (id) => {
  return request.del(`/api/product/${id}`)
}
