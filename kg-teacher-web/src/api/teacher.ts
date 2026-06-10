import { http, type ApiResponse } from './http'
export function home() { return http.get<ApiResponse<Record<string, any>>>('/kinder/teacher-portal/home') }
export function classes() { return http.get<ApiResponse<Record<string, any>[]>>('/kinder/teacher-portal/classes') }
export function students() { return http.get<ApiResponse<Record<string, any>[]>>('/kinder/teacher-portal/students') }
export function notices() { return http.get<ApiResponse<Record<string, any>[]>>('/kinder/teacher-portal/notices') }
export function recipes(date?: string) { return http.get<ApiResponse<Record<string, any>[]>>('/kinder/teacher-portal/recipes', { params: { date } }) }
export function leaveRequests() { return http.get<ApiResponse<Record<string, any>[]>>('/kinder/teacher-portal/leave-requests') }
export function approveLeaveRequest(data: Record<string, any>) { return http.put<ApiResponse<boolean>>('/kinder/teacher-portal/leave-requests/approve', data) }
export function growthRecords() { return http.get<ApiResponse<Record<string, any>[]>>('/kinder/teacher-portal/growth-records') }
export function createGrowth(data: Record<string, any>) { return http.post<ApiResponse<boolean>>('/kinder/teacher-portal/growth-records', data) }
export function updateGrowth(data: Record<string, any>) { return http.put<ApiResponse<boolean>>('/kinder/teacher-portal/growth-records', data) }
export function deleteGrowth(id: string | number) { return http.delete<ApiResponse<boolean>>('/kinder/teacher-portal/growth-records', { params: { id } }) }
