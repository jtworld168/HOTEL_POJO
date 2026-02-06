import request, { Result } from './request'

export interface CartItem {
  id: number
  productId: number
  productName: string
  productImage?: string
  price: number
  employeePrice?: number
  quantity: number
}

export interface AddCartParams {
  productId: number
  quantity: number
}

export interface UpdateCartParams {
  id: number
  quantity: number
}

export const addToCart = (data: AddCartParams) => {
  return request.post<Result<void>>('/api/cart', data)
}

export const getCartList = () => {
  return request.get<Result<CartItem[]>>('/api/cart/list')
}

export const updateCart = (data: UpdateCartParams) => {
  return request.put<Result<void>>(`/api/cart/${data.id}`, { quantity: data.quantity })
}

export const removeFromCart = (id: number) => {
  return request.delete<Result<void>>(`/api/cart/${id}`)
}

export const clearCart = () => {
  return request.delete<Result<void>>('/api/cart/clear')
}
