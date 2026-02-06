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
  return request.post<Result<void>>('/cart/add', data)
}

export const getCartList = () => {
  return request.get<Result<CartItem[]>>('/cart/list')
}

export const updateCart = (data: UpdateCartParams) => {
  return request.put<Result<void>>('/cart/update', data)
}

export const removeFromCart = (id: number) => {
  return request.delete<Result<void>>(`/cart/${id}`)
}

export const clearCart = () => {
  return request.delete<Result<void>>('/cart/clear')
}
