import http from '../http'

export function fetchLeaveRequests(params: any) {
  return http.get('/kinder/leave-requests/pages', { params })
}

export function approveLeaveRequest(data: any) {
  return http.put('/kinder/leave-requests/approve', data)
}

export function deleteLeaveRequest(id: number | string) {
  return http.delete('/kinder/leave-requests', { params: { id } })
}
