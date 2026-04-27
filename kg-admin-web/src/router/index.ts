import { createRouter, createWebHistory } from 'vue-router'
import { routes } from './routes'
import { getCurrentUser } from '@/api/auth'
import { useUserStore } from '@/store/user'
import { usePermissionStore } from '@/store/permission'

const router = createRouter({
  history: createWebHistory(),
  routes
})

let initialized = false

router.beforeEach(async (to) => {
  if (to.path === '/login') {
    return true
  }

  const userStore = useUserStore()
  const permissionStore = usePermissionStore()

  if (!userStore.token) {
    return '/login'
  }

  if (!initialized) {
    const response = await getCurrentUser()
    const currentUser = response.data.data
    userStore.setUser(currentUser.userId, currentUser.username)
    permissionStore.applyCurrentUser(currentUser)
    initialized = true
  }

  return true
})

export default router
