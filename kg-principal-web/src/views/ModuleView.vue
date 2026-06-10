<template>
  <main class="content-stack">
    <section class="module-head">
      <div>
        <p>{{ config.scopeLabel }}</p>
        <h2>{{ config.title }}</h2>
        <span>{{ config.description }}</span>
      </div>
      <button v-if="config.createUrl" @click="openCreate">新增</button>
    </section>

    <section class="panel search-panel">
      <input v-model="keyword" :placeholder="config.searchPlaceholder" />
      <button @click="refreshPage">刷新</button>
    </section>

    <section class="panel">
      <p v-if="filteredRecords.length === 0" class="empty">{{ records.length === 0 ? config.emptyText : '没有匹配的数据' }}</p>
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
        <div v-if="config.updateUrl || config.deleteUrl" class="card-actions">
          <button v-if="config.updateUrl" @click="openEdit(row)">编辑</button>
          <button v-if="config.deleteUrl" class="danger" @click="remove(row)">删除</button>
        </div>
      </article>
    </section>

    <div v-if="editing" class="drawer-mask" @click.self="editing = null">
      <form class="drawer" @submit.prevent="submit">
        <h3>{{ editingMode }}{{ config.title }}</h3>
        <label v-for="field in config.fields" :key="field.key">
          {{ field.label }}<em v-if="field.required">*</em>
          <textarea v-if="field.type === 'textarea'" v-model="editing[field.key]" :placeholder="field.placeholder" />
          <select v-else-if="field.type === 'select'" v-model="editing[field.key]">
            <option value="">请选择{{ field.label }}</option>
            <option v-for="option in fieldOptions(field)" :key="String(option.value)" :value="option.value">
              {{ option.label }}
            </option>
          </select>
          <input v-else :type="field.type || 'text'" v-model="editing[field.key]" :placeholder="field.placeholder" />
        </label>
        <div class="drawer-actions">
          <button type="button" class="ghost" @click="editing = null">取消</button>
          <button>{{ editingMode === '新增' ? '创建' : '保存' }}</button>
        </div>
      </form>
    </div>
  </main>
</template>

<script setup lang="ts">
import { computed, onMounted, ref, watch } from 'vue'
import { useRoute } from 'vue-router'
import {
  createItem,
  deleteItem,
  listModule,
  listOptionSource,
  modules,
  optionSources,
  updateItem,
  type FieldOption,
  type ModuleConfig,
  type ModuleField,
  type OptionSource
} from '@/api/portal'

const route = useRoute()
const records = ref<Record<string, any>[]>([])
const keyword = ref('')
const editing = ref<Record<string, any> | null>(null)
const editingMode = ref('新增')
const sourceOptions = ref<Record<string, FieldOption[]>>({})
const config = computed<ModuleConfig>(() => modules[String(route.params.moduleKey)] || modules.classes)

const filteredRecords = computed(() => {
  const key = keyword.value.trim().toLowerCase()
  if (!key) return records.value
  return records.value.filter((row) => config.value.keywordFields.some((fieldKey) => formatValue(fieldKey, row[fieldKey]).toLowerCase().includes(key)))
})

function firstValue(row: Record<string, any>) {
  return config.value.columns.map((item) => formatValue(item.key, row[item.key])).find((value) => value && value !== '-') || config.value.title
}

function formatValue(key: string, value: unknown) {
  if (value === null || value === undefined || value === '') return '-'
  if (key === 'status') return Number(value) === 1 ? '启用' : '停用'
  if (key === 'primaryContact') return value === true || value === 1 ? '是' : '否'
  const matchedOption = optionForKey(key, value)
  if (matchedOption) return matchedOption.label
  return String(value)
}

function optionForKey(key: string, value: unknown) {
  const field = config.value.fields.find((item) => item.key === key)
  return fieldOptions(field).find((option) => String(option.value) === String(value))
}

function fieldOptions(field?: ModuleField) {
  if (!field) return []
  if (field.options) return field.options
  if (field.source) return sourceOptions.value[field.source] || []
  return []
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
  editing.value = { ...defaults(), ...row }
}

async function load() {
  const payload = (await listModule(config.value, { current: 1, size: 100 })).data.data
  records.value = Array.isArray(payload) ? payload : payload.records || []
}

async function loadOptions() {
  const sources = Array.from(new Set(config.value.fields.map((field) => field.source).filter(Boolean))) as OptionSource[]
  const entries = await Promise.all(sources.map(async (source) => [source, await fetchOptions(source)] as const))
  sourceOptions.value = Object.fromEntries(entries)
}

async function fetchOptions(source: OptionSource) {
  const payload = (await listOptionSource(source)).data.data
  const rows = Array.isArray(payload) ? payload : payload.records || []
  const sourceConfig = optionSources[source]
  return rows.map((row) => ({ value: row.id as string | number, label: optionLabel(row, sourceConfig.labelKeys, sourceConfig.emptyLabel) }))
}

function optionLabel(row: Record<string, unknown>, labelKeys: string[], emptyLabel: string) {
  const parts = labelKeys.map((key) => row[key]).filter((value) => value !== null && value !== undefined && value !== '').map(String)
  return parts.length > 0 ? parts.join(' / ') : `${emptyLabel} #${row.id}`
}

async function refreshPage() {
  await loadOptions()
  await load()
}

function normalizePayload() {
  if (!editing.value) return {}
  const payload: Record<string, unknown> = {}
  if (editing.value.id) payload.id = editing.value.id
  for (const field of config.value.fields) {
    const value = editing.value[field.key]
    if (value === '') {
      payload[field.key] = null
      continue
    }
    if (field.valueType === 'number' && value !== null && value !== undefined) payload[field.key] = Number(value)
    if (field.valueType === 'boolean') payload[field.key] = value === true || value === 'true' || value === 1
  }
  return payload
}

async function submit() {
  if (!editing.value) return
  const payload = normalizePayload()
  if (payload.id) await updateItem(config.value, payload)
  else await createItem(config.value, payload)
  editing.value = null
  await refreshPage()
}

async function remove(row: Record<string, any>) {
  if (confirm(config.value.deleteConfirmText)) {
    await deleteItem(config.value, row.id)
    await refreshPage()
  }
}

watch(() => route.params.moduleKey, async () => {
  keyword.value = ''
  editing.value = null
  await refreshPage()
})

onMounted(refreshPage)
</script>
