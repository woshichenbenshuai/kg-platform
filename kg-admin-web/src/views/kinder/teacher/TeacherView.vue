<template>
  <div class="app-container">
    <el-card class="search-header-area" shadow="never">
      <el-form :model="searchForm" label-position="left">
        <el-form-item label="教师姓名" label-width="70px" style="width: 280px">
          <el-input v-model="searchForm.teacherName" placeholder="请输入教师姓名" clearable />
        </el-form-item>
        <el-form-item label="教师编号" label-width="70px" style="width: 280px">
          <el-input v-model="searchForm.teacherNo" placeholder="请输入教师编号" clearable />
        </el-form-item>
        <el-form-item label="状态" label-width="50px" style="width: 220px">
          <el-select v-model="searchForm.status" placeholder="全部" clearable>
            <el-option label="启用" :value="1" />
            <el-option label="停用" :value="0" />
          </el-select>
        </el-form-item>
      </el-form>
      <div class="btn-box">
        <el-button class="search" @click="getList">查询</el-button>
        <el-button class="add-btn" @click="handleCreate">新增教师</el-button>
        <el-button type="success" @click="handleOpenAccount">开通教师账号</el-button>
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
          v-loading="loading"
        >
          <el-table-column prop="teacherNo" label="教师编号" min-width="120" show-overflow-tooltip />
          <el-table-column prop="teacherName" label="教师姓名" min-width="120" show-overflow-tooltip />
          <el-table-column prop="phone" label="手机号" min-width="130" show-overflow-tooltip />
          <el-table-column prop="gender" label="性别" min-width="90" align="center" />
          <el-table-column label="账号绑定" min-width="100" align="center">
            <template #default="{ row }">
              <el-tag :type="row.userId ? 'success' : 'info'">{{ row.userId ? '已绑定' : '未绑定' }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="status" label="状态" min-width="100" align="center">
            <template #default="{ row }">
              <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '启用' : '停用' }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间" min-width="170" align="center" show-overflow-tooltip />
          <el-table-column label="操作" fixed="right" width="180" align="center">
            <template #default="{ row }">
              <el-button type="primary" link size="small" @click="handleEdit(row)">编辑</el-button>
              <el-button type="danger" link size="small" @click="handleDelete(row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <el-pagination
          background
          v-model:current-page="listQuery.current"
          v-model:page-size="listQuery.size"
          :page-sizes="[10, 20, 50]"
          layout="total, prev, pager, next, sizes"
          :total="total"
          @size-change="getList"
          @current-change="getList"
        />
      </div>
    </div>

    <el-drawer :title="drawerTitle" v-model="drawerVisible" size="500px" direction="rtl">
      <el-form :model="formData" label-position="right" label-width="95px" style="padding: 0 20px">
        <el-form-item label="教师编号：">
          <el-input v-model="formData.teacherNo" placeholder="请输入教师编号" />
        </el-form-item>
        <el-form-item label="教师姓名：">
          <el-input v-model="formData.teacherName" placeholder="请输入教师姓名" />
        </el-form-item>
        <el-form-item label="平台用户ID：">
          <el-input v-model="formData.platformUserId" placeholder="可选，绑定已有平台账号" />
        </el-form-item>
        <el-form-item label="手机号：">
          <el-input v-model="formData.phone" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="性别：">
          <el-select v-model="formData.gender" placeholder="请选择性别" clearable style="width: 100%">
            <el-option label="男" value="MALE" />
            <el-option label="女" value="FEMALE" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态：">
          <el-radio-group v-model="formData.status">
            <el-radio :value="1">启用</el-radio>
            <el-radio :value="0">停用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <div style="padding: 0 20px">
          <el-button @click="drawerVisible = false">取消</el-button>
          <el-button type="primary" @click="submitForm">保存</el-button>
        </div>
      </template>
    </el-drawer>

    <el-drawer title="开通教师账号" v-model="accountDrawerVisible" size="500px" direction="rtl">
      <el-alert
        title="手机号会作为登录账号，同时会创建教师档案、绑定当前幼儿园并分配 TEACHER_PORTAL 角色。"
        type="info"
        show-icon
        :closable="false"
        style="margin: 0 20px 18px"
      />
      <el-form :model="accountForm" label-position="right" label-width="95px" style="padding: 0 20px">
        <el-form-item label="教师编号：">
          <el-input v-model="accountForm.teacherNo" placeholder="请输入教师编号" />
        </el-form-item>
        <el-form-item label="教师姓名：">
          <el-input v-model="accountForm.teacherName" placeholder="请输入教师姓名" />
        </el-form-item>
        <el-form-item label="手机号：">
          <el-input v-model="accountForm.phone" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="初始密码：">
          <el-input v-model="accountForm.password" type="password" placeholder="请输入初始密码" show-password />
        </el-form-item>
        <el-form-item label="性别：">
          <el-select v-model="accountForm.gender" placeholder="请选择性别" clearable style="width: 100%">
            <el-option label="男" value="MALE" />
            <el-option label="女" value="FEMALE" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <div style="padding: 0 20px">
          <el-button @click="accountDrawerVisible = false">取消</el-button>
          <el-button type="primary" @click="submitOpenAccount">开通</el-button>
        </div>
      </template>
    </el-drawer>
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { createTeacher, deleteTeacher, fetchTeachers, openTeacherAccount, updateTeacher } from '@/api/kinder/teacher'

const loading = ref(false)
const tableData = ref<any[]>([])
const total = ref(0)
const listQuery = reactive({ current: 1, size: 10 })
const searchForm = reactive({ teacherName: '', teacherNo: '', status: '' as number | string })

const drawerVisible = ref(false)
const drawerTitle = ref('')
const formData = ref<any>({})

const accountDrawerVisible = ref(false)
const accountForm = ref<any>({})

const getList = async () => {
  loading.value = true
  try {
    const res = await fetchTeachers({ current: listQuery.current, size: listQuery.size, ...searchForm })
    if (res.data.code === '0') {
      tableData.value = res.data.data?.records || []
      total.value = res.data.data?.total || 0
    }
  } finally {
    loading.value = false
  }
}

const handleCreate = () => {
  formData.value = { status: 1 }
  drawerTitle.value = '新增教师'
  drawerVisible.value = true
}

const handleEdit = (row: any) => {
  formData.value = { ...row, platformUserId: row.userId }
  drawerTitle.value = '编辑教师'
  drawerVisible.value = true
}

const submitForm = async () => {
  if (formData.value.id) {
    await updateTeacher(formData.value)
  } else {
    await createTeacher(formData.value)
  }
  ElMessage.success('保存成功')
  drawerVisible.value = false
  getList()
}

const handleDelete = (row: any) => {
  ElMessageBox.confirm(`确认删除教师“${row.teacherName}”？`, '提示信息', {
    type: 'warning',
    confirmButtonText: '确认',
    cancelButtonText: '取消'
  }).then(async () => {
    await deleteTeacher(row.id)
    ElMessage.success('删除成功')
    getList()
  })
}

const handleOpenAccount = () => {
  accountForm.value = {}
  accountDrawerVisible.value = true
}

const submitOpenAccount = async () => {
  const res = await openTeacherAccount(accountForm.value)
  if (res.data.code === '0') {
    ElMessage.success(`教师账号已开通：${res.data.data?.username || accountForm.value.phone}`)
    accountDrawerVisible.value = false
    getList()
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
  display: flex;
  justify-content: flex-end;
  margin-top: 15px;
}
</style>
