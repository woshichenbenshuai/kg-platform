import { http, type ApiResponse } from './http'

export interface ModuleConfig {
  key: string
  title: string
  listUrl: string
  createUrl?: string
  updateUrl?: string
  deleteUrl?: string
  approveUrl?: string
  idParam?: string
  fields: Array<{ key: string; label: string; type?: 'text' | 'date' | 'textarea' | 'number'; default?: unknown }>
  columns: Array<{ key: string; label: string }>
}

export const modules: Record<string, ModuleConfig> = {
  classes: {
    key: 'classes', title: '班级管理', listUrl: '/kinder/kindergarten-classes/pages', createUrl: '/kinder/kindergarten-classes', updateUrl: '/kinder/kindergarten-classes', deleteUrl: '/kinder/kindergarten-classes', idParam: 'id',
    fields: [{ key: 'classCode', label: '班级编码' }, { key: 'className', label: '班级名称' }, { key: 'gradeName', label: '年级' }, { key: 'headTeacherId', label: '班主任ID', type: 'number' }, { key: 'status', label: '状态', type: 'number', default: 1 }],
    columns: [{ key: 'className', label: '班级' }, { key: 'gradeName', label: '年级' }, { key: 'headTeacherId', label: '班主任' }, { key: 'status', label: '状态' }]
  },
  teachers: {
    key: 'teachers', title: '教师管理', listUrl: '/kinder/teachers/pages', createUrl: '/kinder/teachers', updateUrl: '/kinder/teachers', deleteUrl: '/kinder/teachers', idParam: 'id',
    fields: [{ key: 'teacherNo', label: '教师编号' }, { key: 'teacherName', label: '教师姓名' }, { key: 'phone', label: '手机号' }, { key: 'gender', label: '性别' }, { key: 'status', label: '状态', type: 'number', default: 1 }],
    columns: [{ key: 'teacherNo', label: '编号' }, { key: 'teacherName', label: '姓名' }, { key: 'phone', label: '手机' }, { key: 'status', label: '状态' }]
  },
  students: {
    key: 'students', title: '学生管理', listUrl: '/kinder/students/pages', createUrl: '/kinder/students', updateUrl: '/kinder/students', deleteUrl: '/kinder/students', idParam: 'id',
    fields: [{ key: 'studentNo', label: '学号' }, { key: 'studentName', label: '姓名' }, { key: 'classId', label: '班级ID', type: 'number' }, { key: 'gender', label: '性别' }, { key: 'birthday', label: '生日', type: 'date' }, { key: 'status', label: '状态', type: 'number', default: 1 }],
    columns: [{ key: 'studentNo', label: '学号' }, { key: 'studentName', label: '姓名' }, { key: 'classId', label: '班级' }, { key: 'status', label: '状态' }]
  },
  guardians: {
    key: 'guardians', title: '家长管理', listUrl: '/kinder/guardians/pages', createUrl: '/kinder/guardians', updateUrl: '/kinder/guardians', deleteUrl: '/kinder/guardians', idParam: 'id',
    fields: [{ key: 'guardianName', label: '家长姓名' }, { key: 'phone', label: '手机号' }, { key: 'status', label: '状态', type: 'number', default: 1 }],
    columns: [{ key: 'guardianName', label: '姓名' }, { key: 'phone', label: '手机' }, { key: 'status', label: '状态' }]
  },
  notices: {
    key: 'notices', title: '通知管理', listUrl: '/kinder/notices/pages', createUrl: '/kinder/notices', updateUrl: '/kinder/notices', deleteUrl: '/kinder/notices', idParam: 'id',
    fields: [{ key: 'title', label: '标题' }, { key: 'content', label: '内容', type: 'textarea' }, { key: 'status', label: '状态', type: 'number', default: 1 }],
    columns: [{ key: 'title', label: '标题' }, { key: 'publishTime', label: '发布时间' }, { key: 'status', label: '状态' }]
  },
  recipes: {
    key: 'recipes', title: '食谱管理', listUrl: '/kinder/recipes/pages', createUrl: '/kinder/recipes', updateUrl: '/kinder/recipes', deleteUrl: '/kinder/recipes', idParam: 'id',
    fields: [{ key: 'recipeDate', label: '日期', type: 'date' }, { key: 'mealType', label: '餐次' }, { key: 'content', label: '内容', type: 'textarea' }, { key: 'status', label: '状态', type: 'number', default: 1 }],
    columns: [{ key: 'recipeDate', label: '日期' }, { key: 'mealType', label: '餐次' }, { key: 'content', label: '内容' }]
  },
  growth: {
    key: 'growth', title: '成长记录', listUrl: '/kinder/growth-records/pages', createUrl: '/kinder/growth-records', updateUrl: '/kinder/growth-records', deleteUrl: '/kinder/growth-records', idParam: 'id',
    fields: [{ key: 'studentId', label: '学生ID', type: 'number' }, { key: 'title', label: '标题' }, { key: 'content', label: '内容', type: 'textarea' }, { key: 'recordDate', label: '日期', type: 'date' }, { key: 'visibleToParent', label: '家长可见', type: 'number', default: 1 }, { key: 'status', label: '状态', type: 'number', default: 1 }],
    columns: [{ key: 'studentId', label: '学生' }, { key: 'title', label: '标题' }, { key: 'recordDate', label: '日期' }, { key: 'visibleToParent', label: '可见' }]
  },
  leave: {
    key: 'leave', title: '请假审批', listUrl: '/kinder/leave-requests/pages', approveUrl: '/kinder/leave-requests/approve',
    fields: [{ key: 'approveStatus', label: '审批状态' }, { key: 'approveRemark', label: '审批备注', type: 'textarea' }],
    columns: [{ key: 'studentId', label: '学生' }, { key: 'startDate', label: '开始' }, { key: 'endDate', label: '结束' }, { key: 'approveStatus', label: '状态' }]
  }
}

export function principalHome() { return http.get<ApiResponse<Record<string, unknown>>>('/kinder/principal-portal/home') }
export function listModule(config: ModuleConfig, params: Record<string, unknown>) { return http.get<ApiResponse<{ records: Record<string, unknown>[]; total: number }>>(config.listUrl, { params }) }
export function createItem(config: ModuleConfig, data: Record<string, unknown>) { return http.post(config.createUrl || config.listUrl, data) }
export function updateItem(config: ModuleConfig, data: Record<string, unknown>) { return http.put(config.updateUrl || config.listUrl, data) }
export function deleteItem(config: ModuleConfig, id: unknown) { return http.delete(config.deleteUrl || config.listUrl, { params: { [config.idParam || 'id']: id } }) }
export function approveLeave(data: Record<string, unknown>) { return http.put('/kinder/leave-requests/approve', data) }
