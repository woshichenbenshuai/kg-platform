<template>
  <div class="phone-shell principal-theme">
    <header class="topbar">
      <div><p>{{ today }}</p><h1>{{ title }}</h1></div>
      <button class="ghost" @click="logout">退出</button>
    </header>
    <section class="tenant-card" v-if="session.user">
      <span>{{ session.user.nickname || session.user.username }}</span>
      <select :value="session.user.tenantId" @change="onTenantChange">
        <option v-for="tenant in session.user.tenants || []" :key="tenant.tenantId" :value="tenant.tenantId">{{ tenant.tenantName }}</option>
      </select>
    </section>
    <router-view />
    <nav class="tabbar">
      <RouterLink to="/home">首页</RouterLink>
      <RouterLink to="/module/classes">班级</RouterLink>
      <RouterLink to="/module/teachers">教师</RouterLink>
      <RouterLink to="/module/leave">请假</RouterLink>
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
const title = computed(() => route.params.moduleKey ? modules[String(route.params.moduleKey)]?.title || '园长端' : '园长工作台')
async function onTenantChange(event: Event) {
  const tenantId = (event.target as HTMLSelectElement).value
  if (tenantId) await session.changeTenant(tenantId)
}
function logout() { session.clear(); router.replace('/login') }
</script>
