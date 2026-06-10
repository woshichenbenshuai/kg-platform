<template>
  <main class="content-stack">
    <section class="module-head">
      <div>
        <p>绑定信息</p>
        <h2>我的孩子</h2>
      </div>
      <button @click="load">刷新</button>
    </section>

    <section class="panel">
      <article v-for="item in rows" :key="String(item.id)" class="list-card">
        <div class="row-title">
          <b>{{ item.studentName || '未命名孩子' }}</b>
          <span>{{ item.relationType || '监护人' }}</span>
        </div>
        <dl>
          <dt>学号</dt>
          <dd>{{ item.studentNo || '-' }}</dd>
          <dt>班级</dt>
          <dd>{{ item.gradeName || '-' }} {{ item.className || '' }}</dd>
          <dt>性别</dt>
          <dd>{{ item.gender || '-' }}</dd>
          <dt>生日</dt>
          <dd>{{ item.birthday || '-' }}</dd>
        </dl>
      </article>
      <p v-if="rows.length === 0" class="empty">暂无绑定孩子</p>
    </section>
  </main>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { children } from '@/api/parent'

const rows = ref<Array<Record<string, any>>>([])

async function load() {
  rows.value = (await children()).data.data
}

onMounted(load)
</script>
