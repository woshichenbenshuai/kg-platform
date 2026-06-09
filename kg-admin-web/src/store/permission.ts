import { defineStore } from 'pinia'
import type { CurrentUserDto, CurrentUserTenantDto, MenuDto } from '@/types/auth'

export const usePermissionStore = defineStore('permission', {
  state: () => ({
    initialized: false,
    tenantId: null as number | null,
    roleCodes: [] as string[],
    roleNames: [] as string[],
    menus: [] as MenuDto[],
    tenants: [] as CurrentUserTenantDto[]
  }),
  actions: {
    applyCurrentUser(payload: CurrentUserDto) {
      this.initialized = true
      this.tenantId = payload.tenantId
      this.roleCodes = payload.roleCodes
      this.roleNames = payload.roleNames
      this.menus = payload.menus || []
      this.tenants = payload.tenants || []
    },
    clearPermission() {
      this.initialized = false
      this.tenantId = null
      this.roleCodes = []
      this.roleNames = []
      this.menus = []
      this.tenants = []
    }
  }
})
