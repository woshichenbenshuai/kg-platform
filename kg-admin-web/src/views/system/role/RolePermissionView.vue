<template>
  <el-card>
    <template #header>????</template>
    <el-empty v-if="!permissionStore.menus.length" description="?????????" />
    <div v-else class="menu-tree">
      <div v-for="menu in permissionStore.menus" :key="menu.id" class="menu-tree__node">
        <div class="menu-tree__title">{{ menu.menuName }}</div>
        <div class="menu-tree__meta">???{{ menu.menuCode }}</div>
        <div class="menu-tree__meta">???{{ menu.routePath || '-' }}</div>
        <div class="menu-tree__meta">???{{ menu.componentPath || '-' }}</div>
      </div>
    </div>
  </el-card>
</template>

<script setup lang="ts">
import { usePermissionStore } from '@/store/permission'

const permissionStore = usePermissionStore()
</script>

<style scoped>
.menu-tree {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.menu-tree__node {
  border: 1px solid #dcdfe6;
  border-radius: 8px;
  padding: 16px;
  background: #ffffff;
}

.menu-tree__title {
  font-size: 16px;
  font-weight: 600;
}

.menu-tree__meta {
  margin-top: 8px;
  color: #909399;
}
</style>
