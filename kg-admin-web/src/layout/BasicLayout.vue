<template>
  <div :class="classObj" class="app-wrapper">
    <div class="ambient ambient-left" />
    <div class="ambient ambient-right" />
    <el-container class="layout-shell">
      <el-aside class="sidebar-shell" :width="appStore.sidebar.opened ? '248px' : '76px'">
        <Sidebar />
      </el-aside>
      <el-container class="content-shell">
        <el-header class="header-box">
          <HeaderNavbar />
        </el-header>
        <el-main class="main-box">
          <AppMain />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { HeaderNavbar, Sidebar, AppMain } from './components'
import { useAppStore } from '@/store/app'

const appStore = useAppStore()

const classObj = computed(() => {
  return {
    hideSidebar: !appStore.sidebar.opened,
    openSidebar: appStore.sidebar.opened,
    withoutAnimation: appStore.sidebar.withoutAnimation,
  }
})
</script>

<style lang="scss" scoped>
.app-wrapper {
  position: relative;
  width: 100%;
  height: 100vh;
  padding: 18px;
  overflow: hidden;
}

.ambient {
  position: absolute;
  pointer-events: none;
  filter: blur(4px);
  opacity: 0.78;
}

.ambient-left {
  left: -120px;
  bottom: -160px;
  width: 360px;
  height: 360px;
  border-radius: 50%;
  background: rgba(246, 169, 77, 0.2);
}

.ambient-right {
  top: -170px;
  right: 10%;
  width: 460px;
  height: 460px;
  border-radius: 46% 54% 52% 48%;
  background: rgba(29, 138, 116, 0.16);
}

.layout-shell {
  position: relative;
  z-index: 1;
  height: 100%;
  gap: 18px;
}

.sidebar-shell {
  overflow: hidden;
  border: 1px solid rgba(255, 255, 255, 0.72);
  border-radius: 28px;
  background: linear-gradient(180deg, rgba(20, 71, 62, 0.96), rgba(13, 44, 40, 0.98));
  box-shadow: 0 24px 72px rgba(17, 52, 46, 0.24);
  transition: width 0.28s ease;
}

.content-shell {
  min-width: 0;
  height: 100%;
  gap: 16px;
}

.header-box {
  height: 76px;
  padding: 0;
  border: 1px solid rgba(255, 255, 255, 0.78);
  border-radius: 26px;
  background: rgba(255, 255, 255, 0.72);
  box-shadow: var(--kg-soft-shadow);
  backdrop-filter: blur(22px);
}

.main-box {
  min-height: 0;
  padding: 0;
  overflow: hidden;
  background: transparent;
}

@media (max-width: 900px) {
  .app-wrapper {
    padding: 10px;
  }

  .layout-shell {
    gap: 10px;
  }

  .sidebar-shell {
    width: 76px !important;
  }

  .header-box {
    height: 70px;
    border-radius: 22px;
  }
}
</style>
