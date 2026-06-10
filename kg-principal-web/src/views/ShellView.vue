<template>
  <div class="phone-shell principal-theme">
    <header class="topbar">
      <div>
        <p>{{ today }}</p>
        <h1>{{ title }}</h1>
      </div>
      <button class="ghost" @click="logout">退出</button>
    </header>

    <section class="tenant-card" v-if="session.user">
      <span>{{ session.user.nickname || session.user.username }}</span>
      <strong>{{ currentTenantName }}</strong>
      <small>当前账号只管理本园数据，不提供园区切换。</small>
    </section>

    <router-view />

    <nav class="tabbar scroll-tabs" aria-label="园所管理导航">
      <RouterLink to="/home">首页</RouterLink>
      <RouterLink to="/module/classes">班级</RouterLink>
      <RouterLink to="/module/teachers">老师</RouterLink>
      <RouterLink to="/module/students">学生</RouterLink>
      <RouterLink to="/module/guardians">家长</RouterLink>
      <RouterLink to="/module/relations">绑定</RouterLink>
      <RouterLink to="/module/notices">通知</RouterLink>
    </nav>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useSessionStore } from '@/stores/session'
import { modules } from '@/api/portal'

const route = useRoute()
const router = useRouter()
const session = useSessionStore()
const today = new Date().toLocaleDateString('zh-CN', { month: 'long', day: 'numeric', weekday: 'long' })

const currentTenantName = computed(() => {
  const tenants = session.user?.tenants || []
  return tenants.find((item) => String(item.tenantId) === String(session.user?.tenantId))?.tenantName || '当前园所'
})

const title = computed(() => {
  if (route.params.moduleKey) return modules[String(route.params.moduleKey)]?.title || '园所管理'
  return '园所管理'
})

function logout() {
  session.clear()
  router.replace('/login')
}
</script>
