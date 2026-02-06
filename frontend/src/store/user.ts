import { defineStore } from 'pinia'
import { ref } from 'vue'
import { login as loginApi, getUserInfo as getUserInfoApi, type LoginParams, type UserInfo } from '@/api/user'
import { setToken, removeToken } from '@/utils/auth'
import { ElMessage } from 'element-plus'

export const useUserStore = defineStore('user', () => {
  const userInfo = ref<UserInfo | null>(null)
  const token = ref<string>('')

  const login = async (params: LoginParams) => {
    try {
      const { data } = await loginApi(params)
      token.value = data.data.token
      setToken(data.data.token)
      await getUserInfo()
      ElMessage.success('登录成功')
      return true
    } catch (error) {
      return false
    }
  }

  const getUserInfo = async () => {
    try {
      const { data } = await getUserInfoApi()
      userInfo.value = data.data
    } catch (error) {
      console.error('获取用户信息失败', error)
    }
  }

  const logout = () => {
    userInfo.value = null
    token.value = ''
    removeToken()
    ElMessage.success('退出成功')
  }

  const isEmployee = () => {
    return userInfo.value?.role === 'employee' || userInfo.value?.role === 'admin'
  }

  return {
    userInfo,
    token,
    login,
    getUserInfo,
    logout,
    isEmployee,
  }
})
