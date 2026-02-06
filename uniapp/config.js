// 应用配置文件

// API 基础地址配置
// 开发环境和生产环境可以配置不同的地址
export const BASE_URL = process.env.NODE_ENV === 'production' 
  ? 'https://your-production-domain.com'  // 生产环境地址
  : 'http://localhost:8080'                // 开发环境地址

// 其他配置
export const CONFIG = {
  // 请求超时时间（毫秒）
  timeout: 10000,
  
  // 分页默认配置
  pageSize: 10,
  
  // 图片上传大小限制（MB）
  maxImageSize: 5,
  
  // Token 存储键名
  tokenKey: 'token',
  
  // 用户信息存储键名
  userInfoKey: 'userInfo'
}

export default {
  BASE_URL,
  CONFIG
}
