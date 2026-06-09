<template>
  <div class="app-container">
    <el-card class="search-header-area" shadow="never">
      <el-form :model="searchForm" label-position="left">
        <el-form-item label="学生" label-width="45px" style="width: 260px"><el-select v-model="searchForm.studentId" placeholder="全部" clearable filterable><el-option v-for="item in studentOptions" :key="item.id" :label="studentLabel(item)" :value="item.id" /></el-select></el-form-item>
        <el-form-item label="状态" label-width="45px" style="width: 220px"><el-select v-model="searchForm.approveStatus" placeholder="全部" clearable><el-option label="待审批" value="PENDING" /><el-option label="已同意" value="APPROVED" /><el-option label="已拒绝" value="REJECTED" /></el-select></el-form-item>
      </el-form>
      <div class="btn-box"><el-button class="search" @click="getList">查询</el-button></div>
    </el-card>
    <div class="content-box"><div class="left-box" style="width: 100%">
      <el-table :data="tableData" stripe v-loading="loading" style="width: 100%" :header-cell-style="{ fontSize: '14px', background: '#D6DEE7', color: '#0F243C', fontWeight: 'normal' }">
        <el-table-column label="学生" min-width="120"><template #default="{ row }">{{ studentName(row.studentId) }}</template></el-table-column>
        <el-table-column prop="startDate" label="开始日期" width="120" align="center" />
        <el-table-column prop="endDate" label="结束日期" width="120" align="center" />
        <el-table-column prop="reason" label="请假原因" min-width="180" show-overflow-tooltip />
        <el-table-column label="审批状态" width="110" align="center"><template #default="{ row }"><el-tag :type="statusType(row.approveStatus)">{{ statusText(row.approveStatus) }}</el-tag></template></el-table-column>
        <el-table-column prop="approveRemark" label="审批备注" min-width="150" show-overflow-tooltip />
        <el-table-column label="操作" fixed="right" width="210" align="center"><template #default="{ row }"><el-button type="success" link size="small" @click="handleApprove(row, 'APPROVED')">同意</el-button><el-button type="warning" link size="small" @click="handleApprove(row, 'REJECTED')">拒绝</el-button><el-button type="danger" link size="small" @click="handleDelete(row)">删除</el-button></template></el-table-column>
      </el-table>
      <el-pagination background v-model:current-page="listQuery.current" v-model:page-size="listQuery.size" :page-sizes="[10, 20, 50]" layout="total, prev, pager, next, sizes" :total="total" @size-change="getList" @current-change="getList" />
    </div></div>
  </div>
</template>
<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { fetchStudents } from '@/api/kinder/student'
import { approveLeaveRequest, deleteLeaveRequest, fetchLeaveRequests } from '@/api/kinder/leave-request'
const loading = ref(false)
const tableData = ref<any[]>([])
const studentOptions = ref<any[]>([])
const total = ref(0)
const listQuery = reactive({ current: 1, size: 10 })
const searchForm = reactive({ studentId: '' as number | string, approveStatus: '' })
const studentLabel = (item: any) => `${item.studentName}（${item.studentNo}）`
const studentName = (id: number | string) => studentOptions.value.find(item => String(item.id) === String(id))?.studentName || id
const statusText = (status: string) => ({ PENDING: '待审批', APPROVED: '已同意', REJECTED: '已拒绝' }[status] || status)
const statusType = (status: string) => status === 'APPROVED' ? 'success' : status === 'REJECTED' ? 'danger' : 'warning'
const loadStudents = async () => { const res = await fetchStudents({ current: 1, size: 500, status: 1 }); if (res.data.code === '0') studentOptions.value = res.data.data?.records || [] }
const getList = async () => {
  loading.value = true
  try { const res = await fetchLeaveRequests({ current: listQuery.current, size: listQuery.size, ...searchForm }); if (res.data.code === '0') { tableData.value = res.data.data?.records || []; total.value = res.data.data?.total || 0 } } finally { loading.value = false }
}
const handleApprove = async (row: any, approveStatus: string) => {
  const action = approveStatus === 'APPROVED' ? '同意' : '拒绝'
  const { value } = await ElMessageBox.prompt(`请输入${action}备注`, '审批请假', { inputType: 'textarea', confirmButtonText: action, cancelButtonText: '取消' })
  await approveLeaveRequest({ id: row.id, approveStatus, approveRemark: value })
  ElMessage.success('审批完成')
  getList()
}
const handleDelete = (row: any) => { ElMessageBox.confirm('确认删除该请假记录？', '提示信息', { type: 'warning' }).then(async () => { await deleteLeaveRequest(row.id); ElMessage.success('删除成功'); getList() }) }
onMounted(async () => { await loadStudents(); getList() })
</script>
<style lang="scss" scoped>
.content-box { display: flex; margin-top: 20px; .left-box { background: #fff; padding: 15px; border-radius: 4px; } }
.el-pagination { display: flex; justify-content: flex-end; margin-top: 15px; }
</style>
