export interface MenuDto {
  id: number
  menuCode: string
  menuName: string
  menuScope: string
  parentId: number | null
  routePath: string | null
  componentPath: string | null
  icon: string | null
  visible: boolean | null
  keepAlive: boolean | null
  sortNo: number | null
  remarks: string | null
  status: boolean | null
}

export interface PermissionPointDto {
  id: number
  permissionCode: string
  permissionName: string
  permissionType: string
  permissionScope: string
  bindMenuId: number | null
  apiPath: string | null
  apiMethod: string | null
  remarks: string | null
  status: boolean | null
}

export interface CurrentUserDto {
  userId: number
  username: string
  tenantId: number | null
  roleCodes: string[]
  roleNames: string[]
  menus: MenuDto[]
  permissionCodes: string[]
  permissionPoints: PermissionPointDto[]
}
