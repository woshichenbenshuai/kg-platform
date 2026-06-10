import { http,type ApiResponse } from './http'
export interface LoginDto{accessToken:string}
export interface CurrentUserDto{userId:string;username:string;nickname?:string;tenantId?:string;tenants?:Array<{tenantId:string;tenantName:string}>}
export function login(username:string,password:string){return http.post<ApiResponse<LoginDto>>('/auth/login',{username,password})}
export function currentUser(){return http.get<ApiResponse<CurrentUserDto>>('/auth/current-user')}
export function switchTenant(tenantId:string|number){return http.post<ApiResponse<LoginDto>>('/auth/switch-tenant',{tenantId})}
