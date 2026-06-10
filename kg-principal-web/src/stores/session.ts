import { defineStore } from 'pinia'
import { currentUser, switchTenant, type CurrentUserDto } from '@/api/auth'

export const useSessionStore = defineStore('session', {
  state: () => ({
    token: localStorage.getItem('kg-principal-token') || '',
    user: null as CurrentUserDto | null,
    loading: false
  }),
  actions: {
    setToken(token: string) { this.token = token; localStorage.setItem('kg-principal-token', token) },
    clear() { this.token = ''; this.user = null; localStorage.removeItem('kg-principal-token') },
    async loadUser() { const res = await currentUser(); this.user = res.data.data; return this.user },
    async changeTenant(tenantId: string) { const res = await switchTenant(tenantId); this.setToken(res.data.data.accessToken); await this.loadUser() }
  }
})
