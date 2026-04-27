<template>
  <el-card>
    <template #header>??</template>
    <div>?????{{ userStore.username }}</div>
    <div>?????{{ permissionStore.tenantId ?? '???' }}</div>
    <div>?????{{ permissionStore.roleCodes.join(', ') || '?' }}</div>
    <div>?????{{ permissionStore.menus.length }}</div>
  </el-card>
</template>

<script setup lang="ts">
import { useUserStore } from '@/store/user'
import { usePermissionStore } from '@/store/permission'

const userStore = useUserStore()
const permissionStore = usePermissionStore()
</script>
