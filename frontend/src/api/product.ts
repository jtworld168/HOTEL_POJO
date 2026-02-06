import request, { Result } from './request'

export interface Product {
  id: number
  name: string
  description: string
  price: number
  employeePrice?: number
  stock: number
  category: string
  imageUrl?: string
  barcode?: string
}

export const getProductList = () => {
  return request.get<Result<Product[]>>('/api/product/list')
}

export const getProduct = (id: number) => {
  return request.get<Result<Product>>(`/api/product/${id}`)
}

export const getProductByBarcode = (barcode: string) => {
  return request.get<Result<Product>>(`/api/product/barcode/${barcode}`)
}
