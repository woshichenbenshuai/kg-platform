import http from '../http'

export function fetchClasses(params: any) {
  return http.get('/kinder/kindergarten-classes/pages', { params })
}

export function createClass(data: any) {
  return http.post('/kinder/kindergarten-classes', data)
}

export function updateClass(data: any) {
  return http.put('/kinder/kindergarten-classes', data)
}

export function deleteClass(id: number | string) {
  return http.delete('/kinder/kindergarten-classes', { params: { id } })
}
