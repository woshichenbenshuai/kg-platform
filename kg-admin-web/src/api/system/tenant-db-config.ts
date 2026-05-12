import http from '../http'

export function fetchTenantDbConfigs(params: any) {
  return http.get('/tenant-db-configs/pages', { params })
}

export function createTenantDbConfig(data: any) {
  return http.post('/tenant-db-configs', data)
}

export function updateTenantDbConfig(data: any) {
  return http.put('/tenant-db-configs', data)
}

export function testTenantDbConnection(data: any) {
  return http.post('/tenant-db-configs/test-connection', data)
}

export function getTenantSchemaVersion(tenantId: number | string) {
  return http.get('/tenant-db-configs/schema-version', { params: { tenantId } })
}

