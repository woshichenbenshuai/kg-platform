import { defineStore } from 'pinia'
import type { CurrentUserDto, MenuDto } from '@/types/auth'

export const usePermissionStore = defineStore('permission', {
  state: () => ({
    tenantId: null as number | null,
    roleCodes: [] as string[],
    roleNames: [] as string[],
    menus: [] as MenuDto[]
  }),
  actions: {
    applyCurrentUser(payload: CurrentUserDto) {
      this.tenantId = payload.tenantId
      this.roleCodes = payload.roleCodes
      this.roleNames = payload.roleNames
      this.menus = payload.menus
    },
    clearPermission() {
      this.tenantId = null
      this.roleCodes = []
      this.roleNames = []
      this.menus = []
    }
  }
})
