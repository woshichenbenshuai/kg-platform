<template>
  <main class="content-stack">
    <section class="hero-card">
      <p>今日班级</p>
      <h2>{{ homeData.teacherName || '老师' }}，有 {{ pendingLeaveCount }} 条请假待处理</h2>
      <div class="hero-actions">
        <RouterLink to="/growth">发布成长</RouterLink>
        <RouterLink to="/leave">处理请假</RouterLink>
      </div>
    </section>

    <section class="metric-grid">
      <article class="metric-card">
        <b>{{ homeData.classCount || 0 }}</b>
        <span>我的班级</span>
      </article>
      <article class="metric-card">
        <b>{{ homeData.studentCount || 0 }}</b>
        <span>我的学生</span>
      </article>
    </section>

    <section class="quick-grid">
      <RouterLink to="/classes">班级信息</RouterLink>
      <RouterLink to="/students">学生花名册</RouterLink>
      <RouterLink to="/growth">成长记录</RouterLink>
      <RouterLink to="/info">通知食谱</RouterLink>
    </section>

    <section class="panel">
      <h3>最新通知</h3>
      <article v-for="item in latestNotices" :key="String(item.id)" class="list-card">
        <b>{{ item.title || '未命名通知' }}</b>
        <span>{{ item.publishTime || '未发布' }}</span>
      </article>
      <p v-if="latestNotices.length === 0" class="empty">暂无通知</p>
    </section>

    <section class="panel">
      <h3>最近成长记录</h3>
      <article v-for="item in recentGrowthRecords" :key="String(item.id)" class="list-card">
        <b>{{ item.title || '成长记录' }}</b>
        <span>{{ item.recordDate || '-' }} / 学生 {{ item.studentId || '-' }}</span>
        <p>{{ item.content || '暂无内容' }}</p>
      </article>
      <p v-if="recentGrowthRecords.length === 0" class="empty">暂无成长记录</p>
    </section>
  </main>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { home } from '@/api/teacher'

const homeData = ref<Record<string, any>>({})
const pendingLeaveCount = computed(() => Number(homeData.value.pendingLeaveCount || 0))
const latestNotices = computed(() => (homeData.value.latestNotices || []) as Array<Record<string, any>>)
const recentGrowthRecords = computed(() => (homeData.value.recentGrowthRecords || []) as Array<Record<string, any>>)

onMounted(async () => {
  homeData.value = (await home()).data.data
})
</script>
