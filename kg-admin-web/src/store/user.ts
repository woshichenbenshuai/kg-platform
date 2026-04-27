import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', {
  state: () => ({
    token: localStorage.getItem('kg-token') ?? '',
    userId: 0,
    username: ''
  }),
  actions: {
    setToken(token: string) {
      this.token = token
      localStorage.setItem('kg-token', token)
    },
    setUser(userId: number, username: string) {
      this.userId = userId
      this.username = username
    },
    clearUser() {
      this.token = ''
      this.userId = 0
      this.username = ''
      localStorage.removeItem('kg-token')
    }
  }
})
