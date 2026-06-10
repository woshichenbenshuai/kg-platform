<template>
  <main class="content-stack">
    <section class="module-head">
      <div>
        <p>当前孩子</p>
        <h2>{{ currentChild?.studentName || '我的孩子' }}</h2>
      </div>
      <button @click="load">刷新</button>
    </section>

    <section class="panel">
      <article v-if="currentChild" class="list-card current-child-detail-card">
        <div class="row-title">
          <b>{{ currentChild.studentName || '未命名孩子' }}</b>
          <span>{{ currentChild.relationType || '监护人' }}</span>
        </div>
        <dl>
          <dt>学号</dt>
          <dd>{{ currentChild.studentNo || '-' }}</dd>
          <dt>班级</dt>
          <dd>{{ currentChild.gradeName || '-' }} {{ currentChild.className || '' }}</dd>
          <dt>性别</dt>
          <dd>{{ currentChild.gender || '-' }}</dd>
          <dt>生日</dt>
          <dd>{{ currentChild.birthday || '-' }}</dd>
        </dl>
      </article>
      <p v-else class="empty">暂无绑定孩子</p>
    </section>
  </main>
</template>

<script setup lang="ts">
import { computed, onMounted } from 'vue'
import { useChildStore } from '@/stores/child'

const childStore = useChildStore()
const currentChild = computed(() => childStore.currentChild)

async function load() {
  await childStore.loadChildren()
}

onMounted(load)
</script>
