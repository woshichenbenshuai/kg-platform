import axios from 'axios'
import { useSessionStore } from '@/stores/session'
export interface ApiResponse<T>{code:string;msg?:string;message?:string;data:T}
export const http=axios.create({baseURL:'/api'})
http.interceptors.request.use((config)=>{const token=localStorage.getItem('kg-parent-token');if(token)config.headers.Authorization=`Bearer ${token}`;return config})
http.interceptors.response.use((r)=>r,(e)=>{if(e.response?.status===401||e.response?.data?.code==='401'){useSessionStore().clear();if(location.pathname!=='/login')location.replace('/login')}return Promise.reject(e)})
