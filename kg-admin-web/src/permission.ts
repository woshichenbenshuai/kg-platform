import router from './router'
import { useUserStore } from './store/user'
import { usePermissionStore } from './store/permission'
import { getCurrentUser } from '@/api/auth'

const whiteList = ['/login']

router.beforeEach(async (to, from, next) => {
  const userStore = useUserStore()
  const permissionStore = usePermissionStore()

  const hasToken = userStore.token

  if (hasToken) {
    if (to.path === '/login') {
      next({ path: '/' })
    } else {
      const hasMenus = permissionStore.menus && permissionStore.menus.length > 0
      if (hasMenus) {
        next()
      } else {
        try {
          // Fetch user info and menus
          const { data } = await getCurrentUser()
          if (data.code === '0') {
            const userData = data.data
            userStore.setUser(userData.userId, userData.username)
            permissionStore.applyCurrentUser(userData)
            next({ ...to, replace: true })
          } else {
            userStore.clearUser()
            next(`/login?redirect=${to.path}`)
          }
        } catch (error) {
          userStore.clearUser()
          next(`/login?redirect=${to.path}`)
        }
      }
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      next()
    } else {
      next(`/login?redirect=${to.path}`)
    }
  }
})
