import type { AxiosResponse, InternalAxiosRequestConfig } from 'axios';
import axios from 'axios';
import type { AuthResponse } from '../Models/responce/AutResponse';

export const BASE_URL = 'http://localhost:8081/api/';


//httpTrainerApi.ts
const $api = axios.create({
    withCredentials: true,
    baseURL: BASE_URL
});

//httpTrainerApi.ts
export const $trainerApi = axios.create({
  baseURL: "http://localhost:8083/api/",
  withCredentials: true,
});


//httpTrainerApi.ts
export const $BookingApi = axios.create({
  baseURL: "http://localhost:8084/api/",
  withCredentials: true,
});

$BookingApi.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});



$trainerApi.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});


$api.interceptors.request.use((config: InternalAxiosRequestConfig) => {
    // Не додаємо Authorization для /auth/* і /users/me
    if (!config.url?.includes('/auth/') && !config.url?.includes('/users/me')) {
        const token = localStorage.getItem('token');
        if (token && token !== 'undefined') {
            config.headers.Authorization = `Bearer ${token}`;
            console.log('Додано Authorization для', config.url, ':', config.headers.Authorization);
        } else {
            console.log('Токен відсутній або undefined для', config.url);
        }
    } else {
        console.log('Пропускаємо Authorization для', config.url);
    }
    return config;
});

$api.interceptors.response.use(
    (response) => response,
    async (error) => {
        const originalRequest = error.config;

        // Якщо вже пробували refresh або сам refresh неавторизований — не повторювати!
        if (
            (error.response?.status === 401 || error.response?.status === 403) &&
            !originalRequest._retry &&
            !originalRequest.url.includes("/auth/refresh")
        ) {
            originalRequest._retry = true;
            try {
                const response = await $api.post('/auth/refresh', {}, { withCredentials: true });
                localStorage.setItem('token', response.data.accessToken);
                originalRequest.headers['Authorization'] = `Bearer ${response.data.accessToken}`;
                return $api(originalRequest);
            } catch (refreshError) {
                // Якщо refresh теж не спрацював — логаут і редірект
                localStorage.removeItem('token');
                window.location.href = '/login';
                return Promise.reject(refreshError);
            }
        }
        // Якщо це сам /auth/refresh — не намагатись повторити
        return Promise.reject(error);
    }
);


export default $api;