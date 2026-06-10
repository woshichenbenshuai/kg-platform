<template>
  <main class="content-stack">
    <section class="module-head">
      <div>
        <p>本班学生</p>
        <h2>学生花名册</h2>
      </div>
      <button @click="load">刷新</button>
    </section>

    <section class="panel search-panel">
      <input v-model.trim="keyword" placeholder="按姓名或学号筛选" />
    </section>

    <section class="panel">
      <article v-for="item in filteredRows" :key="String(item.id)" class="list-card">
        <div class="row-title">
          <b>{{ item.studentName || '未命名学生' }}</b>
          <span>{{ item.studentNo || '-' }}</span>
        </div>
        <dl>
          <dt>班级ID</dt>
          <dd>{{ item.classId || '-' }}</dd>
          <dt>性别</dt>
          <dd>{{ item.gender || '-' }}</dd>
          <dt>生日</dt>
          <dd>{{ item.birthday || '-' }}</dd>
          <dt>状态</dt>
          <dd>{{ Number(item.status) === 1 ? '在读' : '停用' }}</dd>
        </dl>
      </article>
      <p v-if="filteredRows.length === 0" class="empty">暂无学生</p>
    </section>
  </main>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { students } from '@/api/teacher'

const rows = ref<Array<Record<string, any>>>([])
const keyword = ref('')
const filteredRows = computed(() => {
  if (!keyword.value) return rows.value
  return rows.value.filter((item) => JSON.stringify(item).includes(keyword.value))
})

async function load() {
  rows.value = (await students()).data.data
}

onMounted(load)
</script>
