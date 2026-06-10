<template>
  <main class="login-page">
    <section class="login-card">
      <p class="eyebrow">Kinder Garden</p>
      <h1>园长端</h1>
      <p class="sub">面向园所负责人的移动 Web 工作台，后续可迁移小程序。</p>
      <form @submit.prevent="submit">
        <label>账号<input v-model="form.username" autocomplete="username" placeholder="请输入账号" /></label>
        <label>密码<input v-model="form.password" autocomplete="current-password" type="password" placeholder="请输入密码" /></label>
        <button :disabled="loading">{{ loading ? '登录中...' : '进入园所' }}</button>
      </form>
      <p class="error" v-if="error">{{ error }}</p>
    </section>
  </main>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { login } from '@/api/auth'
import { useSessionStore } from '@/stores/session'

const router = useRouter()
const session = useSessionStore()
const loading = ref(false)
const error = ref('')
const form = reactive({ username: '', password: '' })

async function submit() {
  error.value = ''
  loading.value = true
  try {
    const res = await login(form.username, form.password)
    session.setToken(res.data.data.accessToken)
    await session.loadUser()
    await router.replace('/home')
  } catch (err) {
    error.value = err instanceof Error ? err.message : '登录失败'
  } finally {
    loading.value = false
  }
}
</script>
