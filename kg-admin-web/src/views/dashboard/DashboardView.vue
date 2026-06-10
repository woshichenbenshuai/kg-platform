<template>
  <div class="dashboard-view">
    <section class="hero-card">
      <div>
        <span class="eyebrow">KG PLATFORM</span>
        <h1>{{ greeting }}，{{ userStore.username || '管理员' }}</h1>
        <p>这里是平台超级管理员的管理工作台，负责幼儿园租户配置、账号权限、菜单字典等管理端能力。</p>
      </div>
      <div class="platform-badge">
        <span>当前身份</span>
        <strong>{{ primaryRole }}</strong>
      </div>
    </section>

    <section class="metric-grid">
      <article v-for="item in metrics" :key="item.label" class="metric-card">
        <span>{{ item.label }}</span>
        <strong>{{ item.value }}</strong>
        <em>{{ item.hint }}</em>
      </article>
    </section>

    <section class="content-grid">
      <el-card class="quick-card" shadow="never">
        <template #header>
          <div class="section-title">
            <span>管理入口</span>
            <small>平台配置、账号权限和基础数据</small>
          </div>
        </template>
        <div class="quick-list">
          <button
            v-for="action in quickActions"
            :key="action.path"
            class="quick-action"
            type="button"
            @click="router.push(action.path)"
          >
            <span class="action-icon">
              <el-icon><component :is="action.icon" /></el-icon>
            </span>
            <span>
              <strong>{{ action.title }}</strong>
              <em>{{ action.desc }}</em>
            </span>
          </button>
        </div>
      </el-card>

      <el-card class="profile-card" shadow="never">
        <template #header>
          <div class="section-title">
            <span>账号状态</span>
            <small>来自当前登录上下文</small>
          </div>
        </template>
        <div class="profile-line">
          <span>用户</span>
          <strong>{{ userStore.username || '-' }}</strong>
        </div>
        <div class="profile-line">
          <span>租户上下文</span>
          <strong>{{ permissionStore.tenantId ?? '平台级' }}</strong>
        </div>
        <div class="role-tags">
          <el-tag v-for="role in visibleRoles" :key="role" effect="plain" round>{{ role }}</el-tag>
          <el-tag v-if="visibleRoles.length === 0" effect="plain" round>暂无角色</el-tag>
        </div>
      </el-card>
    </section>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'
import { usePermissionStore } from '@/store/permission'
import { Lock, Menu, Notebook, OfficeBuilding, User } from '@element-plus/icons-vue'

const router = useRouter()
const userStore = useUserStore()
const permissionStore = usePermissionStore()

const visibleRoles = computed(() => permissionStore.roleNames.length > 0 ? permissionStore.roleNames : permissionStore.roleCodes)
const primaryRole = computed(() => visibleRoles.value[0] || '平台管理员')
const platformMenuCount = computed(() => permissionStore.menus.filter(menu => menu.menuScope === 'PLATFORM').length)

const greeting = computed(() => {
  const hour = new Date().getHours()
  if (hour < 6) return '夜深了'
  if (hour < 12) return '早上好'
  if (hour < 18) return '下午好'
  return '晚上好'
})

const metrics = computed(() => [
  {
    label: '平台菜单',
    value: platformMenuCount.value,
    hint: '平台管理员可见的管理端入口'
  },
  {
    label: '租户上下文',
    value: permissionStore.tenantId ?? '无',
    hint: '超级管理员保持平台级上下文，不切入单个园所'
  },
  {
    label: '角色身份',
    value: visibleRoles.value.length,
    hint: visibleRoles.value.join(' / ') || '暂无角色'
  }
])

const quickActions = [
  {
    title: '幼儿园配置',
    desc: '开通幼儿园、生成租户库并创建园所管理员',
    path: '/platform/tenants',
    icon: OfficeBuilding
  },
  {
    title: '菜单管理',
    desc: '维护管理端菜单、路由和展示顺序',
    path: '/platform/menu',
    icon: Menu
  },
  {
    title: '用户管理',
    desc: '维护平台账号和管理端用户状态',
    path: '/system/users',
    icon: User
  },
  {
    title: '角色权限',
    desc: '配置角色可访问的管理端菜单',
    path: '/system/role-menus',
    icon: Lock
  },
  {
    title: '字典管理',
    desc: '维护平台基础字典和字典值',
    path: '/system/dicts',
    icon: Notebook
  }
]
</script>

<style lang="scss" scoped>
.dashboard-view {
  display: flex;
  flex-direction: column;
  gap: 18px;
}

.hero-card {
  position: relative;
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
  gap: 24px;
  min-height: 230px;
  overflow: hidden;
  padding: 34px;
  border: 1px solid rgba(255, 255, 255, 0.72);
  border-radius: 30px;
  color: #f8fffb;
  background:
    radial-gradient(circle at 12% 16%, rgba(248, 216, 137, 0.28), transparent 28%),
    linear-gradient(135deg, #13564a, #17322d 62%, #102622);
  box-shadow: var(--kg-shadow);
}

.hero-card::after {
  position: absolute;
  right: -80px;
  top: -120px;
  width: 340px;
  height: 340px;
  content: "";
  border-radius: 42% 58% 56% 44%;
  background: rgba(134, 224, 200, 0.2);
}

.eyebrow {
  display: inline-flex;
  margin-bottom: 18px;
  color: rgba(248, 255, 251, 0.58);
  font-size: 12px;
  font-weight: 900;
  letter-spacing: 0.18em;
}

.hero-card h1 {
  position: relative;
  z-index: 1;
  max-width: 720px;
  margin: 0;
  font-size: clamp(34px, 5vw, 58px);
  line-height: 1.02;
  letter-spacing: -0.055em;
}

.hero-card p {
  position: relative;
  z-index: 1;
  max-width: 620px;
  margin: 18px 0 0;
  color: rgba(248, 255, 251, 0.68);
  font-size: 16px;
  line-height: 1.8;
}

.platform-badge {
  position: relative;
  z-index: 1;
  min-width: 210px;
  padding: 18px;
  border: 1px solid rgba(255, 255, 255, 0.18);
  border-radius: 24px;
  background: rgba(255, 255, 255, 0.12);
  backdrop-filter: blur(18px);
}

.platform-badge span,
.metric-card span,
.section-title small,
.profile-line span,
.quick-action em {
  color: var(--kg-muted);
}

.platform-badge span {
  color: rgba(248, 255, 251, 0.6);
  font-size: 12px;
}

.platform-badge strong {
  display: block;
  margin-top: 8px;
  font-size: 20px;
}

.metric-grid {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 16px;
}

.metric-card {
  padding: 22px;
  border: 1px solid var(--kg-border);
  border-radius: 24px;
  background: rgba(255, 255, 255, 0.76);
  box-shadow: var(--kg-soft-shadow);
  backdrop-filter: blur(16px);
}

.metric-card span,
.metric-card em {
  display: block;
  font-style: normal;
}

.metric-card strong {
  display: block;
  margin: 12px 0 8px;
  color: var(--kg-text);
  font-size: 34px;
  line-height: 1;
}

.metric-card em {
  overflow: hidden;
  font-size: 13px;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.content-grid {
  display: grid;
  grid-template-columns: minmax(0, 1.55fr) minmax(280px, 0.8fr);
  gap: 18px;
}

.section-title {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.section-title span {
  color: var(--kg-text);
  font-size: 17px;
  font-weight: 850;
}

.section-title small {
  font-size: 12px;
}

.quick-list {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 14px;
}

.quick-action {
  display: flex;
  align-items: center;
  gap: 14px;
  min-height: 92px;
  padding: 16px;
  border: 1px solid rgba(34, 67, 57, 0.09);
  border-radius: 22px;
  text-align: left;
  background: linear-gradient(135deg, rgba(255, 255, 255, 0.86), rgba(244, 250, 246, 0.78));
  cursor: pointer;
  transition: transform 0.2s ease, box-shadow 0.2s ease, border-color 0.2s ease;
}

.quick-action:hover {
  border-color: rgba(29, 138, 116, 0.28);
  box-shadow: 0 16px 34px rgba(32, 72, 61, 0.12);
  transform: translateY(-2px);
}

.action-icon {
  width: 46px;
  height: 46px;
  display: grid;
  flex: 0 0 46px;
  place-items: center;
  border-radius: 16px;
  color: #123d35;
  background: linear-gradient(135deg, #f8d889, #86e0c8);
  font-size: 20px;
}

.quick-action strong,
.quick-action em {
  display: block;
}

.quick-action strong {
  color: var(--kg-text);
  font-size: 15px;
  line-height: 1.3;
}

.quick-action em {
  margin-top: 6px;
  font-size: 12px;
  font-style: normal;
  line-height: 1.5;
}

.profile-line {
  display: flex;
  justify-content: space-between;
  gap: 18px;
  padding: 14px 0;
  border-bottom: 1px solid rgba(34, 67, 57, 0.08);
}

.profile-line strong {
  color: var(--kg-text);
}

.role-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-top: 18px;
}

@media (max-width: 1024px) {
  .hero-card {
    align-items: flex-start;
    flex-direction: column;
  }

  .metric-grid,
  .content-grid {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 640px) {
  .hero-card {
    padding: 24px;
  }

  .quick-list {
    grid-template-columns: 1fr;
  }
}
</style>
