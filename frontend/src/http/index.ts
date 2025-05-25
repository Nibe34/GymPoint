import type { AxiosResponse, InternalAxiosRequestConfig } from 'axios';
import axios from 'axios';
import type { AuthResponse } from '../Models/responce/AutResponse';

export const BASE_URL = 'http://localhost:8081/api/';

const $api = axios.create({
    withCredentials: true,
    baseURL: BASE_URL
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
        // Додаємо і 401, і 403
        if (
            (error.response?.status === 401 || error.response?.status === 403) &&
            !originalRequest._retry
        ) {
            originalRequest._retry = true;
            try {
                const response = await $api.post('/auth/refresh', {}, { withCredentials: true });
                localStorage.setItem('token', response.data.accessToken);
                originalRequest.headers['Authorization'] = `Bearer ${response.data.accessToken}`;
                return $api(originalRequest);
            } catch (refreshError) {
                localStorage.removeItem('token');
                window.location.href = '/login';
                return Promise.reject(refreshError);
            }
        }
        return Promise.reject(error);
    }
);

export default $api;