<template>
  <div class="app-container" v-loading="pageLoading">
    <el-card class="search-header-area" shadow="never">
      <el-form :model="searchForm" label-position="left">
        <el-form-item label="幼儿园名称" label-width="90px" style="width: 320px">
          <el-input v-model="searchForm.tenantName" placeholder="请输入幼儿园名称" clearable />
        </el-form-item>
        <el-form-item label="状态" label-width="50px" style="width: 220px">
          <el-select v-model="searchForm.status" placeholder="全部" clearable>
            <el-option label="启用" :value="true" />
            <el-option label="停用" :value="false" />
          </el-select>
        </el-form-item>
      </el-form>
      <div class="btn-box">
        <el-button class="search" @click="getList">查 询</el-button>
        <el-button class="add-btn" @click="handleCreateTenant">新增幼儿园</el-button>
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
          <el-table-column prop="tenantName" label="幼儿园名称" min-width="150" show-overflow-tooltip />
          <el-table-column prop="tenantCode" label="幼儿园编号" min-width="150" show-overflow-tooltip />
          <el-table-column prop="contactName" label="联系人" min-width="110" show-overflow-tooltip />
          <el-table-column prop="contactPhone" label="联系电话" min-width="130" show-overflow-tooltip />
          <el-table-column prop="expireDate" label="到期日期" min-width="120" align="center" />
          <el-table-column label="数据库" width="120" align="center">
            <template #default="{ row }">
              <el-tag :type="row.hasDatabase ? 'success' : 'info'">{{ row.hasDatabase ? '已生成' : '未生成' }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="status" label="状态" align="center" width="100">
            <template #default="{ row }">
              <el-tag :type="row.status ? 'success' : 'danger'">{{ row.status ? '启用' : '停用' }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" fixed="right" width="360" align="center">
            <template #default="{ row }">
              <el-button type="primary" link size="small" @click="handleEditTenant(row)">编辑</el-button>
              <el-button v-if="!row.hasDatabase" type="warning" link size="small" @click="handleGenerateDatabase(row)">生成幼儿园数据库</el-button>
              <el-button type="success" link size="small" @click="handleDbInfo(row)">数据库信息</el-button>
              <el-button type="danger" link size="small" :disabled="row.hasDatabase" @click="handleDeleteTenant(row)">删除</el-button>
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

    <el-drawer :title="tenantDrawerTitle" v-model="tenantDrawerVisible" size="520px" direction="rtl">
      <el-form :model="tenantForm" label-position="right" label-width="105px" style="padding: 0 20px">
        <el-alert
          v-if="!tenantForm.id"
          title="幼儿园编号可留空，系统会自动生成。保存后可在列表点击“生成幼儿园数据库”按模板创建业务库。"
          type="info"
          show-icon
          :closable="false"
          style="margin-bottom: 18px"
        />
        <el-form-item label="幼儿园名称：">
          <el-input v-model="tenantForm.tenantName" placeholder="请输入幼儿园名称" />
        </el-form-item>
        <el-form-item label="幼儿园编号：">
          <el-input v-model="tenantForm.tenantCode" placeholder="可留空自动生成，如 KINDER_00001234" :disabled="!!tenantForm.id" />
        </el-form-item>
        <el-form-item label="联系人：">
          <el-input v-model="tenantForm.contactName" placeholder="请输入联系人" />
        </el-form-item>
        <el-form-item label="联系电话：">
          <el-input v-model="tenantForm.contactPhone" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="地址：">
          <el-input v-model="tenantForm.address" placeholder="请输入地址" />
        </el-form-item>
        <el-form-item label="到期日期：">
          <el-date-picker v-model="tenantForm.expireDate" type="date" value-format="YYYY-MM-DD" placeholder="请选择到期日期" style="width: 100%" />
        </el-form-item>
        <el-form-item label="状态：">
          <el-radio-group v-model="tenantForm.status">
            <el-radio :value="true">启用</el-radio>
            <el-radio :value="false">停用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注：">
          <el-input v-model="tenantForm.remarks" type="textarea" :rows="3" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div style="padding: 0 20px">
          <el-button @click="tenantDrawerVisible = false">取消</el-button>
          <el-button type="primary" @click="submitTenant">保存</el-button>
        </div>
      </template>
    </el-drawer>

    <el-drawer title="幼儿园数据库信息" v-model="dbDrawerVisible" size="520px" direction="rtl">
      <el-alert
        v-if="currentTenant"
        :title="`当前幼儿园：${currentTenant.tenantName}`"
        type="info"
        show-icon
        :closable="false"
        style="margin: 0 20px 18px"
      />
      <el-empty v-if="!dbInfo" description="当前幼儿园还未生成数据库">
        <el-button v-if="currentTenant" type="primary" @click="handleGenerateDatabase(currentTenant)">生成幼儿园数据库</el-button>
      </el-empty>
      <el-descriptions v-else :column="1" border style="margin: 0 20px">
        <el-descriptions-item label="数据库名">{{ dbInfo.dbName || '-' }}</el-descriptions-item>
        <el-descriptions-item label="状态">{{ dbInfo.dbStatus === 'NORMAL' ? '正常' : dbInfo.dbStatus || '-' }}</el-descriptions-item>
      </el-descriptions>
      <template #footer>
        <div style="padding: 0 20px">
          <el-button @click="dbDrawerVisible = false">关闭</el-button>
        </div>
      </template>
    </el-drawer>
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { createTenant, deleteTenant, fetchTenants, rebuildTenantDatabase, updateTenant } from '@/api/system/tenant'
import { fetchTenantDbConfigs } from '@/api/system/tenant-db-config'

const loading = ref(false)
const pageLoading = ref(false)
const tableData = ref<any[]>([])
const total = ref(0)
const listQuery = reactive({ current: 1, size: 10 })
const searchForm = reactive({ tenantName: '', status: '' as boolean | string })

const tenantDrawerVisible = ref(false)
const tenantDrawerTitle = ref('')
const tenantForm = ref<any>({})

const dbDrawerVisible = ref(false)
const currentTenant = ref<any>(null)
const dbInfo = ref<any>(null)

const getList = async () => {
  loading.value = true
  try {
    const res = await fetchTenants({ current: listQuery.current, size: listQuery.size, ...searchForm })
    if (res.data.code === '0') {
      tableData.value = await attachDatabaseInfo(res.data.data?.records || [])
      total.value = res.data.data?.total || 0
    }
  } finally {
    loading.value = false
  }
}

const attachDatabaseInfo = async (records: any[]) => {
  return Promise.all(
    records.map(async (tenant) => {
      const config = await fetchTenantDatabase(tenant.id)
      return {
        ...tenant,
        dbInfo: config,
        hasDatabase: !!config
      }
    })
  )
}

const fetchTenantDatabase = async (tenantId: number | string) => {
  const res = await fetchTenantDbConfigs({ current: 1, size: 1, bindTenantId: tenantId })
  return res.data.code === '0' ? res.data.data?.records?.[0] || null : null
}

const handleCreateTenant = () => {
  tenantForm.value = { status: true }
  tenantDrawerTitle.value = '新增幼儿园'
  tenantDrawerVisible.value = true
}

const handleEditTenant = (row: any) => {
  tenantForm.value = { ...row }
  tenantDrawerTitle.value = '编辑幼儿园'
  tenantDrawerVisible.value = true
}

const submitTenant = async () => {
  if (tenantForm.value.id) {
    await updateTenant(tenantForm.value)
  } else {
    await createTenant(tenantForm.value)
  }
  ElMessage.success(tenantForm.value.id ? '幼儿园信息已保存' : '幼儿园已保存')
  tenantDrawerVisible.value = false
  getList()
}

const handleGenerateDatabase = (row: any) => {
  ElMessageBox.confirm(
    `确认给「${row.tenantName}」生成幼儿园数据库？数据库一旦创建后不能删除，也不能重复生成。`,
    '生成数据库确认',
    {
      type: 'warning',
      confirmButtonText: '确认生成',
      cancelButtonText: '取消'
    }
  ).then(async () => {
    const res = await rebuildTenantDatabase(row.id)
    if (res.data.code === '0') {
      ElMessage.success(`幼儿园数据库已生成：${res.data.data}`)
      dbDrawerVisible.value = false
      getList()
    }
  })
}

const handleDeleteTenant = (row: any) => {
  ElMessageBox.confirm(`确认删除幼儿园「${row.tenantName}」？`, '提示信息', {
    type: 'warning',
    confirmButtonText: '确认',
    cancelButtonText: '取消'
  }).then(async () => {
    await deleteTenant(row.id)
    ElMessage.success('删除成功')
    getList()
  })
}

const handleDbInfo = async (row: any) => {
  currentTenant.value = row
  dbInfo.value = row.dbInfo || (await fetchTenantDatabase(row.id))
  dbDrawerVisible.value = true
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

.check-result {
  color: #606266;
  line-height: 32px;
}
</style>
