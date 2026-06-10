<template>
  <main class="content-stack">
    <section class="hero-card">
      <p>家校连接</p>
      <h2>{{ data.parentName || '家长' }}，已绑定 {{ data.childCount || 0 }} 个孩子</h2>
      <div class="hero-summary" v-if="currentChild">
        当前查看：{{ currentChild.studentName || '未命名孩子' }}
      </div>
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

    <section class="panel current-child-panel">
      <h3>当前孩子</h3>
      <article v-if="currentChild" class="list-card current-child-card">
        <b>{{ currentChild.studentName || '未命名孩子' }}</b>
        <span>{{ currentChild.gradeName || '-' }} {{ currentChild.className || '' }}</span>
        <dl>
          <dt>学号</dt>
          <dd>{{ currentChild.studentNo || '-' }}</dd>
          <dt>关系</dt>
          <dd>{{ currentChild.relationType || '监护人' }}</dd>
        </dl>
      </article>
      <p v-else class="empty">暂无绑定孩子</p>
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
import { useChildStore } from '@/stores/child'

const childStore = useChildStore()
const data = ref<Record<string, any>>({})
const currentChild = computed(() => childStore.currentChild)
const todayRecipes = computed(() => (data.value.todayRecipes || []) as Array<Record<string, any>>)

onMounted(async () => {
  data.value = (await home()).data.data
  if (childStore.children.length === 0) await childStore.loadChildren()
})
</script>
