<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>用户信息</span>
            </div>
          </template>
          <div class="info-row"><span class="label">用户名：</span>{{ userStore.username }}</div>
          <div class="info-row"><span class="label">租户ID：</span>{{ permissionStore.tenantId ?? '无' }}</div>
          <div class="info-row"><span class="label">角色：</span>{{ permissionStore.roleCodes.join(', ') || '无' }}</div>
          <div class="info-row"><span class="label">菜单数：</span>{{ permissionStore.menus.length }}</div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { useUserStore } from '@/store/user'
import { usePermissionStore } from '@/store/permission'

const userStore = useUserStore()
const permissionStore = usePermissionStore()
</script>

<style scoped>
.info-row {
  padding: 8px 0;
  font-size: 14px;
  color: #606266;
}
.info-row .label {
  font-weight: bold;
  color: #303133;
}
.card-header {
  font-weight: bold;
  font-size: 16px;
}
</style>
