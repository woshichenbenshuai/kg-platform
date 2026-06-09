import http from '../http'

export function fetchGrowthRecords(params: any) {
  return http.get('/kinder/growth-records/pages', { params })
}

export function createGrowthRecord(data: any) {
  return http.post('/kinder/growth-records', data)
}

export function updateGrowthRecord(data: any) {
  return http.put('/kinder/growth-records', data)
}

export function deleteGrowthRecord(id: number | string) {
  return http.delete('/kinder/growth-records', { params: { id } })
}
