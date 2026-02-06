import request, { Result } from './request'

export interface Banner {
  id: number
  title: string
  imageUrl: string
  linkUrl?: string
  sort: number
}

export const getBannerList = () => {
  return request.get<Result<Banner[]>>('/api/banner/active')
}
