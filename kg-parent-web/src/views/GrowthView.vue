<template>
  <main class="content-stack">
    <section class="module-head">
      <div>
        <p>老师记录</p>
        <h2>成长记录</h2>
      </div>
      <button @click="load">刷新</button>
    </section>

    <section class="panel">
      <article v-for="item in rows" :key="String(item.id)" class="list-card">
        <div class="row-title">
          <b>{{ item.title || '成长记录' }}</b>
          <span>{{ item.recordDate || '-' }}</span>
        </div>
        <span>{{ item.studentName || '孩子' }}</span>
        <p>{{ item.content || '暂无内容' }}</p>
      </article>
      <p v-if="rows.length === 0" class="empty">暂无成长记录</p>
    </section>
  </main>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { growthRecords } from '@/api/parent'

const rows = ref<Array<Record<string, any>>>([])

async function load() {
  rows.value = (await growthRecords()).data.data
}

onMounted(load)
</script>
