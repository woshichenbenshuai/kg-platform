import { createRouter, createWebHistory } from 'vue-router'
import { routes } from './routes'
import { getCurrentUser } from '@/api/auth'
import { useUserStore } from '@/store/user'
import { usePermissionStore } from '@/store/permission'

const router = createRouter({
  history: createWebHistory(),
  routes
})

const PLATFORM_ADMIN = 'PLATFORM_ADMIN'

router.beforeEach(async (to) => {
  const userStore = useUserStore()
  const permissionStore = usePermissionStore()

  if (to.path === '/login') {
    if (userStore.token) {
      return '/'
    }
    return true
  }

  if (!userStore.token) {
    permissionStore.clearPermission()
    return '/login'
  }

  if (!permissionStore.initialized) {
    try {
      const response = await getCurrentUser()
      const currentUser = response.data.data
      userStore.setUser(currentUser.userId, currentUser.username)
      permissionStore.applyCurrentUser(currentUser)
    } catch (error) {
      userStore.clearUser()
      permissionStore.clearPermission()
      return `/login?redirect=${encodeURIComponent(to.fullPath)}`
    }
  }

  if (permissionStore.roleCodes.includes(PLATFORM_ADMIN) && to.path.startsWith('/kinder')) {
    return '/'
  }

  return true
})

export default router
