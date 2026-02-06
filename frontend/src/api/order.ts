import request, { Result } from './request'

export interface Order {
  id: number
  orderNo: string
  totalAmount: number
  status: string
  createTime: string
  items: OrderItem[]
}

export interface OrderItem {
  productName: string
  price: number
  quantity: number
}

export interface CreateOrderParams {
  couponId?: number
}

export const createOrder = (data: CreateOrderParams) => {
  return request.post<Result<Order>>('/order/create', data)
}

export const getOrderList = () => {
  return request.get<Result<Order[]>>('/order/list')
}
