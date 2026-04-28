import http from './http'
import type { CurrentUserDto } from '@/types/auth'

interface ApiResponse<T> {
  code: string
  message: string
  data: T
}

interface LoginDto {
  accessToken: string
}

interface LoginVo {
  username: string
  password: string
}

export function login(data: LoginVo) {
  return http.post<ApiResponse<LoginDto>>('/auth/login', data)
}

export function getCurrentUser() {
  return http.get<ApiResponse<CurrentUserDto>>('/auth/current-user')
}
