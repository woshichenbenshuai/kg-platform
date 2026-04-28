<template>
  <div :class="classObj" class="app-wrapper">
    <el-container class="main-container-box">
      <el-header class="header-box">
        <HeaderNavbar class="header-navbar-container" />
      </el-header>
      <el-container>
        <el-aside :width="appStore.sidebar.opened ? '149px' : '54px'">
          <Sidebar class="sidebar-container" />
        </el-aside>
        <el-main>
          <AppMain class="app-main-box" />
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
  height: 100vh;
  width: 100%;
}
.main-container-box {
  height: 100%;
}
.header-box {
  height: 60px;
  background-color: #0F243C;
  background-image: url('@/assets/images/header-bg.png');
  background-size: cover;
  background-repeat: no-repeat;
  background-position: left;
  padding: 0;
}
.el-main {
  padding: 0;
  background-color: #F6F9FD;
}
.el-aside {
  background-color: #304156;
  transition: width 0.28s;
  overflow: hidden;
}
</style>
