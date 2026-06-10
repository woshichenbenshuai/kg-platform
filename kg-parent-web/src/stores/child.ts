import { defineStore } from 'pinia'
import { children } from '@/api/parent'

export interface ParentChild {
  id: string | number
  studentName?: string
  studentNo?: string
  className?: string
  gradeName?: string
  relationType?: string
  [key: string]: unknown
}

export const useChildStore = defineStore('child', {
  state: () => ({
    children: [] as ParentChild[],
    currentChildId: localStorage.getItem('kg-parent-current-child-id') || ''
  }),
  getters: {
    currentChild(state) {
      return state.children.find((child) => String(child.id) === String(state.currentChildId)) || state.children[0] || null
    }
  },
  actions: {
    async loadChildren() {
      const response = await children()
      this.children = response.data.data as ParentChild[]
      if (!this.children.some((child) => String(child.id) === String(this.currentChildId))) {
        this.setCurrentChild(this.children[0]?.id || '')
      }
    },
    setCurrentChild(id: string | number) {
      this.currentChildId = id ? String(id) : ''
      if (this.currentChildId) localStorage.setItem('kg-parent-current-child-id', this.currentChildId)
      else localStorage.removeItem('kg-parent-current-child-id')
    }
  }
})
