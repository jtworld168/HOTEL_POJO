import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { getCartList as getCartListApi, addToCart as addToCartApi, updateCart as updateCartApi, removeFromCart as removeFromCartApi, clearCart as clearCartApi, type CartItem, type AddCartParams, type UpdateCartParams } from '@/api/cart'
import { ElMessage } from 'element-plus'
import { useUserStore } from './user'

export const useCartStore = defineStore('cart', () => {
  const cartItems = ref<CartItem[]>([])
  const userStore = useUserStore()

  const getCartList = async () => {
    try {
      const { data } = await getCartListApi()
      cartItems.value = data.data
    } catch (error) {
      console.error('获取购物车失败', error)
    }
  }

  const addToCart = async (params: AddCartParams) => {
    try {
      await addToCartApi(params)
      await getCartList()
      ElMessage.success('已加入购物车')
    } catch (error) {
      console.error('加入购物车失败', error)
    }
  }

  const updateCart = async (params: UpdateCartParams) => {
    try {
      await updateCartApi(params)
      await getCartList()
    } catch (error) {
      console.error('更新购物车失败', error)
    }
  }

  const removeFromCart = async (id: number) => {
    try {
      await removeFromCartApi(id)
      await getCartList()
      ElMessage.success('已移除')
    } catch (error) {
      console.error('移除失败', error)
    }
  }

  const clearCart = async () => {
    try {
      await clearCartApi()
      cartItems.value = []
      ElMessage.success('已清空购物车')
    } catch (error) {
      console.error('清空购物车失败', error)
    }
  }

  const cartCount = computed(() => {
    return cartItems.value.reduce((sum, item) => sum + item.quantity, 0)
  })

  const totalAmount = computed(() => {
    const isEmployee = userStore.isEmployee()
    return cartItems.value.reduce((sum, item) => {
      const price = isEmployee && item.employeePrice ? item.employeePrice : item.price
      return sum + price * item.quantity
    }, 0)
  })

  return {
    cartItems,
    cartCount,
    totalAmount,
    getCartList,
    addToCart,
    updateCart,
    removeFromCart,
    clearCart,
  }
})
