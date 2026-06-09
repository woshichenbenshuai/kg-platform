<template>
  <div class="app-container">
    <el-card class="search-header-area" shadow="never">
      <el-form :model="searchForm" label-position="left">
        <el-form-item label="班级名称" label-width="70px" style="width: 280px">
          <el-input v-model="searchForm.className" placeholder="请输入班级名称" clearable />
        </el-form-item>
        <el-form-item label="班级编码" label-width="70px" style="width: 280px">
          <el-input v-model="searchForm.classCode" placeholder="请输入班级编码" clearable />
        </el-form-item>
        <el-form-item label="状态" label-width="50px" style="width: 200px">
          <el-select v-model="searchForm.status" placeholder="全部" clearable>
            <el-option label="启用" :value="1" />
            <el-option label="停用" :value="0" />
          </el-select>
        </el-form-item>
      </el-form>
      <div class="btn-box">
        <el-button class="search" @click="getList">查询</el-button>
        <el-button class="add-btn" @click="handleCreate">新增班级</el-button>
      </div>
    </el-card>

    <div class="content-box">
      <div class="left-box" style="width: 100%">
        <el-table :data="tableData" style="width: 100%" stripe v-loading="loading"
          :header-cell-style="{ fontSize: '14px', background: '#D6DEE7', color: '#0F243C', fontWeight: 'normal' }"
          :row-style="{ fontSize: '14px', color: '#1F2025' }">
          <el-table-column prop="classCode" label="班级编码" min-width="120" />
          <el-table-column prop="className" label="班级名称" min-width="140" />
          <el-table-column prop="gradeName" label="年级" min-width="120" />
          <el-table-column label="状态" width="100" align="center">
            <template #default="{ row }">
              <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '启用' : '停用' }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间" min-width="170" align="center" />
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
        <el-form-item label="班级编码："><el-input v-model="formData.classCode" placeholder="请输入班级编码" /></el-form-item>
        <el-form-item label="班级名称："><el-input v-model="formData.className" placeholder="请输入班级名称" /></el-form-item>
        <el-form-item label="年级："><el-input v-model="formData.gradeName" placeholder="请输入年级" /></el-form-item>
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
import { createClass, deleteClass, fetchClasses, updateClass } from '@/api/kinder/class'

const loading = ref(false)
const tableData = ref<any[]>([])
const total = ref(0)
const listQuery = reactive({ current: 1, size: 10 })
const searchForm = reactive({ className: '', classCode: '', status: '' as number | string })
const drawerVisible = ref(false)
const drawerTitle = ref('')
const formData = ref<any>({})

const getList = async () => {
  loading.value = true
  try {
    const res = await fetchClasses({ current: listQuery.current, size: listQuery.size, ...searchForm })
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
  drawerTitle.value = '新增班级'
  drawerVisible.value = true
}

const handleEdit = (row: any) => {
  formData.value = { ...row }
  drawerTitle.value = '编辑班级'
  drawerVisible.value = true
}

const submitForm = async () => {
  if (formData.value.id) await updateClass(formData.value)
  else await createClass(formData.value)
  ElMessage.success('保存成功')
  drawerVisible.value = false
  getList()
}

const handleDelete = (row: any) => {
  ElMessageBox.confirm(`确认删除班级“${row.className}”？`, '提示信息', { type: 'warning' }).then(async () => {
    await deleteClass(row.id)
    ElMessage.success('删除成功')
    getList()
  })
}

onMounted(getList)
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
