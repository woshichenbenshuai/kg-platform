import http from '../http'

export function fetchRoles(params: any) {
  return http.get('/roles/pages', { params })
}
