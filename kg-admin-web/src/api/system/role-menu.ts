import http from '../http'

export function fetchRoleMenus(params: any) {
  return http.get('/role-menus/pages', { params })
}

export function createRoleMenu(data: any) {
  return http.post('/role-menus', data)
}

export function batchSaveRoleMenus(data: any) {
  return http.post('/role-menus/batch-save', data)
}

export function updateRoleMenu(data: any) {
  return http.put('/role-menus', data)
}

export function deleteRoleMenu(id: number | string) {
  return http.delete('/role-menus', { params: { id } })
}
