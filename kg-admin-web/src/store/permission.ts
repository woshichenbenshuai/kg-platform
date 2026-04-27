import { defineStore } from 'pinia'
import type { CurrentUserDto, MenuDto, PermissionPointDto } from '@/types/auth'

function groupPermissionPoints(permissionPoints: PermissionPointDto[]) {
  return permissionPoints.reduce<Record<number, PermissionPointDto[]>>((result, item) => {
    if (item.bindMenuId == null) {
      return result
    }
    if (!result[item.bindMenuId]) {
      result[item.bindMenuId] = []
    }
    result[item.bindMenuId].push(item)
    return result
  }, {})
}

export const usePermissionStore = defineStore('permission', {
  state: () => ({
    tenantId: null as number | null,
    roleCodes: [] as string[],
    roleNames: [] as string[],
    menus: [] as MenuDto[],
    permissionCodes: [] as string[],
    permissionCodeSet: {} as Record<string, true>,
    permissionPoints: [] as PermissionPointDto[],
    permissionPointsByMenuId: {} as Record<number, PermissionPointDto[]>
  }),
  actions: {
    applyCurrentUser(payload: CurrentUserDto) {
      this.tenantId = payload.tenantId
      this.roleCodes = payload.roleCodes
      this.roleNames = payload.roleNames
      this.menus = payload.menus
      this.permissionCodes = payload.permissionCodes
      this.permissionCodeSet = payload.permissionCodes.reduce<Record<string, true>>((result, code) => {
        result[code] = true
        return result
      }, {})
      this.permissionPoints = payload.permissionPoints
      this.permissionPointsByMenuId = groupPermissionPoints(payload.permissionPoints)
    },
    clearPermission() {
      this.tenantId = null
      this.roleCodes = []
      this.roleNames = []
      this.menus = []
      this.permissionCodes = []
      this.permissionCodeSet = {}
      this.permissionPoints = []
      this.permissionPointsByMenuId = {}
    }
  }
})
