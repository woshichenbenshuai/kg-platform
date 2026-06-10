import { createRouter, createWebHistory } from 'vue-router'
import { useSessionStore } from '@/stores/session'
import { modules } from '@/api/portal'

export const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/login', component: () => import('@/views/LoginView.vue') },
    {
      path: '/', component: () => import('@/views/ShellView.vue'), children: [
        { path: '', redirect: '/home' },
        { path: 'home', component: () => import('@/views/HomeView.vue'), meta: { title: '园所管理' } },
        { path: 'module/:moduleKey', name: 'module', component: () => import('@/views/ModuleView.vue') }
      ]
    }
  ]
})

router.beforeEach(async (to) => {
  const session = useSessionStore()
  if (to.path !== '/login' && !session.token) return '/login'
  if (to.path !== '/login' && !session.user) await session.loadUser().catch(() => session.clear())
  if (to.path === '/login' && session.token) return '/home'
  if (to.name === 'module' && !modules[String(to.params.moduleKey)]) return '/module/classes'
  return true
})
