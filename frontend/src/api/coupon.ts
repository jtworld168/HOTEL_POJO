import request, { Result } from './request'

export interface Coupon {
  id: number
  name: string
  description: string
  discountType: string
  discountValue: number
  minAmount: number
  startTime: string
  endTime: string
  isClaimed?: boolean
}

export const getCouponList = () => {
  return request.get<Result<Coupon[]>>('/coupon/list')
}

export const claimCoupon = (id: number) => {
  return request.post<Result<void>>(`/coupon/claim/${id}`)
}

export const getUserCoupons = () => {
  return request.get<Result<Coupon[]>>('/coupon/user')
}
