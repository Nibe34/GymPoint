import { makeAutoObservable } from "mobx";
import AuthService from "../services/AuthService";

import UserService from "../services/UserService";


 import type { UserType } from "../Models/users/UserType";
import type { AuthResponse } from "../Models/responce/AutResponse";
import $api from "../http";

export default class Store {
    user = {} as UserType;
    isAuth = false;
     isInitialized = false; 

    constructor() { 
        makeAutoObservable(this);
    }

    setAuth(bool:boolean) {
        this.isAuth = bool;
    }

    setUser(user:UserType) {
        this.user = user;
    }

async login(email: string, password: string) {
    try {
        const response = await AuthService.login(email, password);
        console.log('Відповідь від /auth/login:', response.data);

        // Витягуємо access_token з рядка 'access_token=...; HttpOnly...'
        const accessToken = response.data.accessTokenCookie?.split(';')[0]?.split('=')[1];
        if (accessToken) {
            localStorage.setItem('token', accessToken);
            this.setAuth(true);

            const userResponse = await UserService.fetchCurrentUser();
            console.log('Дані користувача з /users/me:', userResponse.data);
            this.setUser(userResponse.data);
            return response;
        } else {
            throw new Error('accessToken відсутній у відповіді');
        }
    } catch (error) {
        console.error('Помилка в login:', error);
    }
}


async checkAuth() {
    try {
        console.log('Перевірка автентифікації через /auth/refresh');
        const response = await $api.post<AuthResponse>('/auth/refresh', {}, {
            withCredentials: true
        });
        console.log('Отримано новий токен:', response.data.accessToken);
        localStorage.setItem('token', response.data.accessToken);
        this.setAuth(true);
        const userResponse = await UserService.fetchCurrentUser();
        this.setUser(userResponse.data);
    } catch (error) {
        console.error('Помилка в checkAuth:', error);
        this.setAuth(false);
        this.setUser({} as UserType);
    }finally {
    console.log("Auth перевірено, ініціалізуємо!");
    this.isInitialized = true;
}

}



     async registration(userData: UserType) { 
        try {
            const response = await AuthService.registration(userData);
             console.log(response.data);
            localStorage.setItem('token', response.data.accessToken);
            this.setAuth(true);
            const userResponse = await UserService.fetchCurrentUser();
        this.setUser(userResponse.data);
        } catch (error) {
            console.log("error at reg" + error);
            
        }
    }




      logout() { 
            localStorage.removeItem('token');
            this.setAuth(false);
             this.setUser({} as UserType)
    }
}
function getCookie(name: string) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop()?.split(';').shift();
    return null;
}