<template>
  <main class="content-stack">
    <section class="module-head">
      <div>
        <p>业务闭环</p>
        <h2>{{ config.title }}</h2>
      </div>
      <button v-if="config.createUrl" @click="openCreate">新增</button>
    </section>

    <section class="panel search-panel">
      <input v-model="keyword" placeholder="输入关键词快速筛选" />
      <button @click="load">刷新</button>
    </section>

    <section class="panel">
      <p v-if="records.length === 0" class="empty">暂无数据</p>
      <article v-for="row in filteredRecords" :key="String(row.id)" class="list-card">
        <div class="row-title">
          <b>{{ firstValue(row) }}</b>
          <span>#{{ row.id }}</span>
        </div>
        <dl>
          <template v-for="column in config.columns" :key="column.key">
            <dt>{{ column.label }}</dt>
            <dd>{{ formatValue(column.key, row[column.key]) }}</dd>
          </template>
        </dl>
        <div class="card-actions">
          <button v-if="config.updateUrl" @click="openEdit(row)">编辑</button>
          <button v-if="config.deleteUrl" class="danger" @click="remove(row)">删除</button>
          <button v-if="config.approveUrl" @click="openApprove(row)">审批</button>
        </div>
      </article>
    </section>

    <div v-if="editing" class="drawer-mask" @click.self="editing = null">
      <form class="drawer" @submit.prevent="submit">
        <h3>{{ editingMode }}</h3>
        <label v-for="field in config.fields" :key="field.key">
          {{ field.label }}
          <textarea v-if="field.type === 'textarea'" v-model="editing[field.key]" />
          <input v-else :type="field.type || 'text'" v-model="editing[field.key]" />
        </label>
        <div class="drawer-actions">
          <button type="button" class="ghost" @click="editing = null">取消</button>
          <button>保存</button>
        </div>
      </form>
    </div>
  </main>
</template>

<script setup lang="ts">
import { computed, onMounted, ref, watch } from 'vue'
import { useRoute } from 'vue-router'
import { approveLeave, createItem, deleteItem, listModule, modules, updateItem, type ModuleConfig } from '@/api/portal'

const route = useRoute()
const records = ref<Record<string, any>[]>([])
const keyword = ref('')
const editing = ref<Record<string, any> | null>(null)
const editingMode = ref('新增')
const config = computed<ModuleConfig>(() => modules[String(route.params.moduleKey)] || modules.classes)
const filteredRecords = computed(() => records.value.filter((row) => JSON.stringify(row).includes(keyword.value)))

function firstValue(row: Record<string, any>) {
  return config.value.columns.map((item) => row[item.key]).find(Boolean) || config.value.title
}

function formatValue(key: string, value: unknown) {
  if (value === null || value === undefined || value === '') return '-'
  if (key === 'status') return Number(value) === 1 ? '启用' : '停用'
  if (key === 'visibleToParent') return Number(value) === 1 ? '可见' : '不可见'
  if (key === 'approveStatus') {
    if (value === 'APPROVED') return '已通过'
    if (value === 'REJECTED') return '已驳回'
    return '待审批'
  }
  return String(value)
}

function defaults() {
  return Object.fromEntries(config.value.fields.map((field) => [field.key, field.default ?? '']))
}

function openCreate() {
  editingMode.value = '新增'
  editing.value = defaults()
}

function openEdit(row: Record<string, any>) {
  editingMode.value = '编辑'
  editing.value = { ...row }
}

function openApprove(row: Record<string, any>) {
  editingMode.value = '审批'
  editing.value = { id: row.id, approveStatus: 'APPROVED', approveRemark: '' }
}

async function load() {
  records.value = (await listModule(config.value, { current: 1, size: 50 })).data.data.records || []
}

async function submit() {
  if (!editing.value) return
  if (config.value.approveUrl) await approveLeave(editing.value)
  else if (editing.value.id) await updateItem(config.value, editing.value)
  else await createItem(config.value, editing.value)
  editing.value = null
  await load()
}

async function remove(row: Record<string, any>) {
  if (confirm('确认删除该记录？')) {
    await deleteItem(config.value, row.id)
    await load()
  }
}

watch(() => route.params.moduleKey, load)
onMounted(load)
</script>
