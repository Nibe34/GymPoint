
import type { AxiosResponse, InternalAxiosRequestConfig  } from 'axios';
import axios from 'axios';
import type { AuthResponse } from '../Models/responce/AutResponse';


export const BASE_URL = 'http://localhost:8081/api/'

const $api = axios.create({
    withCredentials: true,
    baseURL: BASE_URL
})

$api.interceptors.request.use((config: InternalAxiosRequestConfig) => {
  // Не додаємо токен до запиту /auth/refresh
  if (!config.url?.includes('/auth/refresh')) {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
  }
  return config;
});



$api.interceptors.response.use(
  (response: AxiosResponse) => {
    return response;
  },
  async (error) => {
    const originalRequest = error.config;

    // Перевіряємо 401 і чи ми вже намагались оновити токен, щоб не зациклити
    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;
      try {
        const response = await $api.get<AuthResponse>('/auth/refresh', {
          withCredentials: true
        });

        

        localStorage.setItem('token', response.data.accessToken);

        // Додаємо токен до заголовка і повторюємо запит
        originalRequest.headers['Authorization'] = `Bearer ${response.data.accessToken}`;
        return $api(originalRequest);
      } catch (refreshError) {
        // Наприклад, можна видалити токен і перенаправити на логін
        localStorage.removeItem('token');
        window.location.href = '/login';
        return Promise.reject(refreshError);
      }
    }

    return Promise.reject(error);
  }
);


export default $api;