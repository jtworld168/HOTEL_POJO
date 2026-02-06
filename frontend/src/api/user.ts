import request, { Result } from './request'

export interface LoginParams {
  username: string
  password: string
}

export interface RegisterParams {
  username: string
  password: string
  email?: string
  phone?: string
}

export interface UserInfo {
  id: number
  username: string
  email?: string
  phone?: string
  role: string
  avatar?: string
}

export interface UpdateUserParams {
  email?: string
  phone?: string
  avatar?: string
}

export const login = (data: LoginParams) => {
  return request.post<Result<{ token: string }>>('/api/user/login', data)
}

export const register = (data: RegisterParams) => {
  return request.post<Result<void>>('/api/user/register', data)
}

export const getUserInfo = () => {
  return request.get<Result<UserInfo>>('/api/user/info')
}

export const updateUser = (data: UpdateUserParams) => {
  return request.put<Result<void>>('/api/user/info', data)
}
