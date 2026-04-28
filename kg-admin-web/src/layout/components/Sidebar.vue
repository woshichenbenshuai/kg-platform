<template>
  <div class="sidebar-container">
    <el-scrollbar wrap-class="scrollbar-wrapper">
      <el-menu
        :default-active="activeMenu"
        :collapse="isCollapse"
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409EFF"
        mode="vertical"
        router
      >
        <template v-for="menu in treeMenus" :key="menu.id">
          <!-- Render sub-menu if it has children -->
          <el-sub-menu v-if="menu.children && menu.children.length > 0" :index="menu.routePath || menu.id.toString()">
            <template #title>
              <el-icon><Menu /></el-icon>
              <span>{{ menu.menuName }}</span>
            </template>
            <el-menu-item v-for="child in menu.children" :key="child.id" :index="child.routePath || child.id.toString()">
               <template #title>{{ child.menuName }}</template>
            </el-menu-item>
          </el-sub-menu>
          <!-- Render regular item otherwise -->
          <el-menu-item v-else :index="menu.routePath || menu.id.toString()">
            <el-icon><Menu /></el-icon>
            <template #title>{{ menu.menuName }}</template>
          </el-menu-item>
        </template>
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { useAppStore } from '@/store/app'
import { usePermissionStore } from '@/store/permission'
import { Menu } from '@element-plus/icons-vue'

const route = useRoute()
const appStore = useAppStore()
const permissionStore = usePermissionStore()

const isCollapse = computed(() => !appStore.sidebar.opened)

// Convert flat menus into tree
const treeMenus = computed(() => {
  const menus = permissionStore.menus || []
  const map = new Map()
  menus.forEach(item => {
    map.set(String(item.id), { ...item, children: [] })
  })
  const tree: any[] = []
  map.forEach(node => {
    const parentId = node.parentId ? String(node.parentId) : null
    if (parentId && map.has(parentId)) {
      map.get(parentId).children.push(node)
    } else {
      tree.push(node)
    }
  })
  return tree
})

const activeMenu = computed(() => {
  const { path } = route
  return path
})
</script>

<style lang="scss" scoped>
.sidebar-container {
  height: 100%;
  background-color: #304156;
}
</style>
