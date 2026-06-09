import http from '../http'

export function fetchRecipes(params: any) {
  return http.get('/kinder/recipes/pages', { params })
}

export function createRecipe(data: any) {
  return http.post('/kinder/recipes', data)
}

export function updateRecipe(data: any) {
  return http.put('/kinder/recipes', data)
}

export function deleteRecipe(id: number | string) {
  return http.delete('/kinder/recipes', { params: { id } })
}
