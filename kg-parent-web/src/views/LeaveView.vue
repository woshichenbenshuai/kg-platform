<template>
  <main class="content-stack">
    <section class="module-head">
      <div>
        <p>出勤沟通</p>
        <h2>请假申请</h2>
      </div>
      <button @click="open">提交</button>
    </section>

    <section class="panel">
      <article v-for="item in rows" :key="String(item.id)" class="list-card">
        <div class="row-title">
          <b>{{ item.studentName || `学生 ${item.studentId || '-'}` }}</b>
          <span>{{ statusText(item.approveStatus) }}</span>
        </div>
        <dl>
          <dt>开始</dt>
          <dd>{{ item.startDate || '-' }}</dd>
          <dt>结束</dt>
          <dd>{{ item.endDate || '-' }}</dd>
          <dt>原因</dt>
          <dd>{{ item.reason || '-' }}</dd>
          <dt>反馈</dt>
          <dd>{{ item.approveRemark || '-' }}</dd>
        </dl>
      </article>
      <p v-if="rows.length === 0" class="empty">暂无请假记录</p>
    </section>

    <div v-if="form" class="drawer-mask" @click.self="form = null">
      <form class="drawer" @submit.prevent="submit">
        <h3>提交请假</h3>
        <label>
          学生ID
          <input v-model="form.studentId" type="number" placeholder="请输入学生ID" />
        </label>
        <label>
          开始日期
          <input v-model="form.startDate" type="date" />
        </label>
        <label>
          结束日期
          <input v-model="form.endDate" type="date" />
        </label>
        <label>
          原因
          <textarea v-model.trim="form.reason" placeholder="请说明请假原因" />
        </label>
        <div class="drawer-actions">
          <button type="button" class="ghost" @click="form = null">取消</button>
          <button>提交</button>
        </div>
      </form>
    </div>
  </main>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { leaveRequests, submitLeaveRequest } from '@/api/parent'

const rows = ref<Array<Record<string, any>>>([])
const form = ref<Record<string, any> | null>(null)

function statusText(status: unknown) {
  const value = String(status || 'PENDING')
  if (value === 'APPROVED') return '已通过'
  if (value === 'REJECTED') return '已驳回'
  return '待审批'
}

async function load() {
  rows.value = (await leaveRequests()).data.data
}

function open() {
  const today = new Date().toISOString().slice(0, 10)
  form.value = { studentId: '', startDate: today, endDate: today, reason: '' }
}

async function submit() {
  if (!form.value) return
  await submitLeaveRequest(form.value)
  form.value = null
  await load()
}

onMounted(load)
</script>
