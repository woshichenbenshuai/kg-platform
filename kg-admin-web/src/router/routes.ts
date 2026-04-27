import type { RouteRecordRaw } from 'vue-router'
import BasicLayout from '@/layout/BasicLayout.vue'
import LoginView from '@/views/auth/LoginView.vue'
import DashboardView from '@/views/dashboard/DashboardView.vue'
import RolePermissionView from '@/views/system/role/RolePermissionView.vue'

export const routes: RouteRecordRaw[] = [
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/',
    component: BasicLayout,
    children: [
      {
        path: '',
        name: 'dashboard',
        component: DashboardView
      },
      {
        path: 'system/role-menus',
        name: 'role-menus',
        component: RolePermissionView
      }
    ]
  }
]
