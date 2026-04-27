<template>
  <el-button v-if="visible" :type="type">
    <slot />
  </el-button>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { hasPermission } from '@/utils/permission'

const props = withDefaults(
  defineProps<{
    permissionCode: string
    type?: 'primary' | 'success' | 'warning' | 'danger' | 'info'
  }>(),
  {
    type: 'primary'
  }
)

const visible = computed(() => hasPermission(props.permissionCode))
</script>
