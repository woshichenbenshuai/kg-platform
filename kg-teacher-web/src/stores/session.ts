import { defineStore } from 'pinia'
import { currentUser, type CurrentUserDto } from '@/api/auth'
export const useSessionStore = defineStore('session', {
  state: () => ({ token: localStorage.getItem('kg-teacher-token') || '', user: null as CurrentUserDto | null }),
  actions: { setToken(token: string) { this.token = token; localStorage.setItem('kg-teacher-token', token) }, clear() { this.token = ''; this.user = null; localStorage.removeItem('kg-teacher-token') }, async loadUser() { const res = await currentUser(); this.user = res.data.data } }
})
