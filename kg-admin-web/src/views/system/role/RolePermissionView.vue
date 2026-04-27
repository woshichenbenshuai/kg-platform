<template>
  <el-card>
    <template #header>权限树配置</template>
    <el-empty v-if="!permissionStore.menus.length" description="当前用户无菜单权限" />
    <div v-else class="permission-tree">
      <div v-for="menu in permissionStore.menus" :key="menu.id" class="permission-tree__node">
        <div class="permission-tree__title">{{ menu.menuName }}</div>
        <div class="permission-tree__meta">{{ menu.menuCode }}</div>
        <div
          v-if="permissionStore.permissionPointsByMenuId[menu.id]?.length"
          class="permission-tree__points"
        >
          <el-tag
            v-for="point in permissionStore.permissionPointsByMenuId[menu.id]"
            :key="point.id"
            class="permission-tree__tag"
          >
            {{ point.permissionName }}
          </el-tag>
        </div>
        <div v-else class="permission-tree__empty">当前节点暂无权限点</div>
      </div>
    </div>
  </el-card>
</template>

<script setup lang="ts">
import { usePermissionStore } from '@/store/permission'

const permissionStore = usePermissionStore()
</script>

<style scoped>
.permission-tree {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.permission-tree__node {
  border: 1px solid #dcdfe6;
  border-radius: 8px;
  padding: 16px;
  background: #ffffff;
}

.permission-tree__title {
  font-size: 16px;
  font-weight: 600;
}

.permission-tree__meta {
  margin-top: 8px;
  color: #909399;
}

.permission-tree__points {
  margin-top: 12px;
}

.permission-tree__tag {
  margin-right: 8px;
  margin-bottom: 8px;
}

.permission-tree__empty {
  margin-top: 12px;
  color: #c0c4cc;
}
</style>
