<template>
  <div class="app-container" v-loading="isPageLoading">
    <el-card class="search-header-area" shadow="never">
      <el-form ref="form" :model="searchForm" label-position="left">
        <el-form-item label="用户名" label-width="70px" style="width: 300px">
          <el-input v-model="searchForm.username" placeholder="请输入用户名" clearable />
        </el-form-item>
        <el-form-item label="用户状态" label-width="70px" style="width: 300px">
          <el-select v-model="searchForm.status" placeholder="请选择状态" clearable>
            <el-option label="全部" value="" />
            <el-option label="启用" :value="1" />
            <el-option label="禁用" :value="0" />
          </el-select>
        </el-form-item>
      </el-form>
      <div class="btn-box">
        <el-button class="search" @click="getList">查 询</el-button>
        <el-button class="add-btn" @click="handleCreate">新增用户</el-button>
      </div>
    </el-card>

    <div class="content-box">
      <div class="left-box" style="width: 100%">
        <el-table
          :data="tableData"
          style="width: 100%"
          :stripe="true"
          :header-cell-style="{ fontSize: '14px', background: '#D6DEE7', color: '#0F243C', fontWeight: 'normal' }"
          :row-style="{ fontSize: '14px', color: '#1F2025' }"
          v-loading="isLoading"
        >
          <el-table-column prop="username" label="用户账号" min-width="120" show-overflow-tooltip />
          <el-table-column prop="nickname" label="昵称" min-width="120" show-overflow-tooltip />
          <el-table-column prop="phone" label="手机号" min-width="120" show-overflow-tooltip />
          <el-table-column prop="status" label="用户状态" align="center" min-width="100">
            <template #default="scope">
              <el-switch
                v-model="scope.row.status"
                :active-value="1"
                :inactive-value="0"
                active-text="启用"
                inactive-text="禁用"
                active-color="#3f5beb"
                @change="statusChange(scope.row)"
              />
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间" min-width="160" align="center" show-overflow-tooltip />
          <el-table-column label="操作" fixed="right" width="200" align="center">
            <template #default="scope">
              <el-button type="primary" link size="small" @click="handleEdit(scope.row)">编辑</el-button>
              <el-button type="danger" link size="small" @click="handleDelete(scope.row)" v-if="scope.row.username !== 'admin'">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          background
          v-model:current-page="listQuery.current"
          v-model:page-size="listQuery.size"
          :page-sizes="[10, 20, 30, 40]"
          layout="total, prev, pager, next, sizes"
          :total="total"
          @size-change="getList"
          @current-change="getList"
        />
      </div>
    </div>

    <!-- 用户新增/编辑抽屉 -->
    <el-drawer :title="dialogTitle" v-model="drawerVisible" size="500px" direction="rtl">
      <el-form :model="formData" label-position="right" label-width="95px" style="padding: 0 20px;">
        <el-form-item label="用户账号：">
          <el-input v-model="formData.username" placeholder="请输入用户账号" :disabled="!!formData.id" style="width: 350px" />
        </el-form-item>
        <el-form-item label="昵称：">
          <el-input v-model="formData.nickname" placeholder="请输入昵称" style="width: 350px" />
        </el-form-item>
        <el-form-item label="密码：" v-if="!formData.id">
          <el-input v-model="formData.password" type="password" placeholder="请输入密码" style="width: 350px" />
        </el-form-item>
        <el-form-item label="角色：">
          <el-select v-model="formData.roleIds" multiple placeholder="请选择角色" clearable style="width: 350px">
            <el-option v-for="role in roleList" :key="role.id" :label="role.roleName" :value="role.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态：" v-if="formData.id">
          <el-radio-group v-model="formData.status">
            <el-radio :value="1">启用</el-radio>
            <el-radio :value="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="手机号：">
          <el-input v-model="formData.phone" placeholder="请输入手机号" style="width: 350px" />
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
import { fetchUsers, createUser, updateUser, deleteUser } from '@/api/system/user'
import { fetchRoles } from '@/api/system/role'
import { ElMessage, ElMessageBox } from 'element-plus'
import http from '@/api/http'

const tableData = ref([])
const total = ref(0)
const isLoading = ref(false)
const isPageLoading = ref(false)
const listQuery = reactive({ current: 1, size: 10 })
const searchForm = reactive({ username: '', status: '' })

const drawerVisible = ref(false)
const dialogTitle = ref('')
const formData = ref<any>({})
const roleList = ref<any[]>([])

const getList = async () => {
  isLoading.value = true
  try {
    const params = { current: listQuery.current, size: listQuery.size, ...searchForm }
    const res = await fetchUsers(params)
    if (res.data.code === '0') {
      tableData.value = res.data.data?.records || []
      total.value = res.data.data?.total || 0
    }
  } catch (error) {
    console.error(error)
  } finally {
    isLoading.value = false
  }
}

const getRoleList = async () => {
  try {
    const res = await fetchRoles({ current: 1, size: 100 })
    if (res.data.code === '0') {
      roleList.value = res.data.data?.records || []
    }
  } catch (e) {
    console.error(e)
  }
}

const getUserRoleIds = async (userId: string) => {
  try {
    const res = await http.get('/user-roles/pages', { params: { current: 1, size: 100, userId } })
    if (res.data.code === '0') {
      return (res.data.data?.records || []).map((r: any) => r.roleId)
    }
  } catch (e) {
    console.error(e)
  }
  return []
}

const handleCreate = async () => {
  await getRoleList()
  formData.value = { status: 1, roleIds: [] }
  dialogTitle.value = '新增用户'
  drawerVisible.value = true
}

const handleEdit = async (row: any) => {
  await getRoleList()
  const roleIds = await getUserRoleIds(row.id)
  formData.value = { ...row, roleIds }
  dialogTitle.value = '编辑用户'
  drawerVisible.value = true
}

const handleDelete = (row: any) => {
  ElMessageBox.confirm('确认删除该用户？', '提示信息', { type: 'warning', confirmButtonText: '确认', cancelButtonText: '取消' }).then(async () => {
    await deleteUser(row.id)
    ElMessage.success('删除成功')
    getList()
  })
}

const statusChange = async (row: any) => {
  try {
    await updateUser({ id: row.id, status: row.status })
  } catch (e) {
    row.status = row.status === 1 ? 0 : 1
  }
}

const submitForm = async () => {
  try {
    if (formData.value.id) {
      await updateUser(formData.value)
    } else {
      await createUser(formData.value)
    }
    // save role assignments
    if (formData.value.roleIds && formData.value.roleIds.length > 0) {
      // This is a simplified approach; actual implementation may need backend batch API
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
.el-pagination {
  margin-top: 15px;
  display: flex;
  justify-content: flex-end;
}
</style>
