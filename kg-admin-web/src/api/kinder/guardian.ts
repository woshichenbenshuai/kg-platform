import http from '../http'

export function fetchGuardians(params: any) {
  return http.get('/kinder/guardians/pages', { params })
}

export function createGuardian(data: any) {
  return http.post('/kinder/guardians', data)
}

export function updateGuardian(data: any) {
  return http.put('/kinder/guardians', data)
}

export function deleteGuardian(id: number | string) {
  return http.delete('/kinder/guardians', { params: { id } })
}

export function openParentAccount(data: any) {
  return http.post('/kinder/guardians/open-account', data)
}
