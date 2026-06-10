import { defineStore } from 'pinia'
import { currentUser,type CurrentUserDto } from '@/api/auth'
export const useSessionStore=defineStore('session',{state:()=>({token:localStorage.getItem('kg-parent-token')||'',user:null as CurrentUserDto|null}),actions:{setToken(token:string){this.token=token;localStorage.setItem('kg-parent-token',token)},clear(){this.token='';this.user=null;localStorage.removeItem('kg-parent-token');localStorage.removeItem('kg-parent-current-child-id')},async loadUser(){const res=await currentUser();this.user=res.data.data}}})
