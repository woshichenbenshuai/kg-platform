<template>
  <div class="app-container">
    <el-card class="search-header-area" shadow="never">
      <el-form label-position="left">
        <el-form-item label="角色菜单权限一览" label-width="150px" />
      </el-form>
    </el-card>

    <div class="content-box">
      <div class="left-box" style="width: 100%">
        <el-empty v-if="!permissionStore.menus.length" description="暂无菜单权限数据" />
        <el-table
          v-else
          :data="permissionStore.menus"
          style="width: 100%"
          :stripe="true"
          :header-cell-style="{ fontSize: '14px', background: '#D6DEE7', color: '#0F243C', fontWeight: 'normal' }"
          :row-style="{ fontSize: '14px', color: '#1F2025' }"
        >
          <el-table-column prop="menuName" label="菜单名称" min-width="150" show-overflow-tooltip />
          <el-table-column prop="menuCode" label="菜单编码" min-width="150" show-overflow-tooltip />
          <el-table-column prop="routePath" label="路由地址" min-width="180" show-overflow-tooltip>
            <template #default="scope">{{ scope.row.routePath || '-' }}</template>
          </el-table-column>
          <el-table-column prop="componentPath" label="组件路径" min-width="180" show-overflow-tooltip>
            <template #default="scope">{{ scope.row.componentPath || '-' }}</template>
          </el-table-column>
          <el-table-column prop="menuScope" label="菜单范围" min-width="120" align="center" />
        </el-table>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { usePermissionStore } from '@/store/permission'

const permissionStore = usePermissionStore()
</script>

<style lang="scss" scoped>
.content-box {
  display: flex;
  margin-top: 20px;
  .left-box {
    background: #fff;
    padding: 15px;
    border-radius: 4px;
  }
}
</style>
