<template>
  <main class="content-stack">
    <section class="hero-card">
      <p>园务与学籍</p>
      <h2>学生建档、家长绑定和班级分配都在园所管理端完成</h2>
      <div class="hero-summary">
        当前维护 {{ classCount }} 个班级、{{ teacherCount }} 名老师、{{ studentCount }} 名学生、{{ guardianCount }} 位家长
      </div>
      <div class="hero-actions">
        <RouterLink to="/module/students">新增学生</RouterLink>
        <RouterLink to="/module/relations">绑定家长</RouterLink>
      </div>
    </section>

    <section class="metric-grid">
      <RouterLink class="metric-card" to="/module/classes"><b>{{ classCount }}</b><span>班级</span></RouterLink>
      <RouterLink class="metric-card" to="/module/teachers"><b>{{ teacherCount }}</b><span>老师</span></RouterLink>
      <RouterLink class="metric-card" to="/module/students"><b>{{ studentCount }}</b><span>学生</span></RouterLink>
      <RouterLink class="metric-card" to="/module/guardians"><b>{{ guardianCount }}</b><span>家长</span></RouterLink>
    </section>

    <section class="panel quick-panel">
      <h3>园所管理入口</h3>
      <div class="quick-grid">
        <RouterLink to="/module/classes">班级与班主任</RouterLink>
        <RouterLink to="/module/teachers">老师档案</RouterLink>
        <RouterLink to="/module/students">学生建档</RouterLink>
        <RouterLink to="/module/guardians">家长档案</RouterLink>
        <RouterLink to="/module/relations">学生家长绑定</RouterLink>
        <RouterLink to="/module/notices">全园通知发布</RouterLink>
      </div>
    </section>

    <section class="panel focus-panel">
      <h3>端边界</h3>
      <p>园所管理端负责官方档案和关系配置；老师端只处理自己班级的学生日常、成长记录和请假；家长端只查看已绑定孩子并提交请假。</p>
    </section>
  </main>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { principalHome } from '@/api/portal'

const home = ref<Record<string, any>>({})
const classCount = computed(() => Number(home.value.classCount || 0))
const teacherCount = computed(() => Number(home.value.teacherCount || 0))
const studentCount = computed(() => Number(home.value.studentCount || 0))
const guardianCount = computed(() => Number(home.value.guardianCount || 0))

onMounted(async () => {
  home.value = (await principalHome()).data.data
})
</script>
