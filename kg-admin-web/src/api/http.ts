import axios from 'axios'

const http = axios.create({
  baseURL: '/api'
})

function redirectToLogin() {
  localStorage.removeItem('kg-token')
  if (window.location.pathname !== '/login') {
    const redirect = encodeURIComponent(window.location.pathname + window.location.search)
    window.location.replace(`/login?redirect=${redirect}`)
  }
}

http.interceptors.request.use((config) => {
  const token = localStorage.getItem('kg-token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

http.interceptors.response.use(
  (response) => {
    if (response.data?.code === '401') {
      redirectToLogin()
      return Promise.reject(new Error('Unauthorized'))
    }
    return response
  },
  (error) => {
    if (error.response?.status === 401 || error.response?.data?.code === '401') {
      redirectToLogin()
    }
    return Promise.reject(error)
  }
)

export default http
