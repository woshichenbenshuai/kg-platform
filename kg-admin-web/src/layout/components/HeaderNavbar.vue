<template>
  <div class="header-navbar">
    <div class="logo">
      <span class="title">管理系统</span>
    </div>
    <div class="right-menu">
      <el-dropdown trigger="click" @command="handleCommand">
        <span class="user-info el-dropdown-link" style="color: white; cursor: pointer;">
          {{ username || 'Admin' }} <el-icon><ArrowDown /></el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'
import { usePermissionStore } from '@/store/permission'
import { ArrowDown } from '@element-plus/icons-vue'

const router = useRouter()
const userStore = useUserStore()
const permissionStore = usePermissionStore()

const username = computed(() => userStore.username)

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
  justify-content: space-between;
  align-items: center;
  height: 100%;
  color: #fff;
  padding: 0 20px;
}
.logo .title {
  font-size: 20px;
  font-weight: bold;
}
.el-dropdown-link {
  display: flex;
  align-items: center;
  gap: 4px;
}
</style>
