<template>
  <div class="header-navbar">
    <div class="left-zone">
      <el-button class="collapse-btn" circle @click="appStore.toggleSideBar">
        <el-icon>
          <component :is="appStore.sidebar.opened ? Fold : Expand" />
        </el-icon>
      </el-button>
      <div class="route-copy">
        <span>{{ currentTitle }}</span>
        <strong>平台管理中心</strong>
      </div>
    </div>

    <div class="right-menu">
      <el-select
        v-if="showTenantSwitcher"
        v-model="currentTenantId"
        class="tenant-select"
        placeholder="选择园所"
        size="large"
        @change="handleTenantChange"
      >
        <el-option
          v-for="tenant in tenantOptions"
          :key="tenant.tenantId"
          :label="tenant.tenantName"
          :value="tenant.tenantId"
        />
      </el-select>

      <el-dropdown trigger="click" @command="handleCommand">
        <button class="user-chip" type="button">
          <span class="avatar">{{ userInitial }}</span>
          <span class="user-name">{{ username || 'Admin' }}</span>
          <el-icon><ArrowDown /></el-icon>
        </button>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="logout">
              <el-icon><SwitchButton /></el-icon>
              退出登录
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getCurrentUser, switchTenant } from '@/api/auth'
import { useAppStore } from '@/store/app'
import { useUserStore } from '@/store/user'
import { usePermissionStore } from '@/store/permission'
import { ArrowDown, Expand, Fold, SwitchButton } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const appStore = useAppStore()
const userStore = useUserStore()
const permissionStore = usePermissionStore()

const username = computed(() => userStore.username)
const isPlatformAdmin = computed(() => permissionStore.roleCodes.includes('PLATFORM_ADMIN'))
const tenantOptions = computed(() => isPlatformAdmin.value ? [] : permissionStore.tenants || [])
const showTenantSwitcher = computed(() => !isPlatformAdmin.value && tenantOptions.value.length > 0)
const currentTenantId = computed({
  get: () => permissionStore.tenantId,
  set: () => {}
})

const currentTitle = computed(() => {
  const menu = permissionStore.menus.find(item => item.routePath === route.path)
  if (menu?.menuName) return menu.menuName

  const titleMap: Record<string, string> = {
    '/': '工作台',
    '/platform/tenants': '园所配置',
    '/platform/menu': '菜单管理',
    '/kinder/teachers': '教师管理',
    '/kinder/classes': '班级管理',
    '/kinder/students': '幼儿管理',
    '/kinder/guardians': '家长管理',
    '/kinder/student-guardian-relations': '亲属关系',
    '/kinder/notices': '通知公告',
    '/kinder/recipes': '每日食谱',
    '/kinder/growth-records': '成长记录',
    '/kinder/leave-requests': '请假审批',
    '/system/users': '用户管理',
    '/system/menus': '菜单管理',
    '/system/dicts': '字典管理',
    '/system/role-menus': '角色权限',
  }

  return titleMap[route.path] || '工作台'
})

const userInitial = computed(() => (username.value || 'A').slice(0, 1).toUpperCase())

const refreshCurrentUser = async () => {
  const response = await getCurrentUser()
  const currentUser = response.data.data
  userStore.setUser(currentUser.userId, currentUser.username)
  permissionStore.applyCurrentUser(currentUser)
}

const handleTenantChange = async (tenantId: number | string) => {
  if (isPlatformAdmin.value || !tenantId || String(tenantId) === String(permissionStore.tenantId)) {
    return
  }
  const response = await switchTenant(tenantId)
  userStore.setToken(response.data.data.accessToken)
  permissionStore.clearPermission()
  await refreshCurrentUser()
  router.push('/')
}

const handleCommand = (command: string) => {
  if (command === 'logout') {
    userStore.clearUser()
    permissionStore.clearPermission()
    router.push('/login')
  }
}
</script>

<style lang="scss" scoped>
.header-navbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 100%;
  gap: 18px;
  padding: 0 18px 0 16px;
}

.left-zone,
.right-menu {
  display: flex;
  align-items: center;
  gap: 14px;
  min-width: 0;
}

.collapse-btn {
  width: 44px;
  height: 44px;
  border: 1px solid rgba(29, 138, 116, 0.16);
  color: var(--kg-primary);
  background: rgba(255, 255, 255, 0.82);
}

.route-copy {
  display: flex;
  min-width: 0;
  flex-direction: column;
  gap: 3px;
}

.route-copy span {
  color: var(--kg-muted);
  font-size: 12px;
}

.route-copy strong {
  color: var(--kg-text);
  font-size: 20px;
  line-height: 1.1;
  letter-spacing: -0.02em;
}

.tenant-select {
  width: 220px;
}

.user-chip {
  display: inline-flex;
  align-items: center;
  gap: 10px;
  height: 44px;
  padding: 0 12px 0 7px;
  border: 1px solid rgba(34, 67, 57, 0.1);
  border-radius: 999px;
  color: var(--kg-text);
  background: rgba(255, 255, 255, 0.78);
  box-shadow: 0 8px 22px rgba(32, 72, 61, 0.08);
  cursor: pointer;
}

.avatar {
  width: 32px;
  height: 32px;
  display: grid;
  place-items: center;
  border-radius: 50%;
  color: #123d35;
  background: linear-gradient(135deg, #f8d889, #86e0c8);
  font-size: 13px;
  font-weight: 900;
}

.user-name {
  max-width: 120px;
  overflow: hidden;
  font-weight: 700;
  text-overflow: ellipsis;
  white-space: nowrap;
}

@media (max-width: 760px) {
  .header-navbar {
    padding: 0 12px;
  }

  .route-copy span,
  .user-name {
    display: none;
  }

  .route-copy strong {
    font-size: 16px;
  }

  .tenant-select {
    width: 150px;
  }
}
</style>
