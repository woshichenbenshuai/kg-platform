import http from '../http'

export function fetchTenants(params: any) {
  return http.get('/tenants/pages', { params })
}

export function createTenant(data: any) {
  return http.post('/tenants', data)
}

export function rebuildTenantDatabase(id: number | string) {
  return http.post(`/tenants/${id}/database/rebuild`)
}

export function openTenantOperatorAccount(id: number | string, data: any) {
  return http.post(`/tenants/${id}/operator-account`, data)
}

export function updateTenant(data: any) {
  return http.put('/tenants', data)
}

export function deleteTenant(id: number | string) {
  return http.delete('/tenants', { params: { id } })
}
