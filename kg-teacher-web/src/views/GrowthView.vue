<template>
  <main class="content-stack">
    <section class="module-head">
      <div>
        <p>家校可见</p>
        <h2>成长记录</h2>
      </div>
      <button @click="openCreate">新增</button>
    </section>

    <section class="panel">
      <article v-for="item in rows" :key="String(item.id)" class="list-card">
        <div class="row-title">
          <b>{{ item.title || '成长记录' }}</b>
          <span>{{ item.recordDate || '-' }}</span>
        </div>
        <dl>
          <dt>学生ID</dt>
          <dd>{{ item.studentId || '-' }}</dd>
          <dt>家长可见</dt>
          <dd>{{ Number(item.visibleToParent) === 1 ? '可见' : '不可见' }}</dd>
        </dl>
        <p>{{ item.content || '暂无内容' }}</p>
        <div class="card-actions">
          <button @click="edit(item)">编辑</button>
          <button class="danger" @click="remove(item.id)">删除</button>
        </div>
      </article>
      <p v-if="rows.length === 0" class="empty">暂无成长记录</p>
    </section>

    <div v-if="form" class="drawer-mask" @click.self="form = null">
      <form class="drawer" @submit.prevent="save">
        <h3>{{ form.id ? '编辑成长记录' : '新增成长记录' }}</h3>
        <label>
          学生ID
          <input v-model="form.studentId" type="number" placeholder="请输入学生ID" />
        </label>
        <label>
          标题
          <input v-model.trim="form.title" placeholder="例如：午睡表现" />
        </label>
        <label>
          内容
          <textarea v-model.trim="form.content" placeholder="记录孩子当天的表现" />
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
import { onMounted, ref } from 'vue'
import { createGrowth, deleteGrowth, growthRecords, updateGrowth } from '@/api/teacher'

const rows = ref<Array<Record<string, any>>>([])
const form = ref<Record<string, any> | null>(null)

async function load() {
  rows.value = (await growthRecords()).data.data
}

function openCreate() {
  form.value = {
    studentId: '',
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
