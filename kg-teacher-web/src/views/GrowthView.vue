<template>
  <main class="content-stack">
    <section class="module-head">
      <div>
        <p>按学生记录</p>
        <h2>学生成长记录</h2>
      </div>
      <button @click="openCreate()">新增</button>
    </section>

    <section class="panel search-panel">
      <input v-model.trim="keyword" placeholder="按学生姓名、学号或记录标题筛选" />
    </section>

    <section class="panel student-growth-list">
      <article v-for="student in filteredStudentCards" :key="String(student.id)" class="list-card student-growth-card">
        <div class="row-title">
          <b>{{ student.studentName || '未命名学生' }}</b>
          <span>{{ student.studentNo || '-' }}</span>
        </div>
        <dl>
          <dt>班级ID</dt>
          <dd>{{ student.classId || '-' }}</dd>
          <dt>记录数</dt>
          <dd>{{ recordsByStudent.get(String(student.id))?.length || 0 }}</dd>
        </dl>
        <div class="card-actions">
          <button @click="openCreate(student)">给该学生新增</button>
        </div>
        <div class="growth-records">
          <article v-for="item in recordsByStudent.get(String(student.id)) || []" :key="String(item.id)" class="growth-mini-card">
            <div class="row-title">
              <b>{{ item.title || '成长记录' }}</b>
              <span>{{ item.recordDate || '-' }}</span>
            </div>
            <p>{{ item.content || '暂无内容' }}</p>
            <dl>
              <dt>家长可见</dt>
              <dd>{{ Number(item.visibleToParent) === 1 ? '可见' : '不可见' }}</dd>
            </dl>
            <div class="card-actions">
              <button @click="edit(item)">编辑</button>
              <button class="danger" @click="remove(item.id)">删除</button>
            </div>
          </article>
          <p v-if="(recordsByStudent.get(String(student.id)) || []).length === 0" class="empty inline-empty">该学生暂无成长记录</p>
        </div>
      </article>
      <p v-if="filteredStudentCards.length === 0" class="empty">暂无学生或成长记录</p>
    </section>

    <div v-if="form" class="drawer-mask" @click.self="form = null">
      <form class="drawer" @submit.prevent="save">
        <h3>{{ form.id ? '编辑成长记录' : '新增成长记录' }}</h3>
        <label>
          学生
          <select v-model="form.studentId">
            <option value="" disabled>请选择学生</option>
            <option v-for="student in studentRows" :key="String(student.id)" :value="student.id">
              {{ student.studentName || '未命名学生' }}（{{ student.studentNo || '-' }}）
            </option>
          </select>
        </label>
        <label>
          标题
          <input v-model.trim="form.title" placeholder="例如：午睡表现" />
        </label>
        <label>
          内容
          <textarea v-model.trim="form.content" placeholder="记录这个孩子当天的表现" />
        </label>
        <label>
          日期
          <input v-model="form.recordDate" type="date" />
        </label>
        <label>
          家长可见
          <select v-model="form.visibleToParent">
            <option :value="1">可见</option>
            <option :value="0">不可见</option>
          </select>
        </label>
        <div class="drawer-actions">
          <button type="button" class="ghost" @click="form = null">取消</button>
          <button>保存</button>
        </div>
      </form>
    </div>
  </main>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { createGrowth, deleteGrowth, growthRecords, students, updateGrowth } from '@/api/teacher'

const rows = ref<Array<Record<string, any>>>([])
const studentRows = ref<Array<Record<string, any>>>([])
const keyword = ref('')
const form = ref<Record<string, any> | null>(null)
const recordsByStudent = computed(() => {
  const map = new Map<string, Array<Record<string, any>>>()
  for (const student of studentRows.value) map.set(String(student.id), [])
  for (const row of rows.value) {
    const key = String(row.studentId)
    if (!map.has(key)) map.set(key, [])
    map.get(key)?.push(row)
  }
  return map
})
const filteredStudentCards = computed(() => {
  if (!keyword.value) return studentRows.value
  return studentRows.value.filter((student) => {
    const records = recordsByStudent.value.get(String(student.id)) || []
    return JSON.stringify({ student, records }).includes(keyword.value)
  })
})

async function load() {
  const [studentResult, growthResult] = await Promise.all([students(), growthRecords()])
  studentRows.value = studentResult.data.data
  rows.value = growthResult.data.data
}

function openCreate(student?: Record<string, any>) {
  form.value = {
    studentId: student?.id || '',
    title: '',
    content: '',
    recordDate: new Date().toISOString().slice(0, 10),
    visibleToParent: 1,
    status: 1
  }
}

function edit(row: Record<string, any>) {
  form.value = { ...row }
}

async function save() {
  if (!form.value) return
  if (!form.value.studentId) return alert('请选择学生')
  if (form.value.id) await updateGrowth(form.value)
  else await createGrowth(form.value)
  form.value = null
  await load()
}

async function remove(id: string | number) {
  if (!confirm('确认删除该成长记录？')) return
  await deleteGrowth(id)
  await load()
}

onMounted(load)
</script>
