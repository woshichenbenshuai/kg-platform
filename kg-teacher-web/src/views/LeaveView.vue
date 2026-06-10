<template>
  <main class="content-stack">
    <section class="module-head">
      <div>
        <p>本班请假</p>
        <h2>请假处理</h2>
      </div>
      <button @click="load">刷新</button>
    </section>

    <section class="panel">
      <article v-for="item in rows" :key="String(item.id)" class="list-card">
        <div class="row-title">
          <b>学生 {{ item.studentId || '-' }}</b>
          <span>{{ item.approveStatus || 'PENDING' }}</span>
        </div>
        <dl>
          <dt>开始</dt>
          <dd>{{ item.startDate || '-' }}</dd>
          <dt>结束</dt>
          <dd>{{ item.endDate || '-' }}</dd>
          <dt>原因</dt>
          <dd>{{ item.reason || '-' }}</dd>
          <dt>备注</dt>
          <dd>{{ item.approveRemark || '-' }}</dd>
        </dl>
        <div class="card-actions">
          <button @click="openApprove(item, 'APPROVED')">通过</button>
          <button class="danger" @click="openApprove(item, 'REJECTED')">驳回</button>
        </div>
      </article>
      <p v-if="rows.length === 0" class="empty">暂无请假申请</p>
    </section>

    <div v-if="form" class="drawer-mask" @click.self="form = null">
      <form class="drawer" @submit.prevent="submit">
        <h3>{{ form.approveStatus === 'APPROVED' ? '通过请假' : '驳回请假' }}</h3>
        <label>
          审批状态
          <select v-model="form.approveStatus">
            <option value="APPROVED">通过</option>
            <option value="REJECTED">驳回</option>
          </select>
        </label>
        <label>
          审批备注
          <textarea v-model.trim="form.approveRemark" placeholder="补充处理说明" />
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
import { approveLeaveRequest, leaveRequests } from '@/api/teacher'

const rows = ref<Array<Record<string, any>>>([])
const form = ref<Record<string, any> | null>(null)

async function load() {
  rows.value = (await leaveRequests()).data.data
}

function openApprove(row: Record<string, any>, approveStatus: 'APPROVED' | 'REJECTED') {
  form.value = {
    id: row.id,
    approveStatus,
    approveRemark: row.approveRemark || ''
  }
}

async function submit() {
  if (!form.value) return
  await approveLeaveRequest(form.value)
  form.value = null
  await load()
}

onMounted(load)
</script>
