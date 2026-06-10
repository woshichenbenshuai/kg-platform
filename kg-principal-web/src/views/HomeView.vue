<template>
  <main class="content-stack">
    <section class="hero-card">
      <p>园所运营</p>
      <h2>今天重点看 {{ pendingLeaveCount }} 条待处理请假</h2>
      <div class="hero-actions">
        <RouterLink to="/module/leave">处理请假</RouterLink>
        <RouterLink to="/module/notices">发布通知</RouterLink>
      </div>
    </section>
    <section class="metric-grid">
      <article v-for="item in metrics" :key="item.label" class="metric-card"><b>{{ item.value }}</b><span>{{ item.label }}</span></article>
    </section>
    <section class="quick-grid">
      <RouterLink v-for="item in quick" :key="item.key" :to="`/module/${item.key}`">{{ item.title }}</RouterLink>
    </section>
    <section class="panel">
      <h3>最新通知</h3>
      <p v-if="latestNotices.length === 0" class="empty">暂无通知</p>
      <article v-for="notice in latestNotices" :key="String(notice.id)" class="list-card"><b>{{ notice.title }}</b><span>{{ notice.publishTime || '未发布' }}</span></article>
    </section>
  </main>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { principalHome, modules } from '@/api/portal'

const home = ref<Record<string, any>>({})
const latestNotices = computed(() => (home.value.latestNotices || []) as Array<Record<string, unknown>>)
const pendingLeaveCount = computed(() => Number(home.value.pendingLeaveCount || 0))
const metrics = computed(() => [
  { label: '班级', value: home.value.classCount || 0 },
  { label: '教师', value: home.value.teacherCount || 0 },
  { label: '学生', value: home.value.studentCount || 0 },
  { label: '家长', value: home.value.guardianCount || 0 }
])
const quick = Object.values(modules)
onMounted(async () => { home.value = (await principalHome()).data.data })
</script>
