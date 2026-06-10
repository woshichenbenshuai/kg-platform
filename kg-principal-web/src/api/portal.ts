import { http, type ApiResponse } from './http'

export type FieldType = 'text' | 'date' | 'textarea' | 'number' | 'select'
export type FieldValueType = 'string' | 'number' | 'boolean'
export type OptionSource = 'teachers' | 'classes' | 'students' | 'guardians'

export interface FieldOption {
  label: string
  value: string | number | boolean
}

export interface ModuleField {
  key: string
  label: string
  type?: FieldType
  default?: unknown
  placeholder?: string
  valueType?: FieldValueType
  options?: FieldOption[]
  source?: OptionSource
  required?: boolean
}

export interface ModuleConfig {
  key: string
  title: string
  scopeLabel: string
  description: string
  emptyText: string
  searchPlaceholder: string
  deleteConfirmText: string
  listUrl: string
  createUrl?: string
  updateUrl?: string
  deleteUrl?: string
  idParam?: string
  keywordFields: string[]
  fields: ModuleField[]
  columns: Array<{ key: string; label: string }>
}

interface OptionSourceConfig {
  url: string
  params: Record<string, unknown>
  emptyLabel: string
  labelKeys: string[]
}

type ModuleListPayload = { records?: Record<string, unknown>[]; total?: number } | Record<string, unknown>[]

const enabledStatusOptions: FieldOption[] = [
  { label: '启用', value: 1 },
  { label: '停用', value: 0 }
]

const genderOptions: FieldOption[] = [
  { label: '男', value: '男' },
  { label: '女', value: '女' }
]

const relationTypeOptions: FieldOption[] = [
  { label: '爸爸', value: '爸爸' },
  { label: '妈妈', value: '妈妈' },
  { label: '爷爷', value: '爷爷' },
  { label: '奶奶', value: '奶奶' },
  { label: '外公', value: '外公' },
  { label: '外婆', value: '外婆' },
  { label: '其他', value: '其他' }
]

export const optionSources: Record<OptionSource, OptionSourceConfig> = {
  teachers: {
    url: '/kinder/teachers/pages',
    params: { current: 1, size: 500, status: 1 },
    emptyLabel: '未命名老师',
    labelKeys: ['teacherName', 'phone', 'teacherNo']
  },
  classes: {
    url: '/kinder/kindergarten-classes/pages',
    params: { current: 1, size: 500, status: 1 },
    emptyLabel: '未命名班级',
    labelKeys: ['className', 'gradeName', 'classCode']
  },
  students: {
    url: '/kinder/students/pages',
    params: { current: 1, size: 500, status: 1 },
    emptyLabel: '未命名学生',
    labelKeys: ['studentName', 'studentNo']
  },
  guardians: {
    url: '/kinder/guardians/pages',
    params: { current: 1, size: 500, status: 1 },
    emptyLabel: '未命名家长',
    labelKeys: ['guardianName', 'phone']
  }
}

export const modules: Record<string, ModuleConfig> = {
  classes: {
    key: 'classes',
    title: '班级管理',
    scopeLabel: '园务建档',
    description: '配置班级、年级和班主任归属，老师端只读取自己负责的班级。',
    emptyText: '暂无班级数据',
    searchPlaceholder: '输入班级名称、编码或年级',
    deleteConfirmText: '确认删除该班级档案？',
    listUrl: '/kinder/kindergarten-classes/pages',
    createUrl: '/kinder/kindergarten-classes',
    updateUrl: '/kinder/kindergarten-classes',
    deleteUrl: '/kinder/kindergarten-classes',
    idParam: 'id',
    keywordFields: ['classCode', 'className', 'gradeName', 'headTeacherId'],
    fields: [
      { key: 'classCode', label: '班级编码', required: true, placeholder: '例如 C2026001' },
      { key: 'className', label: '班级名称', required: true, placeholder: '例如 小一班' },
      { key: 'gradeName', label: '年级名称', placeholder: '例如 小班' },
      { key: 'headTeacherId', label: '班主任', type: 'select', source: 'teachers', valueType: 'number' },
      { key: 'status', label: '状态', type: 'select', valueType: 'number', default: 1, options: enabledStatusOptions }
    ],
    columns: [
      { key: 'classCode', label: '编码' },
      { key: 'className', label: '班级' },
      { key: 'gradeName', label: '年级' },
      { key: 'headTeacherId', label: '班主任' },
      { key: 'status', label: '状态' }
    ]
  },
  teachers: {
    key: 'teachers',
    title: '教师管理',
    scopeLabel: '园务建档',
    description: '维护本园老师档案；班级归属在班级管理中指定。',
    emptyText: '暂无老师数据',
    searchPlaceholder: '输入老师姓名、编号或手机号',
    deleteConfirmText: '确认删除该老师档案？',
    listUrl: '/kinder/teachers/pages',
    createUrl: '/kinder/teachers',
    updateUrl: '/kinder/teachers',
    deleteUrl: '/kinder/teachers',
    idParam: 'id',
    keywordFields: ['teacherNo', 'teacherName', 'phone', 'gender'],
    fields: [
      { key: 'teacherNo', label: '教师编号', required: true, placeholder: '例如 T2026001' },
      { key: 'teacherName', label: '教师姓名', required: true },
      { key: 'phone', label: '手机号' },
      { key: 'gender', label: '性别', type: 'select', options: genderOptions },
      { key: 'status', label: '状态', type: 'select', valueType: 'number', default: 1, options: enabledStatusOptions }
    ],
    columns: [
      { key: 'teacherNo', label: '编号' },
      { key: 'teacherName', label: '姓名' },
      { key: 'phone', label: '手机' },
      { key: 'gender', label: '性别' },
      { key: 'status', label: '状态' }
    ]
  },
  students: {
    key: 'students',
    title: '学生管理',
    scopeLabel: '学籍管理',
    description: '学生正式建档和班级分配由园务侧维护，老师端只处理已分配学生的日常记录。',
    emptyText: '暂无学生数据',
    searchPlaceholder: '输入学生姓名、学号或班级',
    deleteConfirmText: '确认删除该学生档案？',
    listUrl: '/kinder/students/pages',
    createUrl: '/kinder/students',
    updateUrl: '/kinder/students',
    deleteUrl: '/kinder/students',
    idParam: 'id',
    keywordFields: ['studentNo', 'studentName', 'gender', 'classId'],
    fields: [
      { key: 'studentNo', label: '学号', required: true, placeholder: '例如 S2026001' },
      { key: 'studentName', label: '学生姓名', required: true },
      { key: 'classId', label: '所在班级', type: 'select', source: 'classes', valueType: 'number' },
      { key: 'gender', label: '性别', type: 'select', options: genderOptions },
      { key: 'birthday', label: '出生日期', type: 'date' },
      { key: 'status', label: '状态', type: 'select', valueType: 'number', default: 1, options: enabledStatusOptions }
    ],
    columns: [
      { key: 'studentNo', label: '学号' },
      { key: 'studentName', label: '姓名' },
      { key: 'classId', label: '班级' },
      { key: 'gender', label: '性别' },
      { key: 'status', label: '状态' }
    ]
  },
  guardians: {
    key: 'guardians',
    title: '家长管理',
    scopeLabel: '家校关系',
    description: '维护家长基础档案；学生和家长的监护关系在“绑定关系”中确认。',
    emptyText: '暂无家长数据',
    searchPlaceholder: '输入家长姓名或手机号',
    deleteConfirmText: '确认删除该家长档案？',
    listUrl: '/kinder/guardians/pages',
    createUrl: '/kinder/guardians',
    updateUrl: '/kinder/guardians',
    deleteUrl: '/kinder/guardians',
    idParam: 'id',
    keywordFields: ['guardianName', 'phone'],
    fields: [
      { key: 'guardianName', label: '家长姓名', required: true },
      { key: 'phone', label: '手机号', required: true },
      { key: 'status', label: '状态', type: 'select', valueType: 'number', default: 1, options: enabledStatusOptions }
    ],
    columns: [
      { key: 'guardianName', label: '姓名' },
      { key: 'phone', label: '手机' },
      { key: 'userId', label: '账号ID' },
      { key: 'status', label: '状态' }
    ]
  },
  relations: {
    key: 'relations',
    title: '绑定关系',
    scopeLabel: '家校关系',
    description: '确认学生和家长的监护关系；家长端只能查看已绑定孩子，不能自助绑定。',
    emptyText: '暂无绑定关系',
    searchPlaceholder: '输入学生、家长或关系类型',
    deleteConfirmText: '确认解除该学生家长绑定？',
    listUrl: '/kinder/student-guardian-relations/pages',
    createUrl: '/kinder/student-guardian-relations',
    updateUrl: '/kinder/student-guardian-relations',
    deleteUrl: '/kinder/student-guardian-relations',
    idParam: 'id',
    keywordFields: ['studentId', 'guardianId', 'relationType'],
    fields: [
      { key: 'studentId', label: '学生', type: 'select', source: 'students', valueType: 'number', required: true },
      { key: 'guardianId', label: '家长', type: 'select', source: 'guardians', valueType: 'number', required: true },
      { key: 'relationType', label: '关系类型', type: 'select', options: relationTypeOptions, required: true },
      { key: 'primaryContact', label: '主联系人', type: 'select', valueType: 'boolean', default: false, options: [
        { label: '是', value: true },
        { label: '否', value: false }
      ] },
      { key: 'status', label: '状态', type: 'select', valueType: 'number', default: 1, options: enabledStatusOptions }
    ],
    columns: [
      { key: 'studentId', label: '学生' },
      { key: 'guardianId', label: '家长' },
      { key: 'relationType', label: '关系' },
      { key: 'primaryContact', label: '主联系人' },
      { key: 'status', label: '状态' }
    ]
  },
  notices: {
    key: 'notices',
    title: '通知发布',
    scopeLabel: '家园通知',
    description: '发布面向全园的通知，老师端和家长端只负责查看。',
    emptyText: '暂无通知数据',
    searchPlaceholder: '输入通知标题或内容',
    deleteConfirmText: '确认删除该通知？',
    listUrl: '/kinder/notices/pages',
    createUrl: '/kinder/notices',
    updateUrl: '/kinder/notices',
    deleteUrl: '/kinder/notices',
    idParam: 'id',
    keywordFields: ['title', 'content'],
    fields: [
      { key: 'title', label: '标题', required: true },
      { key: 'content', label: '内容', type: 'textarea', required: true },
      { key: 'status', label: '状态', type: 'select', valueType: 'number', default: 1, options: enabledStatusOptions }
    ],
    columns: [
      { key: 'title', label: '标题' },
      { key: 'publishTime', label: '发布时间' },
      { key: 'status', label: '状态' }
    ]
  }
}

export function principalHome() {
  return http.get<ApiResponse<Record<string, unknown>>>('/kinder/principal-portal/home')
}

export function listModule(config: ModuleConfig, params: Record<string, unknown>) {
  return http.get<ApiResponse<ModuleListPayload>>(config.listUrl, { params })
}

export function listOptionSource(source: OptionSource) {
  const config = optionSources[source]
  return http.get<ApiResponse<ModuleListPayload>>(config.url, { params: config.params })
}

export function createItem(config: ModuleConfig, data: Record<string, unknown>) {
  return http.post(config.createUrl || config.listUrl, data)
}

export function updateItem(config: ModuleConfig, data: Record<string, unknown>) {
  return http.put(config.updateUrl || config.listUrl, data)
}

export function deleteItem(config: ModuleConfig, id: unknown) {
  return http.delete(config.deleteUrl || config.listUrl, { params: { [config.idParam || 'id']: id } })
}
