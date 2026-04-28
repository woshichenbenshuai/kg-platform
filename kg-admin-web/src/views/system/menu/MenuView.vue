<template>
  <div class="app-container" v-loading="isPageLoading">
    <el-card class="search-header-area" shadow="never">
      <el-form ref="form" :model="searchForm" label-position="left">
        <el-form-item label="菜单名称" label-width="70px" style="width: 300px">
          <el-input v-model="searchForm.menuName" placeholder="请输入菜单名称" clearable />
        </el-form-item>
      </el-form>
      <div class="btn-box">
        <el-button class="search" @click="getList">查 询</el-button>
        <el-button class="add-btn" @click="handleCreate">新增主菜单</el-button>
      </div>
    </el-card>

    <div class="content-box">
      <div class="left-box" style="width: 100%">
        <el-table
          :data="tableData"
          row-key="id"
          style="width: 100%"
          :stripe="true"
          :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
          :header-cell-style="{ fontSize: '14px', background: '#D6DEE7', color: '#0F243C', fontWeight: 'normal' }"
          :row-style="{ fontSize: '14px', color: '#1F2025' }"
          v-loading="isLoading"
        >
          <el-table-column prop="menuName" label="菜单名称" min-width="200" show-overflow-tooltip />
          <el-table-column prop="menuCode" label="菜单编码" min-width="150" show-overflow-tooltip />
          <el-table-column prop="routePath" label="路由地址" min-width="180" show-overflow-tooltip />
          <el-table-column prop="icon" label="图标" align="center" min-width="80" />
          <el-table-column prop="status" label="状态" align="center" min-width="100">
            <template #default="scope">
              <el-tag :type="scope.row.status ? 'success' : 'danger'">
                {{ scope.row.status ? '启用' : '禁用' }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" fixed="right" width="250" align="center">
            <template #default="scope">
              <el-button type="success" link size="small" @click="handleCreateChild(scope.row)">添加下级</el-button>
              <el-button type="primary" link size="small" @click="handleEdit(scope.row)">编辑</el-button>
              <el-button type="danger" link size="small" @click="handleDelete(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>

    <el-drawer :title="dialogTitle" v-model="drawerVisible" size="500px" direction="rtl">
      <el-form :model="formData" label-position="right" label-width="95px" style="padding: 0 20px;">
        <el-form-item label="上级菜单：">
          <el-input :model-value="parentMenuName" disabled placeholder="主菜单" style="width: 350px" />
        </el-form-item>
        <el-form-item label="菜单名称：">
          <el-input v-model="formData.menuName" placeholder="请输入菜单名称" style="width: 350px" />
        </el-form-item>
        <el-form-item label="路由地址：">
          <el-input v-model="formData.routePath" placeholder="请输入路由地址" style="width: 350px" />
        </el-form-item>
        <el-form-item label="排序值：">
          <el-input-number v-model="formData.sort" />
        </el-form-item>
        <el-form-item label="状态：">
          <el-radio-group v-model="formData.status">
            <el-radio :value="true">启用</el-radio>
            <el-radio :value="false">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <div style="padding: 0 20px;">
          <el-button @click="drawerVisible = false">取消</el-button>
          <el-button type="primary" @click="submitForm">确定</el-button>
        </div>
      </template>
    </el-drawer>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, reactive } from 'vue'
import { fetchMenus, createMenu, updateMenu, deleteMenu } from '@/api/system/menu'
import { ElMessage, ElMessageBox } from 'element-plus'

const tableData = ref<any[]>([])
const allMenusFlat = ref<any[]>([])
const isLoading = ref(false)
const isPageLoading = ref(false)

const searchForm = reactive({ menuName: '' })

const drawerVisible = ref(false)
const dialogTitle = ref('')
const formData = ref<any>({})
const parentMenuName = ref('主菜单')

const getList = async () => {
  isLoading.value = true
  try {
    const res = await fetchMenus({ current: 1, size: 999 })
    if (res.data.code === '0') {
      const records = res.data.data?.records || []
      allMenusFlat.value = records
      // Convert flat list to tree
      const map = new Map()
      const tree: any[] = []
      records.forEach((item: any) => {
        map.set(String(item.id), { ...item, children: [] })
      })
      map.forEach((node: any) => {
        const parentId = node.parentId ? String(node.parentId) : null
        if (parentId && map.has(parentId)) {
          map.get(parentId).children.push(node)
        } else {
          tree.push(node)
        }
      })
      tableData.value = tree
    }
  } catch (error) {
    console.error(error)
  } finally {
    isLoading.value = false
  }
}

const handleCreate = () => {
  formData.value = { parentId: 0, status: true, sort: 1 }
  parentMenuName.value = '主菜单'
  dialogTitle.value = '新增主菜单'
  drawerVisible.value = true
}

const handleCreateChild = (row: any) => {
  formData.value = { parentId: row.id, status: true, sort: (row.children?.length || 0) + 1 }
  parentMenuName.value = row.menuName
  dialogTitle.value = '新增子菜单'
  drawerVisible.value = true
}

const handleEdit = (row: any) => {
  formData.value = Object.assign({}, row)
  if (!row.parentId || row.parentId === '0' || row.parentId === 0) {
    parentMenuName.value = '主菜单'
  } else {
    const parent = allMenusFlat.value.find(m => String(m.id) === String(row.parentId))
    parentMenuName.value = parent ? parent.menuName : '未知'
  }
  dialogTitle.value = '编辑菜单'
  drawerVisible.value = true
}

const handleDelete = (row: any) => {
  ElMessageBox.confirm('确认删除该菜单？', '提示信息', { type: 'warning', confirmButtonText: '确认', cancelButtonText: '取消' }).then(async () => {
    await deleteMenu(row.id)
    ElMessage.success('删除成功')
    getList()
  })
}

const submitForm = async () => {
  try {
    if (formData.value.id) {
      await updateMenu(formData.value)
    } else {
      await createMenu(formData.value)
    }
    ElMessage.success('保存成功')
    drawerVisible.value = false
    getList()
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  getList()
})
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
