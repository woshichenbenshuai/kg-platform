<template>
  <main class="content-stack">
    <section class="module-head">
      <div>
        <p>园所同步</p>
        <h2>园所通知</h2>
      </div>
      <button @click="load">刷新</button>
    </section>

    <section class="panel">
      <article v-for="item in rows" :key="String(item.id)" class="list-card">
        <b>{{ item.title || '通知' }}</b>
        <span>{{ item.publishTime || '未发布' }}</span>
        <p>{{ item.content || '暂无内容' }}</p>
      </article>
      <p v-if="rows.length === 0" class="empty">暂无通知</p>
    </section>
  </main>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { notices } from '@/api/parent'

const rows = ref<Array<Record<string, any>>>([])

async function load() {
  rows.value = (await notices()).data.data
}

onMounted(load)
</script>
