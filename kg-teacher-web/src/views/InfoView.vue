<template>
  <main class="content-stack">
    <section class="module-head">
      <div>
        <p>园所信息</p>
        <h2>通知与食谱</h2>
      </div>
      <button @click="loadAll">刷新</button>
    </section>

    <section class="panel search-panel">
      <input v-model="date" type="date" />
      <button @click="loadRecipes">查食谱</button>
    </section>

    <section class="panel">
      <h3>园所通知</h3>
      <article v-for="item in noticeRows" :key="String(item.id)" class="list-card">
        <b>{{ item.title || '通知' }}</b>
        <span>{{ item.publishTime || '未发布' }}</span>
        <p>{{ item.content || '暂无内容' }}</p>
      </article>
      <p v-if="noticeRows.length === 0" class="empty">暂无通知</p>
    </section>

    <section class="panel">
      <h3>每日食谱</h3>
      <article v-for="item in recipeRows" :key="String(item.id)" class="list-card">
        <b>{{ item.mealType || '餐次' }}</b>
        <span>{{ item.recipeDate || date }}</span>
        <p>{{ item.content || '暂无内容' }}</p>
      </article>
      <p v-if="recipeRows.length === 0" class="empty">暂无食谱</p>
    </section>
  </main>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { notices, recipes } from '@/api/teacher'

const date = ref(new Date().toISOString().slice(0, 10))
const noticeRows = ref<Array<Record<string, any>>>([])
const recipeRows = ref<Array<Record<string, any>>>([])

async function loadNotices() {
  noticeRows.value = (await notices()).data.data
}

async function loadRecipes() {
  recipeRows.value = (await recipes(date.value)).data.data
}

async function loadAll() {
  await Promise.all([loadNotices(), loadRecipes()])
}

onMounted(loadAll)
</script>
