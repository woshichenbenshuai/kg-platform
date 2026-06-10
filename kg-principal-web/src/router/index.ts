import { createRouter, createWebHistory } from 'vue-router'
import { useSessionStore } from '@/stores/session'

export const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/login', component: () => import('@/views/LoginView.vue') },
    {
      path: '/', component: () => import('@/views/ShellView.vue'), children: [
        { path: '', redirect: '/home' },
        { path: 'home', component: () => import('@/views/HomeView.vue'), meta: { title: '园长工作台' } },
        { path: 'module/:moduleKey', component: () => import('@/views/ModuleView.vue') }
      ]
    }
  ]
})

router.beforeEach(async (to) => {
  const session = useSessionStore()
  if (to.path !== '/login' && !session.token) return '/login'
  if (to.path !== '/login' && !session.user) await session.loadUser().catch(() => session.clear())
  if (to.path === '/login' && session.token) return '/home'
  return true
})
