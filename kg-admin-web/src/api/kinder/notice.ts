import http from '../http'

export function fetchNotices(params: any) {
  return http.get('/kinder/notices/pages', { params })
}

export function createNotice(data: any) {
  return http.post('/kinder/notices', data)
}

export function updateNotice(data: any) {
  return http.put('/kinder/notices', data)
}

export function deleteNotice(id: number | string) {
  return http.delete('/kinder/notices', { params: { id } })
}
