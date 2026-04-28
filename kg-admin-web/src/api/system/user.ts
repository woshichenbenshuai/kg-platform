import http from '../http'

export function fetchUsers(params: any) {
  return http.get('/users/pages', { params })
}

export function getUser(id: number | string) {
  return http.get('/users', { params: { id } })
}

export function createUser(data: any) {
  return http.post('/users', data)
}

export function updateUser(data: any) {
  return http.put('/users', data)
}

export function deleteUser(id: number | string) {
  return http.delete('/users', { params: { id } })
}
