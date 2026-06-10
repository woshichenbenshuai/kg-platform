import axios from 'axios'
import { useSessionStore } from '@/stores/session'

export interface ApiResponse<T> { code: string; msg?: string; message?: string; data: T }
export const http = axios.create({ baseURL: '/api' })
http.interceptors.request.use((config) => { const token = localStorage.getItem('kg-teacher-token'); if (token) config.headers.Authorization = `Bearer ${token}`; return config })
http.interceptors.response.use((response) => response, (error) => { if (error.response?.status === 401 || error.response?.data?.code === '401') { useSessionStore().clear(); if (location.pathname !== '/login') location.replace('/login') } return Promise.reject(error) })
