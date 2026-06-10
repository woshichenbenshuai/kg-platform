<template>
  <div class="sidebar-container">
    <div class="brand">
      <div class="brand-mark">KG</div>
      <div v-if="!isCollapse" class="brand-copy">
        <strong>童伴云</strong>
        <span>Kindergarten OS</span>
      </div>
    </div>

    <el-scrollbar class="menu-scrollbar">
      <el-menu
        :default-active="activeMenu"
        :collapse="isCollapse"
        :collapse-transition="false"
        class="kg-menu"
        mode="vertical"
        router
      >
        <template v-for="menu in treeMenus" :key="menu.id">
          <el-sub-menu v-if="menu.children && menu.children.length > 0" :index="menu.routePath || menu.id.toString()">
            <template #title>
              <el-icon><component :is="getMenuIcon(menu)" /></el-icon>
              <span>{{ menu.menuName }}</span>
            </template>
            <el-menu-item
              v-for="child in menu.children"
              :key="child.id"
              :index="child.routePath || child.id.toString()"
            >
              <el-icon><component :is="getMenuIcon(child)" /></el-icon>
              <template #title>{{ child.menuName }}</template>
            </el-menu-item>
          </el-sub-menu>

          <el-menu-item v-else :index="menu.routePath || menu.id.toString()">
            <el-icon><component :is="getMenuIcon(menu)" /></el-icon>
            <template #title>{{ menu.menuName }}</template>
          </el-menu-item>
        </template>
      </el-menu>
    </el-scrollbar>

    <div v-if="!isCollapse" class="sidebar-footer">
      <span class="pulse" />
      <span>管理服务在线</span>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { useAppStore } from '@/store/app'
import { usePermissionStore } from '@/store/permission'
import type { MenuDto } from '@/types/auth'
import {
  Bell,
  Calendar,
  Collection,
  Dish,
  EditPen,
  House,
  Menu,
  OfficeBuilding,
  Reading,
  School,
  Setting,
  Tickets,
  UserFilled,
} from '@element-plus/icons-vue'

const route = useRoute()
const appStore = useAppStore()
const permissionStore = usePermissionStore()

const isCollapse = computed(() => !appStore.sidebar.opened)
const isPlatformAdmin = computed(() => permissionStore.roleCodes.includes('PLATFORM_ADMIN'))

const treeMenus = computed(() => {
  const menus = isPlatformAdmin.value
    ? (permissionStore.menus || []).filter(item => item.menuScope === 'PLATFORM')
    : permissionStore.menus || []
  const map = new Map<string, MenuDto & { children: MenuDto[] }>()
  menus.forEach(item => {
    map.set(String(item.id), { ...item, children: [] })
  })

  const tree: Array<MenuDto & { children: MenuDto[] }> = []
  map.forEach(node => {
    const parentId = node.parentId ? String(node.parentId) : null
    if (parentId && map.has(parentId)) {
      map.get(parentId)?.children.push(node)
    } else {
      tree.push(node)
    }
  })

  const sortByOrder = (items: Array<MenuDto & { children?: MenuDto[] }>) => {
    items.sort((a, b) => (a.sortNo ?? 0) - (b.sortNo ?? 0))
    items.forEach(item => {
      if (item.children?.length) {
        sortByOrder(item.children)
      }
    })
  }

  sortByOrder(tree)
  return tree
})

const activeMenu = computed(() => route.path)

const getMenuIcon = (menu: MenuDto) => {
  const text = `${menu.menuCode || ''} ${menu.menuName || ''} ${menu.routePath || ''}`.toLowerCase()

  if (text.includes('dashboard') || text.includes('首页')) return House
  if (text.includes('tenant') || text.includes('园所') || text.includes('platform')) return OfficeBuilding
  if (text.includes('teacher') || text.includes('教师')) return School
  if (text.includes('student') || text.includes('学生') || text.includes('幼儿')) return Reading
  if (text.includes('guardian') || text.includes('家长')) return UserFilled
  if (text.includes('notice') || text.includes('通知')) return Bell
  if (text.includes('recipe') || text.includes('食谱')) return Dish
  if (text.includes('growth') || text.includes('成长')) return EditPen
  if (text.includes('leave') || text.includes('请假')) return Calendar
  if (text.includes('role') || text.includes('menu') || text.includes('dict') || text.includes('system')) return Setting
  if (text.includes('class') || text.includes('班级')) return Collection
  if (text.includes('relation') || text.includes('关系')) return Tickets
  return Menu
}
</script>

<style lang="scss" scoped>
.sidebar-container {
  display: flex;
  flex-direction: column;
  height: 100%;
  padding: 18px 12px;
  color: #eaf6f2;
}

.brand {
  display: flex;
  align-items: center;
  min-height: 52px;
  gap: 12px;
  padding: 0 8px 18px;
}

.brand-mark {
  width: 42px;
  height: 42px;
  display: grid;
  flex: 0 0 42px;
  place-items: center;
  border-radius: 15px;
  color: #123d35;
  background: linear-gradient(135deg, #fff6d8, #94ecd6);
  box-shadow: 0 14px 34px rgba(0, 0, 0, 0.22);
  font-size: 15px;
  font-weight: 900;
  letter-spacing: -0.04em;
}

.brand-copy {
  display: flex;
  min-width: 0;
  flex-direction: column;
}

.brand-copy strong {
  font-size: 18px;
  line-height: 1.15;
  letter-spacing: 0.04em;
}

.brand-copy span {
  margin-top: 4px;
  color: rgba(234, 246, 242, 0.62);
  font-size: 11px;
  letter-spacing: 0.1em;
  text-transform: uppercase;
}

.menu-scrollbar {
  min-height: 0;
  flex: 1;
}

.kg-menu {
  border: none;
  background: transparent;
}

:deep(.el-menu) {
  border-right: none;
  background: transparent;
}

:deep(.el-menu-item),
:deep(.el-sub-menu__title) {
  height: 46px;
  margin: 6px 0;
  border-radius: 16px;
  color: rgba(234, 246, 242, 0.74);
  font-weight: 650;
  letter-spacing: 0.01em;
}

:deep(.el-menu-item .el-icon),
:deep(.el-sub-menu__title .el-icon) {
  width: 20px;
  color: rgba(234, 246, 242, 0.72);
  font-size: 18px;
}

:deep(.el-menu-item:hover),
:deep(.el-sub-menu__title:hover) {
  color: #ffffff;
  background: rgba(255, 255, 255, 0.09);
}

:deep(.el-menu-item.is-active) {
  color: #123d35;
  background: linear-gradient(135deg, #f8d889, #86e0c8);
  box-shadow: 0 12px 26px rgba(0, 0, 0, 0.18);
}

:deep(.el-menu-item.is-active .el-icon) {
  color: #123d35;
}

:deep(.el-sub-menu .el-menu) {
  padding-left: 8px;
  background: rgba(255, 255, 255, 0.04);
  border-radius: 18px;
}

:deep(.el-sub-menu .el-menu-item) {
  height: 40px;
  margin: 4px 0;
  font-size: 13px;
}

:deep(.el-menu--collapse .el-sub-menu__title),
:deep(.el-menu--collapse .el-menu-item) {
  justify-content: center;
  padding: 0;
}

.sidebar-footer {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-top: 14px;
  padding: 12px 14px;
  border: 1px solid rgba(255, 255, 255, 0.12);
  border-radius: 18px;
  color: rgba(234, 246, 242, 0.72);
  background: rgba(255, 255, 255, 0.07);
  font-size: 12px;
}

.pulse {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background: #7ee7ba;
  box-shadow: 0 0 0 6px rgba(126, 231, 186, 0.14);
}
</style>
