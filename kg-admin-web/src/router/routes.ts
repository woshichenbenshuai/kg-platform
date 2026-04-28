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
        path: 'system',
        redirect: '/system/users'
      },
      {
        path: 'system/role-menus',
        name: 'role-menus',
        component: RolePermissionView
      },
      {
        path: 'system/users',
        name: 'system-users',
        component: () => import('@/views/system/user/UserView.vue')
      },
      {
        path: 'system/menus',
        name: 'system-menus',
        component: () => import('@/views/system/menu/MenuView.vue')
      },
      {
        path: 'system/dicts',
        name: 'system-dicts',
        component: () => import('@/views/system/dict/DictView.vue')
      }
    ]
  }
]
