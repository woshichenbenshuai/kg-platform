<template>
  <main class="content-stack">
    <section class="module-head">
      <div>
        <p>班主任视角</p>
        <h2>我的班级</h2>
      </div>
      <button @click="load">刷新</button>
    </section>

    <section class="panel">
      <article v-for="item in rows" :key="String(item.id)" class="list-card">
        <div class="row-title">
          <b>{{ item.className || '未命名班级' }}</b>
          <span>{{ statusText(item.status) }}</span>
        </div>
        <dl>
          <dt>班级编码</dt>
          <dd>{{ item.classCode || '-' }}</dd>
          <dt>年级</dt>
          <dd>{{ item.gradeName || '-' }}</dd>
          <dt>班主任ID</dt>
          <dd>{{ item.headTeacherId || '-' }}</dd>
        </dl>
      </article>
      <p v-if="rows.length === 0" class="empty">暂无负责班级</p>
    </section>
  </main>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { classes } from '@/api/teacher'

const rows = ref<Array<Record<string, any>>>([])

function statusText(status: unknown) {
  return Number(status) === 1 ? '启用' : '停用'
}

async function load() {
  rows.value = (await classes()).data.data
}

onMounted(load)
</script>
