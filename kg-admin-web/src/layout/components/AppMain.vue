<template>
  <section class="app-main">
    <router-view v-slot="{ Component, route }">
      <transition name="fade-transform" mode="out-in">
        <component :is="Component" :key="route.path" />
      </transition>
    </router-view>
  </section>
</template>

<script setup lang="ts">
</script>

<style lang="scss" scoped>
.app-main {
  height: 100%;
  min-height: 0;
  width: 100%;
  position: relative;
  overflow: auto;
  padding: 2px 2px 10px;
  background: transparent;
}
</style>
