import http from '../http'

// --- 字典类型接口 ---
export function fetchDictTypes(params: any) {
  return http.get('/dict-types/pages', { params })
}

export function getDictType(id: number | string) {
  return http.get('/dict-types', { params: { id } })
}

export function createDictType(data: any) {
  return http.post('/dict-types', data)
}

export function updateDictType(data: any) {
  return http.put('/dict-types', data)
}

export function deleteDictType(id: number | string) {
  return http.delete('/dict-types', { params: { id } })
}

// --- 字典数据/值接口 ---
export function fetchDictValues(params: any) {
  return http.get('/dict-values/pages', { params })
}

export function getDictValue(id: number | string) {
  return http.get('/dict-values', { params: { id } })
}

export function createDictValue(data: any) {
  return http.post('/dict-values', data)
}

export function updateDictValue(data: any) {
  return http.put('/dict-values', data)
}

export function deleteDictValue(id: number | string) {
  return http.delete('/dict-values', { params: { id } })
}
