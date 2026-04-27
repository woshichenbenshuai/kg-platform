<template>
  <div class="layout">
    <aside class="layout__aside">
      <div class="layout__logo">kg-admin-web</div>
      <ul class="layout__menu">
        <li v-for="menu in permissionStore.menus" :key="menu.id">
          {{ menu.menuName }}
        </li>
      </ul>
    </aside>
    <main class="layout__main">
      <RouterView />
    </main>
  </div>
</template>

<script setup lang="ts">
import { usePermissionStore } from '@/store/permission'

const permissionStore = usePermissionStore()
</script>

<style scoped>
.layout {
  display: flex;
  min-height: 100vh;
}

.layout__aside {
  width: 240px;
  background: #001529;
  color: #ffffff;
  padding: 20px;
}

.layout__logo {
  margin-bottom: 20px;
  font-size: 18px;
  font-weight: 600;
}

.layout__menu {
  margin: 0;
  padding: 0;
  list-style: none;
}

.layout__menu li {
  margin-bottom: 12px;
}

.layout__main {
  flex: 1;
  padding: 24px;
}
</style>
