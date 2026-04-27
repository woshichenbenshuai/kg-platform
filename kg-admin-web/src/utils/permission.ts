import { usePermissionStore } from '@/store/permission'

export function hasPermission(permissionCode: string) {
  const permissionStore = usePermissionStore()
  return Boolean(permissionStore.permissionCodeSet[permissionCode])
}
