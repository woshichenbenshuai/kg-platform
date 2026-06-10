import { createRouter, createWebHistory } from 'vue-router'
import { useSessionStore } from '@/stores/session'
export const router = createRouter({ history: createWebHistory(), routes: [
  { path: '/login', component: () => import('@/views/LoginView.vue') },
  { path: '/', component: () => import('@/views/ShellView.vue'), children: [
    { path: '', redirect: '/home' }, { path: 'home', component: () => import('@/views/HomeView.vue') }, { path: 'classes', component: () => import('@/views/ClassesView.vue') }, { path: 'students', component: () => import('@/views/StudentsView.vue') }, { path: 'growth', component: () => import('@/views/GrowthView.vue') }, { path: 'leave', component: () => import('@/views/LeaveView.vue') }, { path: 'info', component: () => import('@/views/InfoView.vue') }
  ]}
]})
router.beforeEach(async (to) => { const session = useSessionStore(); if (to.path !== '/login' && !session.token) return '/login'; if (to.path !== '/login' && !session.user) await session.loadUser().catch(() => session.clear()); if (to.path === '/login' && session.token) return '/home'; return true })
