<template>
  <div class="login-view">
    <el-card class="login-card">
      <template #header>登录</template>
      <el-form label-position="top">
        <el-form-item label="用户名">
          <el-input v-model="form.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" type="password" show-password placeholder="请输入密码" />
        </el-form-item>
        <el-button type="primary" :loading="submitting" @click="enterSystem">进入系统</el-button>
        <div v-if="errorMessage" class="login-error">{{ errorMessage }}</div>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { login } from '@/api/auth'
import { useUserStore } from '@/store/user'

const router = useRouter()
const userStore = useUserStore()
const submitting = ref(false)
const errorMessage = ref('')
const form = reactive({
  username: '',
  password: ''
})

async function enterSystem() {
  submitting.value = true
  errorMessage.value = ''
  try {
    const response = await login({
      username: form.username,
      password: form.password
    })
    userStore.setToken(response.data.data.accessToken)
    router.push('/')
  } catch (error) {
    errorMessage.value = '登录失败，请检查用户名和密码'
  } finally {
    submitting.value = false
  }
}
</script>

<style scoped>
.login-view {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
}

.login-card {
  width: 360px;
}

.login-error {
  margin-top: 12px;
  color: #f56c6c;
}
</style>
