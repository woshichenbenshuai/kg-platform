<template>
  <div class="app-container">
    <el-card class="search-header-area" shadow="never">
      <el-form :model="searchForm" label-position="left">
        <el-form-item label="学生" label-width="45px" style="width: 260px"><el-select v-model="searchForm.studentId" placeholder="全部" clearable filterable><el-option v-for="item in studentOptions" :key="item.id" :label="studentLabel(item)" :value="item.id" /></el-select></el-form-item>
        <el-form-item label="标题" label-width="45px" style="width: 260px"><el-input v-model="searchForm.title" placeholder="请输入标题" clearable /></el-form-item>
      </el-form>
      <div class="btn-box"><el-button class="search" @click="getList">查询</el-button><el-button class="add-btn" @click="handleCreate">新增成长记录</el-button></div>
    </el-card>
    <div class="content-box"><div class="left-box" style="width: 100%">
      <el-table :data="tableData" stripe v-loading="loading" style="width: 100%" :header-cell-style="{ fontSize: '14px', background: '#D6DEE7', color: '#0F243C', fontWeight: 'normal' }">
        <el-table-column label="学生" min-width="120"><template #default="{ row }">{{ studentName(row.studentId) }}</template></el-table-column>
        <el-table-column prop="title" label="标题" min-width="160" />
        <el-table-column prop="recordDate" label="记录日期" width="120" align="center" />
        <el-table-column label="家长可见" width="100" align="center"><template #default="{ row }"><el-tag :type="row.visibleToParent === 1 ? 'success' : 'info'">{{ row.visibleToParent === 1 ? '是' : '否' }}</el-tag></template></el-table-column>
        <el-table-column label="操作" fixed="right" width="160" align="center"><template #default="{ row }"><el-button type="primary" link size="small" @click="handleEdit(row)">编辑</el-button><el-button type="danger" link size="small" @click="handleDelete(row)">删除</el-button></template></el-table-column>
      </el-table>
      <el-pagination background v-model:current-page="listQuery.current" v-model:page-size="listQuery.size" :page-sizes="[10, 20, 50]" layout="total, prev, pager, next, sizes" :total="total" @size-change="getList" @current-change="getList" />
    </div></div>
    <el-drawer :title="drawerTitle" v-model="drawerVisible" size="560px" direction="rtl">
      <el-form :model="formData" label-position="right" label-width="95px" style="padding: 0 20px">
        <el-form-item label="学生："><el-select v-model="formData.studentId" placeholder="请选择学生" filterable style="width: 100%"><el-option v-for="item in studentOptions" :key="item.id" :label="studentLabel(item)" :value="item.id" /></el-select></el-form-item>
        <el-form-item label="标题："><el-input v-model="formData.title" placeholder="请输入标题" /></el-form-item>
        <el-form-item label="记录日期："><el-date-picker v-model="formData.recordDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" /></el-form-item>
        <el-form-item label="内容："><el-input v-model="formData.content" type="textarea" :rows="6" placeholder="请输入内容" /></el-form-item>
        <el-form-item label="图片地址："><el-input v-model="formData.imageUrls" placeholder="多个地址可用英文逗号分隔" /></el-form-item>
        <el-form-item label="家长可见："><el-switch v-model="visibleToParentSwitch" /></el-form-item>
        <el-form-item label="状态："><el-radio-group v-model="formData.status"><el-radio :value="1">启用</el-radio><el-radio :value="0">停用</el-radio></el-radio-group></el-form-item>
      </el-form>
      <template #footer><div style="padding: 0 20px"><el-button @click="drawerVisible = false">取消</el-button><el-button type="primary" @click="submitForm">保存</el-button></div></template>
    </el-drawer>
  </div>
</template>
<script setup lang="ts">
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { fetchStudents } from '@/api/kinder/student'
import { createGrowthRecord, deleteGrowthRecord, fetchGrowthRecords, updateGrowthRecord } from '@/api/kinder/growth-record'
const loading = ref(false)
const tableData = ref<any[]>([])
const studentOptions = ref<any[]>([])
const total = ref(0)
const listQuery = reactive({ current: 1, size: 10 })
const searchForm = reactive({ studentId: '' as number | string, title: '' })
const drawerVisible = ref(false)
const drawerTitle = ref('')
const formData = ref<any>({})
const visibleToParentSwitch = computed({ get: () => formData.value.visibleToParent !== 0, set: (v: boolean) => { formData.value.visibleToParent = v ? 1 : 0 } })
const studentLabel = (item: any) => `${item.studentName}（${item.studentNo}）`
const studentName = (id: number | string) => studentOptions.value.find(item => String(item.id) === String(id))?.studentName || id
const loadStudents = async () => { const res = await fetchStudents({ current: 1, size: 500, status: 1 }); if (res.data.code === '0') studentOptions.value = res.data.data?.records || [] }
const getList = async () => {
  loading.value = true
  try { const res = await fetchGrowthRecords({ current: listQuery.current, size: listQuery.size, ...searchForm }); if (res.data.code === '0') { tableData.value = res.data.data?.records || []; total.value = res.data.data?.total || 0 } } finally { loading.value = false }
}
const handleCreate = () => { formData.value = { status: 1, visibleToParent: 1 }; drawerTitle.value = '新增成长记录'; drawerVisible.value = true }
const handleEdit = (row: any) => { formData.value = { ...row }; drawerTitle.value = '编辑成长记录'; drawerVisible.value = true }
const submitForm = async () => { if (formData.value.id) await updateGrowthRecord(formData.value); else await createGrowthRecord(formData.value); ElMessage.success('保存成功'); drawerVisible.value = false; getList() }
const handleDelete = (row: any) => { ElMessageBox.confirm('确认删除该成长记录？', '提示信息', { type: 'warning' }).then(async () => { await deleteGrowthRecord(row.id); ElMessage.success('删除成功'); getList() }) }
onMounted(async () => { await loadStudents(); getList() })
</script>
<style lang="scss" scoped>
.content-box { display: flex; margin-top: 20px; .left-box { background: #fff; padding: 15px; border-radius: 4px; } }
.el-pagination { display: flex; justify-content: flex-end; margin-top: 15px; }
</style>
