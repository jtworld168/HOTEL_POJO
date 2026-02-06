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
  return request.get<Result<Coupon[]>>('/api/coupon/list')
}

export const claimCoupon = (id: number) => {
  return request.post<Result<void>>(`/api/coupon/${id}/claim`)
}

export const getUserCoupons = () => {
  return request.get<Result<Coupon[]>>('/api/coupon/my')
}
