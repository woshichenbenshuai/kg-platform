<template>
  <div class="phone-shell parent-theme">
    <header class="topbar">
      <div>
        <p>{{ dateText }}</p>
        <h1>{{ title }}</h1>
      </div>
      <button class="ghost" @click="logout">退出</button>
    </header>

    <section v-if="session.user" class="tenant-card">
      <span>{{ session.user.nickname || session.user.username }}</span>
      <select :value="session.user.tenantId" @change="changeTenant">
        <option v-for="tenant in session.user.tenants || []" :key="tenant.tenantId" :value="tenant.tenantId">
          {{ tenant.tenantName }}
        </option>
      </select>
    </section>

    <router-view />

    <nav class="tabbar">
      <RouterLink to="/home">首页</RouterLink>
      <RouterLink to="/children">孩子</RouterLink>
      <RouterLink to="/growth">成长</RouterLink>
      <RouterLink to="/leave">请假</RouterLink>
    </nav>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useSessionStore } from '@/stores/session'

const route = useRoute()
const router = useRouter()
const session = useSessionStore()
const dateText = new Date().toLocaleDateString('zh-CN', { month: 'long', day: 'numeric', weekday: 'long' })
const titles: Record<string, string> = {
  '/home': '家长首页',
  '/children': '我的孩子',
  '/notices': '园所通知',
  '/recipes': '每日食谱',
  '/growth': '成长记录',
  '/leave': '请假申请'
}
const title = computed(() => titles[route.path] || '家长端')

async function changeTenant(event: Event) {
  const tenantId = (event.target as HTMLSelectElement).value
  if (tenantId) await session.changeTenant(tenantId)
}

function logout() {
  session.clear()
  router.replace('/login')
}
</script>
