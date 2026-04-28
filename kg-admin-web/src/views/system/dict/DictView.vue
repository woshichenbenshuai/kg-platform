<template>
  <div class="app-container dictIndex" v-loading="isPageLoading">
    <el-card class="search-header-area" shadow="never">
      <el-form ref="form" :model="searchForm" label-width="70px" label-position="left">
        <el-form-item label="字典编码" style="width: 300px">
          <el-input v-model="searchForm.code" placeholder="请输入字典编码" clearable />
        </el-form-item>
        <el-form-item label="字典名称" style="width: 300px">
          <el-input v-model="searchForm.name" placeholder="请输入字典名称" clearable />
        </el-form-item>
      </el-form>
      <div class="btn-box">
        <el-button class="search" @click="searchClick">查 询</el-button>
        <el-button class="add-btn" @click="addDictionaryClick">新 增</el-button>
      </div>
    </el-card>

    <div class="content-box">
      <div class="left-box" :style="{ width: childrenDictionary.length > 0 ? '70%' : '100%' }">
        <el-table
          :data="dictionaryList"
          style="width: 100%"
          :stripe="true"
          :header-cell-style="{ fontSize: '14px', background: '#D6DEE7', color: '#0F243C', fontWeight: 'normal' }"
          :row-style="{ fontSize: '14px', color: '#1F2025' }"
          v-loading="isLoading"
        >
          <el-table-column prop="code" label="字典编码" min-width="190" show-overflow-tooltip align="center" />
          <el-table-column prop="name" label="字典名称" min-width="190" show-overflow-tooltip align="center" />
          <el-table-column prop="remarks" label="功能描述" min-width="200" show-overflow-tooltip align="center" />
          <el-table-column label="操作" fixed="right" min-width="280" width="280" align="center">
            <template #default="scope">
              <el-button type="primary" link size="small" @click="openChildClick(scope.row)">查看字典值</el-button>
              <el-button type="success" link size="small" @click="addDictionaryDetailClick(scope.row)">新增字典值</el-button>
              <el-button type="primary" link size="small" @click="editDictionaryClick(scope.row)">编辑</el-button>
              <el-button type="danger" link size="small" @click="deleteDictionaryClick(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          background
          v-model:current-page="pagination.current"
          v-model:page-size="pagination.pageSize"
          :page-sizes="[10, 20, 30, 40]"
          layout="total, prev, pager, next, sizes"
          :total="pagination.total"
          @size-change="getPageDictionaryList"
          @current-change="getPageDictionaryList"
        />
      </div>

      <div class="right-box" style="width: 28%;" v-if="childrenDictionary.length > 0">
        <el-table
          :data="childrenDictionary"
          style="width: 100%"
          :stripe="true"
          :header-cell-style="{ fontSize: '14px', background: '#D6DEE7', color: '#0F243C', fontWeight: 'normal' }"
          :row-style="{ fontSize: '14px', color: '#1F2025' }"
          v-loading="isChildLoading"
        >
          <el-table-column prop="label" label="字典标签" min-width="120" show-overflow-tooltip />
          <el-table-column prop="value" label="字典键值" min-width="120" show-overflow-tooltip />
          <el-table-column prop="status" label="状态" min-width="100" show-overflow-tooltip>
            <template #default="scope">
              <span v-if="scope.row.status">启用</span>
              <span v-else>禁用</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" fixed="right" width="100" min-width="100">
            <template #default="scope">
              <el-button type="primary" link size="small" @click="editDictionaryDetailClick(scope.row)">编辑</el-button>
              <el-button type="danger" link size="small" @click="deleteDictionaryDetailClick(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>

    <!-- 字典新增及编辑 -->
    <el-drawer :title="dictionaryForm.id ? '编辑字典' : '新增字典'" v-model="isOpenDictionaryDrawer" size="500px" direction="rtl">
      <el-form :model="dictionaryForm" label-position="right" label-width="95px" style="padding: 0 20px;">
        <el-form-item label="名称：">
          <el-input v-model="dictionaryForm.name" placeholder="请输入字典名称" style="width: 350px" />
        </el-form-item>
        <el-form-item label="编码：">
          <el-input :disabled="!!dictionaryForm.id" v-model="dictionaryForm.code" placeholder="请输入字典编码" style="width: 350px" />
        </el-form-item>
        <el-form-item label="功能描述：">
          <el-input v-model="dictionaryForm.remarks" type="textarea" :autosize="{ minRows: 3, maxRows: 6 }" placeholder="请输入功能描述" style="width: 350px" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div style="padding: 0 20px;">
          <el-button @click="isOpenDictionaryDrawer = false">取消</el-button>
          <el-button type="primary" @click="submitDictionaryForm">确定</el-button>
        </div>
      </template>
    </el-drawer>

    <!-- 字典子项新增及编辑 -->
    <el-drawer :title="dictionaryDetailForm.id ? '编辑字典值' : '新增字典值'" v-model="isOpenDictionaryDetailDrawer" size="500px" direction="rtl">
      <el-form :model="dictionaryDetailForm" label-position="right" label-width="95px" style="padding: 0 20px;">
        <el-form-item label="所属字典：">
          <el-input :model-value="dictionaryDetailForm.parentName" :disabled="true" style="width: 350px" />
        </el-form-item>
        <el-form-item label="标签名：">
          <el-input v-model="dictionaryDetailForm.label" placeholder="请输入字典值名称" style="width: 350px" />
        </el-form-item>
        <el-form-item label="键值：">
          <el-input v-model="dictionaryDetailForm.value" placeholder="请输入字典值编码" style="width: 350px" />
        </el-form-item>
        <el-form-item label="状态：">
          <el-radio-group v-model="dictionaryDetailForm.status">
            <el-radio :value="true">启用</el-radio>
            <el-radio :value="false">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注：">
          <el-input v-model="dictionaryDetailForm.remarks" type="textarea" :autosize="{ minRows: 3, maxRows: 6 }" placeholder="请输入备注" style="width: 350px" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div style="padding: 0 20px;">
          <el-button @click="isOpenDictionaryDetailDrawer = false">取消</el-button>
          <el-button type="primary" @click="submitDictionaryDetailForm">确定</el-button>
        </div>
      </template>
    </el-drawer>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import {
  fetchDictTypes, createDictType, updateDictType, deleteDictType,
  fetchDictValues, createDictValue, updateDictValue, deleteDictValue
} from '@/api/system/dict'
import { ElMessage, ElMessageBox } from 'element-plus'

const isLoading = ref(false)
const isChildLoading = ref(false)
const isPageLoading = ref(false)

const searchForm = reactive({ code: '', name: '' })
const pagination = reactive({ current: 1, pageSize: 10, total: 0 })

const dictionaryList = ref([])
const childrenDictionary = ref<any[]>([])
const currentParent = ref<any>(null)

const isOpenDictionaryDrawer = ref(false)
const isOpenDictionaryDetailDrawer = ref(false)
const dictionaryForm = ref<any>({})
const dictionaryDetailForm = ref<any>({})

const getPageDictionaryList = async () => {
  isLoading.value = true
  try {
    const res = await fetchDictTypes({ current: pagination.current, size: pagination.pageSize, ...searchForm })
    if (res.data.code === '0') {
      dictionaryList.value = res.data.data?.records || []
      pagination.total = res.data.data?.total || 0
    }
  } catch (e) {
    console.error(e)
  } finally {
    isLoading.value = false
  }
}

const getDictionaryDetailAll = async (parent: any) => {
  isChildLoading.value = true
  try {
    const res = await fetchDictValues({ current: 1, size: 100, type: parent.code })
    if (res.data.code === '0') {
      const list = res.data.data?.records || []
      list.forEach((item: any) => { item.parentName = parent.name })
      childrenDictionary.value = list
    }
  } catch (e) {
    console.error(e)
  } finally {
    isChildLoading.value = false
  }
}

const searchClick = () => {
  pagination.current = 1
  getPageDictionaryList()
}

const openChildClick = (row: any) => {
  currentParent.value = row
  getDictionaryDetailAll(row)
}

const addDictionaryClick = () => {
  dictionaryForm.value = {}
  isOpenDictionaryDrawer.value = true
}

const editDictionaryClick = (row: any) => {
  dictionaryForm.value = { id: row.id, name: row.name, code: row.code, remarks: row.remarks }
  isOpenDictionaryDrawer.value = true
}

const deleteDictionaryClick = (row: any) => {
  ElMessageBox.confirm('确认删除该字典？', '提示信息', { confirmButtonText: '确认', cancelButtonText: '取消', type: 'warning' }).then(async () => {
    await deleteDictType(row.id)
    ElMessage.success('删除成功')
    if (currentParent.value?.id === row.id) {
      currentParent.value = null
      childrenDictionary.value = []
    }
    getPageDictionaryList()
  })
}

const submitDictionaryForm = async () => {
  try {
    if (dictionaryForm.value.id) {
      await updateDictType(dictionaryForm.value)
    } else {
      await createDictType(dictionaryForm.value)
    }
    ElMessage.success('保存成功')
    isOpenDictionaryDrawer.value = false
    getPageDictionaryList()
  } catch (e) {
    console.error(e)
  }
}

const addDictionaryDetailClick = (row: any) => {
  currentParent.value = row
  dictionaryDetailForm.value = { type: row.code, dictTypeId: row.id, parentName: row.name, status: true }
  isOpenDictionaryDetailDrawer.value = true
}

const editDictionaryDetailClick = (row: any) => {
  dictionaryDetailForm.value = Object.assign({}, row)
  isOpenDictionaryDetailDrawer.value = true
}

const deleteDictionaryDetailClick = (row: any) => {
  ElMessageBox.confirm('确认删除该字典值？', '提示信息', { confirmButtonText: '确认', cancelButtonText: '取消', type: 'warning' }).then(async () => {
    await deleteDictValue(row.id)
    ElMessage.success('删除成功')
    if (currentParent.value) getDictionaryDetailAll(currentParent.value)
  })
}

const submitDictionaryDetailForm = async () => {
  try {
    if (dictionaryDetailForm.value.id) {
      await updateDictValue(dictionaryDetailForm.value)
    } else {
      await createDictValue(dictionaryDetailForm.value)
    }
    ElMessage.success('保存成功')
    isOpenDictionaryDetailDrawer.value = false
    if (currentParent.value) getDictionaryDetailAll(currentParent.value)
  } catch (e) {
    console.error(e)
  }
}

onMounted(() => {
  getPageDictionaryList()
})
</script>

<style lang="scss" scoped>
.dictIndex {
  :deep(.el-card__body) {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
}
.content-box {
  display: flex;
  margin-top: 20px;
  gap: 2%;
  .left-box {
    background: #fff;
    padding: 15px;
    border-radius: 4px;
    flex: 1;
  }
  .right-box {
    background: #fff;
    padding: 15px;
    border-radius: 4px;
    width: 350px;
  }
}
.el-pagination {
  margin-top: 15px;
  display: flex;
  justify-content: flex-end;
}
</style>
