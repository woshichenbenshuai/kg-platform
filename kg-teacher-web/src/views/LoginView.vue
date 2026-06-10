<template>
  <main class="login-page">
    <section class="login-card">
      <p class="eyebrow">Teacher Portal</p>
      <h1>老师端</h1>
      <p class="sub">面向一线老师的移动网页工作台，覆盖班级、学生、成长记录和请假处理。</p>

      <form @submit.prevent="submit">
        <label>
          账号
          <input v-model.trim="form.username" autocomplete="username" placeholder="请输入老师账号" />
        </label>
        <label>
          密码
          <input v-model="form.password" autocomplete="current-password" type="password" placeholder="请输入密码" />
        </label>
        <button :disabled="loading">{{ loading ? '登录中...' : '进入老师端' }}</button>
      </form>

      <p v-if="error" class="error">{{ error }}</p>
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
  loading.value = true
  error.value = ''
  try {
    const response = await login(form.username, form.password)
    session.setToken(response.data.data.accessToken)
    await session.loadUser()
    await router.replace('/home')
  } catch (err) {
    error.value = err instanceof Error ? err.message : '登录失败'
  } finally {
    loading.value = false
  }
}
</script>
