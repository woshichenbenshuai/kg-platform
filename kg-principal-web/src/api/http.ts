import axios from 'axios'
import { useSessionStore } from '@/stores/session'

export interface ApiResponse<T> {
  code: string
  msg?: string
  message?: string
  data: T
}

export const http = axios.create({ baseURL: '/api' })

http.interceptors.request.use((config) => {
  const token = localStorage.getItem('kg-principal-token')
  if (token) config.headers.Authorization = `Bearer ${token}`
  return config
})

http.interceptors.response.use(
  (response) => {
    if (response.data?.code === '401') {
      useSessionStore().clear()
      routerToLogin()
      return Promise.reject(new Error('Unauthorized'))
    }
    return response
  },
  (error) => {
    if (error.response?.status === 401 || error.response?.data?.code === '401') {
      useSessionStore().clear()
      routerToLogin()
    }
    return Promise.reject(error)
  }
)

function routerToLogin() {
  if (window.location.pathname !== '/login') window.location.replace('/login')
}
