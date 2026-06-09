import http from '../http'

export function fetchStudents(params: any) {
  return http.get('/kinder/students/pages', { params })
}

export function createStudent(data: any) {
  return http.post('/kinder/students', data)
}

export function updateStudent(data: any) {
  return http.put('/kinder/students', data)
}

export function deleteStudent(id: number | string) {
  return http.delete('/kinder/students', { params: { id } })
}
