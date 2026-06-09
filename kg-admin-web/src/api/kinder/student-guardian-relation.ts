import http from '../http'

export function fetchStudentGuardianRelations(params: any) {
  return http.get('/kinder/student-guardian-relations/pages', { params })
}

export function createStudentGuardianRelation(data: any) {
  return http.post('/kinder/student-guardian-relations', data)
}

export function updateStudentGuardianRelation(data: any) {
  return http.put('/kinder/student-guardian-relations', data)
}

export function deleteStudentGuardianRelation(id: number | string) {
  return http.delete('/kinder/student-guardian-relations', { params: { id } })
}
