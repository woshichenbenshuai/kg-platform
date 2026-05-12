<template>
  <div class="app-container" v-loading="pageLoading">
    <el-card class="search-header-area" shadow="never">
      <div class="page-title">
        <div>
          <div class="title">角色菜单授权</div>
          <div class="desc">先选择角色，再勾选该角色可访问的后台菜单。</div>
        </div>
        <div class="actions">
          <el-button @click="loadPageData">刷新</el-button>
          <el-button class="add-btn" type="primary" :disabled="!currentRole" :loading="saving" @click="savePermission">
            保存授权
          </el-button>
        </div>
      </div>
    </el-card>

    <div class="permission-layout">
      <el-card class="role-panel" shadow="never">
        <template #header>
          <div class="panel-header">
            <span>角色列表</span>
            <el-tag size="small" type="info">{{ roleList.length }}</el-tag>
          </div>
        </template>

        <el-empty v-if="!roleList.length" description="暂无角色数据" />
        <div v-else class="role-list">
          <button
            v-for="role in roleList"
            :key="role.id"
            class="role-item"
            :class="{ active: String(currentRole?.id) === String(role.id) }"
            type="button"
            @click="selectRole(role)"
          >
            <span class="role-name">{{ role.roleName }}</span>
            <span class="role-code">{{ role.roleCode }}</span>
            <el-tag size="small" :type="role.status ? 'success' : 'danger'">
              {{ role.status ? '启用' : '禁用' }}
            </el-tag>
          </button>
        </div>
      </el-card>

      <el-card class="menu-panel" shadow="never">
        <template #header>
          <div class="panel-header">
            <div>
              <span>菜单权限</span>
              <span v-if="currentRole" class="current-role">当前角色：{{ currentRole.roleName }}</span>
            </div>
            <div class="tree-actions">
              <el-button link type="primary" :disabled="!currentRole" @click="checkAll">全选</el-button>
              <el-button link type="primary" :disabled="!currentRole" @click="clearChecked">清空</el-button>
            </div>
          </div>
        </template>

        <el-empty v-if="!currentRole" description="请先选择左侧角色" />
        <el-empty v-else-if="!menuTree.length" description="暂无菜单数据" />
        <el-tree
          v-else
          ref="menuTreeRef"
          class="menu-tree"
          node-key="id"
          show-checkbox
          default-expand-all
          :data="menuTree"
          :props="{ label: 'menuName', children: 'children' }"
          :expand-on-click-node="false"
          :check-strictly="false"
        >
          <template #default="{ data }">
            <div class="menu-node">
              <span class="menu-name">{{ data.menuName }}</span>
              <span class="menu-meta">{{ data.menuCode }}</span>
              <span class="menu-meta">{{ data.routePath || '-' }}</span>
              <el-tag size="small" effect="plain">{{ data.menuScope }}</el-tag>
            </div>
          </template>
        </el-tree>
      </el-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import { nextTick, onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { fetchRoles } from '@/api/system/role'
import { fetchMenus } from '@/api/system/menu'
import { batchSaveRoleMenus, fetchRoleMenus } from '@/api/system/role-menu'

interface RoleItem {
  id: string | number
  roleCode: string
  roleName: string
  roleScope: string
  status: boolean
}

interface MenuItem {
  id: string | number
  parentId?: string | number | null
  menuCode: string
  menuName: string
  menuScope: string
  routePath?: string
  sortNo?: number
  children?: MenuItem[]
}

interface RoleMenuItem {
  id: string | number
  roleId: string | number
  menuId: string | number
  status: boolean
}

const pageLoading = ref(false)
const roleMenuLoading = ref(false)
const saving = ref(false)
const roleList = ref<RoleItem[]>([])
const menuTree = ref<MenuItem[]>([])
const menuFlat = ref<MenuItem[]>([])
const currentRole = ref<RoleItem | null>(null)
const currentRoleMenus = ref<RoleMenuItem[]>([])
const menuTreeRef = ref<any>()

const normalizeId = (id: string | number | null | undefined) => (id == null ? '' : String(id))

const isRootParent = (parentId: string | number | null | undefined) => {
  return parentId == null || String(parentId) === '' || String(parentId) === '0'
}

const buildMenuTree = (records: MenuItem[]) => {
  const map = new Map<string, MenuItem>()
  const tree: MenuItem[] = []

  records.forEach((item) => {
    map.set(normalizeId(item.id), { ...item, children: [] })
  })

  map.forEach((node) => {
    const parentId = normalizeId(node.parentId)
    if (!isRootParent(parentId) && map.has(parentId)) {
      map.get(parentId)?.children?.push(node)
    } else {
      tree.push(node)
    }
  })

  const sortTree = (nodes: MenuItem[]) => {
    nodes.sort((a, b) => (a.sortNo ?? 0) - (b.sortNo ?? 0))
    nodes.forEach((node) => sortTree(node.children || []))
  }
  sortTree(tree)
  return tree
}

const getLeafCheckedKeys = (menuIds: string[]) => {
  const assigned = new Set(menuIds)
  const parentIds = new Set(menuFlat.value.map((menu) => normalizeId(menu.parentId)).filter((id) => id && id !== '0'))
  return menuIds.filter((id) => !parentIds.has(id) || !hasAssignedChild(id, assigned))
}

const hasAssignedChild = (menuId: string, assigned: Set<string>) => {
  return menuFlat.value.some((menu) => normalizeId(menu.parentId) === menuId && assigned.has(normalizeId(menu.id)))
}

const loadRoles = async () => {
  const res = await fetchRoles({ current: 1, size: 1000 })
  if (res.data.code === '0') {
    roleList.value = res.data.data?.records || []
  }
}

const loadMenus = async () => {
  const res = await fetchMenus({ current: 1, size: 5000 })
  if (res.data.code === '0') {
    menuFlat.value = res.data.data?.records || []
    menuTree.value = buildMenuTree(menuFlat.value)
  }
}

const loadRoleMenus = async (role: RoleItem) => {
  roleMenuLoading.value = true
  try {
    const res = await fetchRoleMenus({ current: 1, size: 5000, bindRoleId: role.id })
    if (res.data.code === '0') {
      currentRoleMenus.value = res.data.data?.records || []
      const activeMenuIds = currentRoleMenus.value
        .filter((item) => item.status !== false)
        .map((item) => normalizeId(item.menuId))
      await nextTick()
      menuTreeRef.value?.setCheckedKeys(getLeafCheckedKeys(activeMenuIds))
    }
  } finally {
    roleMenuLoading.value = false
  }
}

const loadPageData = async () => {
  pageLoading.value = true
  try {
    await Promise.all([loadRoles(), loadMenus()])
    if (!currentRole.value && roleList.value.length) {
      currentRole.value = roleList.value[0]
    }
    if (currentRole.value) {
      await loadRoleMenus(currentRole.value)
    }
  } finally {
    pageLoading.value = false
  }
}

const selectRole = async (role: RoleItem) => {
  if (roleMenuLoading.value || saving.value) {
    return
  }
  currentRole.value = role
  menuTreeRef.value?.setCheckedKeys([])
  await loadRoleMenus(role)
}

const checkAll = () => {
  menuTreeRef.value?.setCheckedKeys(menuFlat.value.map((menu) => normalizeId(menu.id)))
}

const clearChecked = () => {
  menuTreeRef.value?.setCheckedKeys([])
}

const getTargetMenuIds = () => {
  const checkedKeys = menuTreeRef.value?.getCheckedKeys(false) || []
  const halfCheckedKeys = menuTreeRef.value?.getHalfCheckedKeys() || []
  return Array.from(new Set([...checkedKeys, ...halfCheckedKeys].map(normalizeId))).filter(Boolean)
}

const savePermission = async () => {
  if (!currentRole.value) {
    ElMessage.warning('请先选择角色')
    return
  }

  saving.value = true
  try {
    await batchSaveRoleMenus({
      roleId: currentRole.value.id,
      menuIds: getTargetMenuIds()
    })
    ElMessage.success('角色菜单授权已保存')
    await loadRoleMenus(currentRole.value)
  } finally {
    saving.value = false
  }
}

onMounted(() => {
  loadPageData()
})
</script>

<style lang="scss" scoped>
.page-title {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
}

.title {
  font-size: 16px;
  font-weight: 600;
  color: #0f243c;
}

.desc {
  margin-top: 6px;
  font-size: 13px;
  color: #667085;
}

.actions {
  display: flex;
  gap: 10px;
}

.permission-layout {
  display: grid;
  grid-template-columns: 340px minmax(0, 1fr);
  gap: 18px;
  margin-top: 20px;
}

.panel-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  color: #0f243c;
  font-weight: 600;
}

.role-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.role-item {
  display: grid;
  grid-template-columns: minmax(0, 1fr) auto;
  gap: 6px 10px;
  width: 100%;
  padding: 13px 14px;
  text-align: left;
  cursor: pointer;
  background: #f8fafc;
  border: 1px solid #e4e7ed;
  border-radius: 8px;
}

.role-item.active {
  background: #eef3ff;
  border-color: #3f5beb;
  box-shadow: inset 3px 0 0 #3f5beb;
}

.role-name {
  color: #0f243c;
  font-weight: 600;
}

.role-code {
  grid-column: 1 / 3;
  color: #667085;
  font-size: 12px;
}

.current-role {
  margin-left: 12px;
  color: #667085;
  font-size: 13px;
  font-weight: normal;
}

.tree-actions {
  display: flex;
  gap: 10px;
}

.menu-tree {
  min-height: 420px;
}

.menu-node {
  display: inline-flex;
  align-items: center;
  gap: 12px;
  min-height: 32px;
}

.menu-name {
  min-width: 130px;
  color: #0f243c;
  font-weight: 500;
}

.menu-meta {
  min-width: 130px;
  color: #667085;
  font-size: 12px;
}

@media (max-width: 960px) {
  .permission-layout {
    grid-template-columns: 1fr;
  }
}
</style>
