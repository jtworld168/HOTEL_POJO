// 轮播图相关API
import request from '@/utils/request.js'

// 获取轮播图列表
export const getBannerList = (params) => {
  return request.get('/api/banner/list', params)
}

// 获取启用的轮播图
export const getActiveBanners = () => {
  return request.get('/api/banner/active')
}

// 创建轮播图（管理员）
export const createBanner = (data) => {
  return request.post('/api/banner', data)
}

// 更新轮播图（管理员）
export const updateBanner = (data) => {
  return request.put('/api/banner', data)
}

// 删除轮播图（管理员）
export const deleteBanner = (id) => {
  return request.del(`/api/banner/${id}`)
}
