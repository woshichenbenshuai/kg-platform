<template>
  <div class="login-view">
    <div class="shape shape-a" />
    <div class="shape shape-b" />

    <section class="login-stage">
      <div class="story-panel">
        <div class="brand-line">
          <span class="brand-mark">KG</span>
          <span>童伴云 Kindergarten OS</span>
        </div>
        <h1>把园所运营、家园沟通和幼儿成长记录放在同一个工作台。</h1>
        <p>面向多园区管理、教师协作、家长服务和日常审批的幼儿园数字化平台。</p>

        <div class="insight-card">
          <span>今日园务概览</span>
          <strong>安全、清晰、可追踪</strong>
          <div class="bars">
            <i />
            <i />
            <i />
          </div>
        </div>
      </div>

      <el-card class="login-card" shadow="never">
        <div class="form-heading">
          <span>欢迎回来</span>
          <h2>登录管理后台</h2>
          <p>使用平台账号进入当前授权园所。</p>
        </div>

        <el-form class="login-form" label-position="top" @keyup.enter="enterSystem">
          <el-form-item label="用户名">
            <el-input
              v-model="form.username"
              :prefix-icon="User"
              autocomplete="username"
              placeholder="请输入用户名"
              size="large"
            />
          </el-form-item>
          <el-form-item label="密码">
            <el-input
              v-model="form.password"
              :prefix-icon="Lock"
              autocomplete="current-password"
              type="password"
              show-password
              placeholder="请输入密码"
              size="large"
            />
          </el-form-item>
          <el-button class="login-button" type="primary" size="large" :loading="submitting" @click="enterSystem">
            进入系统
            <el-icon><Right /></el-icon>
          </el-button>
          <div v-if="errorMessage" class="login-error">{{ errorMessage }}</div>
        </el-form>
      </el-card>
    </section>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { login } from '@/api/auth'
import { useUserStore } from '@/store/user'
import { Lock, Right, User } from '@element-plus/icons-vue'

const router = useRouter()
const userStore = useUserStore()
const submitting = ref(false)
const errorMessage = ref('')
const form = reactive({
  username: '',
  password: ''
})

async function enterSystem() {
  if (submitting.value) return

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

<style lang="scss" scoped>
.login-view {
  position: relative;
  min-height: 100vh;
  display: grid;
  place-items: center;
  overflow: hidden;
  padding: 32px;
  background:
    linear-gradient(120deg, rgba(19, 86, 74, 0.9), rgba(23, 35, 31, 0.94)),
    radial-gradient(circle at 18% 12%, rgba(246, 169, 77, 0.36), transparent 28%);
}

.shape {
  position: absolute;
  border-radius: 999px;
  pointer-events: none;
}

.shape-a {
  width: 520px;
  height: 520px;
  top: -220px;
  right: -120px;
  background: rgba(134, 224, 200, 0.22);
}

.shape-b {
  width: 300px;
  height: 300px;
  left: 8%;
  bottom: -130px;
  background: rgba(246, 169, 77, 0.24);
}

.login-stage {
  position: relative;
  z-index: 1;
  display: grid;
  width: min(1080px, 100%);
  grid-template-columns: minmax(0, 1.08fr) 420px;
  gap: 28px;
  align-items: stretch;
}

.story-panel,
.login-card {
  border: 1px solid rgba(255, 255, 255, 0.22);
  border-radius: 34px;
  backdrop-filter: blur(22px);
}

.story-panel {
  min-height: 600px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 42px;
  color: #f7fffb;
  background:
    radial-gradient(circle at 18% 18%, rgba(248, 216, 137, 0.22), transparent 28%),
    linear-gradient(145deg, rgba(255, 255, 255, 0.18), rgba(255, 255, 255, 0.06));
  box-shadow: 0 34px 90px rgba(0, 0, 0, 0.26);
}

.brand-line {
  display: inline-flex;
  align-items: center;
  gap: 12px;
  color: rgba(247, 255, 251, 0.76);
  font-size: 13px;
  font-weight: 800;
  letter-spacing: 0.08em;
  text-transform: uppercase;
}

.brand-mark {
  width: 44px;
  height: 44px;
  display: grid;
  place-items: center;
  border-radius: 15px;
  color: #123d35;
  background: linear-gradient(135deg, #f8d889, #86e0c8);
  font-weight: 950;
  letter-spacing: -0.04em;
}

.story-panel h1 {
  max-width: 650px;
  margin: 72px 0 18px;
  font-size: clamp(38px, 5vw, 64px);
  line-height: 1.04;
  letter-spacing: -0.055em;
}

.story-panel p {
  max-width: 520px;
  margin: 0;
  color: rgba(247, 255, 251, 0.68);
  font-size: 17px;
  line-height: 1.8;
}

.insight-card {
  width: min(360px, 100%);
  padding: 22px;
  border: 1px solid rgba(255, 255, 255, 0.18);
  border-radius: 26px;
  background: rgba(255, 255, 255, 0.14);
}

.insight-card span {
  color: rgba(247, 255, 251, 0.62);
  font-size: 13px;
}

.insight-card strong {
  display: block;
  margin-top: 8px;
  font-size: 24px;
}

.bars {
  display: grid;
  grid-template-columns: 0.7fr 1fr 0.48fr;
  gap: 8px;
  align-items: end;
  height: 72px;
  margin-top: 18px;
}

.bars i {
  display: block;
  border-radius: 999px 999px 10px 10px;
  background: linear-gradient(180deg, #f8d889, #86e0c8);
}

.bars i:nth-child(1) {
  height: 52%;
}

.bars i:nth-child(2) {
  height: 100%;
}

.bars i:nth-child(3) {
  height: 68%;
}

.login-card {
  align-self: center;
  padding: 18px;
  background: rgba(255, 255, 255, 0.88);
  box-shadow: 0 30px 80px rgba(0, 0, 0, 0.22);
}

.form-heading {
  margin-bottom: 30px;
}

.form-heading span {
  color: var(--kg-primary);
  font-size: 13px;
  font-weight: 850;
  letter-spacing: 0.12em;
}

.form-heading h2 {
  margin: 10px 0 8px;
  color: var(--kg-text);
  font-size: 34px;
  line-height: 1.08;
  letter-spacing: -0.04em;
}

.form-heading p {
  margin: 0;
  color: var(--kg-muted);
}

.login-form {
  :deep(.el-form-item) {
    margin-bottom: 20px;
  }

  :deep(.el-form-item__label) {
    color: #31534a;
    font-weight: 750;
  }
}

.login-button {
  width: 100%;
  height: 48px;
  margin-top: 4px;
  font-size: 16px;
}

.login-error {
  margin-top: 14px;
  padding: 11px 12px;
  border-radius: 14px;
  color: #b42318;
  background: rgba(244, 63, 94, 0.1);
  font-size: 13px;
}

@media (max-width: 900px) {
  .login-view {
    padding: 18px;
  }

  .login-stage {
    grid-template-columns: 1fr;
  }

  .story-panel {
    min-height: auto;
    padding: 28px;
  }

  .story-panel h1 {
    margin-top: 40px;
    font-size: 38px;
  }
}
</style>
