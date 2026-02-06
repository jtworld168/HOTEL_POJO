// 本地存储工具

// 保存数据
export const setStorage = (key, value) => {
  try {
    uni.setStorageSync(key, value)
    return true
  } catch (e) {
    console.error('保存数据失败', e)
    return false
  }
}

// 获取数据
export const getStorage = (key, defaultValue = null) => {
  try {
    const value = uni.getStorageSync(key)
    return value || defaultValue
  } catch (e) {
    console.error('获取数据失败', e)
    return defaultValue
  }
}

// 删除数据
export const removeStorage = (key) => {
  try {
    uni.removeStorageSync(key)
    return true
  } catch (e) {
    console.error('删除数据失败', e)
    return false
  }
}

// 清空所有数据
export const clearStorage = () => {
  try {
    uni.clearStorageSync()
    return true
  } catch (e) {
    console.error('清空数据失败', e)
    return false
  }
}

// 保存用户信息
export const setUserInfo = (userInfo) => {
  return setStorage('userInfo', userInfo)
}

// 获取用户信息
export const getUserInfo = () => {
  return getStorage('userInfo')
}

// 保存token
export const setToken = (token) => {
  return setStorage('token', token)
}

// 获取token
export const getToken = () => {
  return getStorage('token')
}

// 移除token
export const removeToken = () => {
  return removeStorage('token')
}

// 检查是否登录
export const isLogin = () => {
  return !!getToken()
}

// 退出登录
export const logout = () => {
  removeStorage('token')
  removeStorage('userInfo')
}
