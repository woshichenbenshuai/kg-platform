import { http,type ApiResponse } from './http'
export function home(){return http.get<ApiResponse<Record<string,any>>>('/kinder/parent/home')}
export function children(){return http.get<ApiResponse<Record<string,any>[]>>('/kinder/parent/children')}
export function notices(){return http.get<ApiResponse<Record<string,any>[]>>('/kinder/parent/notices')}
export function recipes(date?:string){return http.get<ApiResponse<Record<string,any>[]>>('/kinder/parent/recipes',{params:{date}})}
export function leaveRequests(studentId?:string|number){return http.get<ApiResponse<Record<string,any>[]>>('/kinder/parent/leave-requests',{params:{studentId}})}
export function submitLeaveRequest(data:Record<string,any>){return http.post<ApiResponse<boolean>>('/kinder/parent/leave-requests',data)}
export function growthRecords(studentId?:string|number){return http.get<ApiResponse<Record<string,any>[]>>('/kinder/parent/growth-records',{params:{studentId}})}
