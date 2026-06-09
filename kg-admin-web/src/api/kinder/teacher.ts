import http from '../http'

export function fetchTeachers(params: any) {
  return http.get('/kinder/teachers/pages', { params })
}

export function createTeacher(data: any) {
  return http.post('/kinder/teachers', data)
}

export function updateTeacher(data: any) {
  return http.put('/kinder/teachers', data)
}

export function deleteTeacher(id: number | string) {
  return http.delete('/kinder/teachers', { params: { id } })
}

export function openTeacherAccount(data: any) {
  return http.post('/kinder/teachers/open-account', data)
}
