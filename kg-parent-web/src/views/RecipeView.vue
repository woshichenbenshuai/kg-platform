<template>
  <main class="content-stack">
    <section class="module-head">
      <div>
        <p>一日餐食</p>
        <h2>每日食谱</h2>
      </div>
      <button @click="load">查询</button>
    </section>

    <section class="panel search-panel">
      <input v-model="date" type="date" />
    </section>

    <section class="panel">
      <article v-for="item in rows" :key="String(item.id)" class="list-card">
        <div class="row-title">
          <b>{{ item.mealType || '餐次' }}</b>
          <span>{{ item.recipeDate || date }}</span>
        </div>
        <p>{{ item.content || '暂无内容' }}</p>
      </article>
      <p v-if="rows.length === 0" class="empty">暂无食谱</p>
    </section>
  </main>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { recipes } from '@/api/parent'

const date = ref(new Date().toISOString().slice(0, 10))
const rows = ref<Array<Record<string, any>>>([])

async function load() {
  rows.value = (await recipes(date.value)).data.data
}

onMounted(load)
</script>
