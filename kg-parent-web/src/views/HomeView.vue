<template>
  <main class="content-stack">
    <section class="hero-card">
      <p>家校连接</p>
      <h2>{{ data.parentName || '家长' }}，已绑定 {{ data.childCount || 0 }} 个孩子</h2>
      <div class="hero-actions">
        <RouterLink to="/notices">看通知</RouterLink>
        <RouterLink to="/leave">提交请假</RouterLink>
      </div>
    </section>

    <section class="quick-grid">
      <RouterLink to="/children">孩子信息</RouterLink>
      <RouterLink to="/notices">园所通知</RouterLink>
      <RouterLink to="/recipes">每日食谱</RouterLink>
      <RouterLink to="/growth">成长记录</RouterLink>
    </section>

    <section class="panel">
      <h3>孩子</h3>
      <article v-for="child in childrenRows" :key="String(child.id)" class="list-card">
        <b>{{ child.studentName || '未命名孩子' }}</b>
        <span>{{ child.gradeName || '-' }} {{ child.className || '' }}</span>
      </article>
      <p v-if="childrenRows.length === 0" class="empty">暂无绑定孩子</p>
    </section>

    <section class="panel">
      <h3>今日食谱</h3>
      <article v-for="item in todayRecipes" :key="String(item.id)" class="list-card">
        <b>{{ item.mealType || '餐次' }}</b>
        <span>{{ item.content || '暂无内容' }}</span>
      </article>
      <p v-if="todayRecipes.length === 0" class="empty">今日暂无食谱</p>
    </section>
  </main>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { home } from '@/api/parent'

const data = ref<Record<string, any>>({})
const childrenRows = computed(() => (data.value.children || []) as Array<Record<string, any>>)
const todayRecipes = computed(() => (data.value.todayRecipes || []) as Array<Record<string, any>>)

onMounted(async () => {
  data.value = (await home()).data.data
})
</script>
