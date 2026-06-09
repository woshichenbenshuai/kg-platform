<template>
  <div class="app-container">
    <el-card class="search-header-area" shadow="never">
      <el-form :model="searchForm" label-position="left">
        <el-form-item label="学生" label-width="45px" style="width: 260px">
          <el-select v-model="searchForm.studentId" placeholder="全部" clearable filterable>
            <el-option v-for="item in studentOptions" :key="item.id" :label="studentLabel(item)" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="家长" label-width="45px" style="width: 260px">
          <el-select v-model="searchForm.guardianId" placeholder="全部" clearable filterable>
            <el-option v-for="item in guardianOptions" :key="item.id" :label="guardianLabel(item)" :value="item.id" />
          </el-select>
        </el-form-item>
      </el-form>
      <div class="btn-box">
        <el-button class="search" @click="getList">查询</el-button>
        <el-button class="add-btn" @click="handleCreate">新增绑定</el-button>
      </div>
    </el-card>

    <div class="content-box">
      <div class="left-box" style="width: 100%">
        <el-table :data="tableData" style="width: 100%" stripe v-loading="loading"
          :header-cell-style="{ fontSize: '14px', background: '#D6DEE7', color: '#0F243C', fontWeight: 'normal' }"
          :row-style="{ fontSize: '14px', color: '#1F2025' }">
          <el-table-column label="学生" min-width="150">
            <template #default="{ row }">{{ studentName(row.studentId) }}</template>
          </el-table-column>
          <el-table-column label="家长" min-width="150">
            <template #default="{ row }">{{ guardianName(row.guardianId) }}</template>
          </el-table-column>
          <el-table-column prop="relationType" label="关系" min-width="100" />
          <el-table-column label="主联系人" width="100" align="center">
            <template #default="{ row }">
              <el-tag :type="row.primaryContact ? 'success' : 'info'">{{ row.primaryContact ? '是' : '否' }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="状态" width="100" align="center">
            <template #default="{ row }">
              <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '启用' : '停用' }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" fixed="right" width="160" align="center">
            <template #default="{ row }">
              <el-button type="primary" link size="small" @click="handleEdit(row)">编辑</el-button>
              <el-button type="danger" link size="small" @click="handleDelete(row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination background v-model:current-page="listQuery.current" v-model:page-size="listQuery.size"
          :page-sizes="[10, 20, 50]" layout="total, prev, pager, next, sizes" :total="total"
          @size-change="getList" @current-change="getList" />
      </div>
    </div>

    <el-drawer :title="drawerTitle" v-model="drawerVisible" size="500px" direction="rtl">
      <el-form :model="formData" label-position="right" label-width="95px" style="padding: 0 20px">
        <el-form-item label="学生：">
          <el-select v-model="formData.studentId" placeholder="请选择学生" filterable style="width: 100%">
            <el-option v-for="item in studentOptions" :key="item.id" :label="studentLabel(item)" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="家长：">
          <el-select v-model="formData.guardianId" placeholder="请选择家长" filterable style="width: 100%">
            <el-option v-for="item in guardianOptions" :key="item.id" :label="guardianLabel(item)" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="关系："><el-input v-model="formData.relationType" placeholder="例如：父亲、母亲、爷爷" /></el-form-item>
        <el-form-item label="主联系人："><el-switch v-model="formData.primaryContact" /></el-form-item>
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
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { fetchStudents } from '@/api/kinder/student'
import { fetchGuardians } from '@/api/kinder/guardian'
import {
  createStudentGuardianRelation,
  deleteStudentGuardianRelation,
  fetchStudentGuardianRelations,
  updateStudentGuardianRelation
} from '@/api/kinder/student-guardian-relation'

const loading = ref(false)
const tableData = ref<any[]>([])
const studentOptions = ref<any[]>([])
const guardianOptions = ref<any[]>([])
const total = ref(0)
const listQuery = reactive({ current: 1, size: 10 })
const searchForm = reactive({ studentId: '' as number | string, guardianId: '' as number | string })
const drawerVisible = ref(false)
const drawerTitle = ref('')
const formData = ref<any>({})

const studentLabel = (item: any) => `${item.studentName}（${item.studentNo}）`
const guardianLabel = (item: any) => item.phone ? `${item.guardianName}（${item.phone}）` : item.guardianName
const studentName = (id: number | string) => studentOptions.value.find(item => String(item.id) === String(id))?.studentName || id
const guardianName = (id: number | string) => guardianOptions.value.find(item => String(item.id) === String(id))?.guardianName || id

const loadOptions = async () => {
  const [students, guardians] = await Promise.all([
    fetchStudents({ current: 1, size: 500, status: 1 }),
    fetchGuardians({ current: 1, size: 500, status: 1 })
  ])
  if (students.data.code === '0') studentOptions.value = students.data.data?.records || []
  if (guardians.data.code === '0') guardianOptions.value = guardians.data.data?.records || []
}

const getList = async () => {
  loading.value = true
  try {
    const res = await fetchStudentGuardianRelations({ current: listQuery.current, size: listQuery.size, ...searchForm })
    if (res.data.code === '0') {
      tableData.value = res.data.data?.records || []
      total.value = res.data.data?.total || 0
    }
  } finally {
    loading.value = false
  }
}

const handleCreate = () => {
  formData.value = { status: 1, primaryContact: false }
  drawerTitle.value = '新增绑定'
  drawerVisible.value = true
}

const handleEdit = (row: any) => {
  formData.value = { ...row }
  drawerTitle.value = '编辑绑定'
  drawerVisible.value = true
}

const submitForm = async () => {
  if (formData.value.id) await updateStudentGuardianRelation(formData.value)
  else await createStudentGuardianRelation(formData.value)
  ElMessage.success('保存成功')
  drawerVisible.value = false
  getList()
}

const handleDelete = (row: any) => {
  ElMessageBox.confirm('确认删除该学生家长绑定关系？', '提示信息', { type: 'warning' }).then(async () => {
    await deleteStudentGuardianRelation(row.id)
    ElMessage.success('删除成功')
    getList()
  })
}

onMounted(async () => {
  await loadOptions()
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
