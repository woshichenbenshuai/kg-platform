import http from '../http'

export function fetchMenus(params: any) {
  return http.get('/menus/pages', { params })
}

export function getMenu(id: number | string) {
  return http.get('/menus', { params: { id } })
}

export function createMenu(data: any) {
  return http.post('/menus', data)
}

export function updateMenu(data: any) {
  return http.put('/menus', data)
}

export function deleteMenu(id: number | string) {
  return http.delete('/menus', { params: { id } })
}
