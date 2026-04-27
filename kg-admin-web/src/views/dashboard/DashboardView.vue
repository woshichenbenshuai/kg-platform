<template>
  <el-card>
    <template #header>首页</template>
    <div>当前用户：{{ userStore.username }}</div>
    <div>当前租户：{{ permissionStore.tenantId ?? '未选择' }}</div>
    <div>角色编码：{{ permissionStore.roleCodes.join(', ') || '无' }}</div>
    <div class="dashboard-actions">
      <PermissionButton permission-code="PLATFORM_USER_ADD" type="primary">
        新增用户
      </PermissionButton>
    </div>
  </el-card>
</template>

<script setup lang="ts">
import PermissionButton from '@/components/PermissionButton.vue'
import { useUserStore } from '@/store/user'
import { usePermissionStore } from '@/store/permission'

const userStore = useUserStore()
const permissionStore = usePermissionStore()
</script>

<style scoped>
.dashboard-actions {
  margin-top: 16px;
}
</style>
