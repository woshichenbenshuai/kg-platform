<template>
  <div class="phone-shell parent-theme">
    <header class="topbar">
      <div>
        <p>{{ dateText }}</p>
        <h1>{{ title }}</h1>
      </div>
      <button class="ghost" @click="logout">退出</button>
    </header>

    <section v-if="session.user" class="tenant-card child-switch-card">
      <div class="parent-line">
        <span>{{ session.user.nickname || session.user.username }}</span>
        <strong>{{ currentTenantName }}</strong>
      </div>
      <label>
        当前孩子
        <select :value="childStore.currentChildId" @change="changeChild">
          <option v-for="child in childStore.children" :key="String(child.id)" :value="child.id">
            {{ child.studentName || '未命名孩子' }}（{{ child.studentNo || '-' }}）
          </option>
        </select>
      </label>
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
import { computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useChildStore } from '@/stores/child'
import { useSessionStore } from '@/stores/session'

const route = useRoute()
const router = useRouter()
const session = useSessionStore()
const childStore = useChildStore()
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
const currentTenantName = computed(() => {
  const tenants = session.user?.tenants || []
  return tenants.find((item) => String(item.tenantId) === String(session.user?.tenantId))?.tenantName || '当前园所'
})

function changeChild(event: Event) {
  childStore.setCurrentChild((event.target as HTMLSelectElement).value)
}

function logout() {
  session.clear()
  router.replace('/login')
}

onMounted(() => childStore.loadChildren())
</script>
