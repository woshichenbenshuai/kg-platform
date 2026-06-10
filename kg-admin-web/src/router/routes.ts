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
        path: 'platform',
        redirect: '/platform/tenants'
      },
      {
        path: 'platform/tenants',
        name: 'platform-tenants',
        component: () => import('@/views/platform/TenantConfigView.vue')
      },
      {
        path: 'platform/menu',
        name: 'platform-menu',
        component: () => import('@/views/system/menu/MenuView.vue')
      },
      {
        path: 'kinder',
        redirect: '/kinder/teachers'
      },
      {
        path: 'kinder/teachers',
        name: 'kinder-teachers',
        component: () => import('@/views/kinder/teacher/TeacherView.vue')
      },
      {
        path: 'kinder/classes',
        name: 'kinder-classes',
        component: () => import('@/views/kinder/class/ClassView.vue')
      },
      {
        path: 'kinder/students',
        name: 'kinder-students',
        component: () => import('@/views/kinder/student/StudentView.vue')
      },
      {
        path: 'kinder/guardians',
        name: 'kinder-guardians',
        component: () => import('@/views/kinder/guardian/GuardianView.vue')
      },
      {
        path: 'kinder/student-guardian-relations',
        name: 'kinder-student-guardian-relations',
        component: () => import('@/views/kinder/relation/StudentGuardianRelationView.vue')
      },
      {
        path: 'kinder/notices',
        name: 'kinder-notices',
        component: () => import('@/views/kinder/notice/NoticeView.vue')
      },
      {
        path: 'kinder/recipes',
        name: 'kinder-recipes',
        component: () => import('@/views/kinder/recipe/RecipeView.vue')
      },
      {
        path: 'kinder/growth-records',
        name: 'kinder-growth-records',
        component: () => import('@/views/kinder/growth/GrowthRecordView.vue')
      },
      {
        path: 'kinder/leave-requests',
        name: 'kinder-leave-requests',
        component: () => import('@/views/kinder/leave/LeaveRequestView.vue')
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
