// 通用工具函数

// 格式化时间
export const formatTime = (date) => {
  if (!date) return ''
  
  const d = new Date(date)
  const year = d.getFullYear()
  const month = String(d.getMonth() + 1).padStart(2, '0')
  const day = String(d.getDate()).padStart(2, '0')
  const hour = String(d.getHours()).padStart(2, '0')
  const minute = String(d.getMinutes()).padStart(2, '0')
  const second = String(d.getSeconds()).padStart(2, '0')
  
  return `${year}-${month}-${day} ${hour}:${minute}:${second}`
}

// 格式化日期
export const formatDate = (date) => {
  if (!date) return ''
  
  const d = new Date(date)
  const year = d.getFullYear()
  const month = String(d.getMonth() + 1).padStart(2, '0')
  const day = String(d.getDate()).padStart(2, '0')
  
  return `${year}-${month}-${day}`
}

// 格式化金额
export const formatPrice = (price) => {
  if (price === null || price === undefined) return '0.00'
  return Number(price).toFixed(2)
}

// 防抖函数
export const debounce = (fn, delay = 500) => {
  let timer = null
  return function(...args) {
    if (timer) clearTimeout(timer)
    timer = setTimeout(() => {
      fn.apply(this, args)
    }, delay)
  }
}

// 节流函数
export const throttle = (fn, delay = 500) => {
  let lastTime = 0
  return function(...args) {
    const now = Date.now()
    if (now - lastTime > delay) {
      fn.apply(this, args)
      lastTime = now
    }
  }
}

// 图片路径处理
export const getImageUrl = (path) => {
  if (!path) return '/static/placeholder.png'
  
  // 如果是完整URL，直接返回
  if (path.startsWith('http://') || path.startsWith('https://')) {
    return path
  }
  
  // 如果是相对路径，拼接baseUrl
  // 注意：BASE_URL 应该从 config.js 导入，避免硬编码
  // 这里为了保持工具函数的独立性，建议调用时传入完整URL或相对路径
  return path.startsWith('/') ? path : '/' + path
}

// 订单状态文本
export const getOrderStatusText = (status) => {
  const statusMap = {
    'PENDING': '待支付',
    'PAID': '已支付',
    'SHIPPED': '已发货',
    'COMPLETED': '已完成',
    'CANCELLED': '已取消'
  }
  return statusMap[status] || '未知'
}

// 优惠券类型文本
export const getCouponTypeText = (type) => {
  const typeMap = {
    'DISCOUNT': '折扣券',
    'VOUCHER': '代金券'
  }
  return typeMap[type] || '未知'
}

// 用户角色文本
export const getUserRoleText = (role) => {
  const roleMap = {
    'admin': '管理员',
    'employee': '员工',
    'user': '用户'
  }
  return roleMap[role] || '未知'
}
