import { makeAutoObservable } from "mobx";
import AuthService from "../services/AuthService";

import UserService from "../services/UserService";


 import type { UserType } from "../Models/users/UserType";
import axios from "axios";
import type { AuthResponse } from "../Models/responce/AutResponse";
import $api from "../http";

export default class Store {
    user = {} as UserType;
    isAuth = false;

    constructor() { 
        makeAutoObservable(this);
    }

    setAuth(bool:boolean) {
        this.isAuth = bool;
    }

    setUser(user:UserType) {
        this.user = user;
    }


    async login(email:string, password: string) { 
        try {
            const response = await AuthService.login(email, password);
            console.log(response.data);
            
            localStorage.setItem('token', response.data.accessToken);
            console.log(response.data.refreshToken);
            
            this.setAuth(true);
             const userResponse = await UserService.fetchCurrentUser();
        this.setUser(userResponse.data);
        console.log( userResponse.data);
        
        } catch (error) {
            console.log("error at login" + error);
            
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

        // store.ts
async checkAuth() {
  if (localStorage.getItem('token')) {
    try {
      const response = await $api.get<AuthResponse>('/auth/refresh', {
        withCredentials: true,
      });
      localStorage.setItem("token", response.data.accessToken);

      this.setAuth(true);
      const userResponse = await UserService.fetchCurrentUser();
      this.setUser(userResponse.data);

    } catch (error) {
      this.setAuth(false);
      this.setUser({} as UserType);
    }
  } else {
    this.setAuth(false);
    this.setUser({} as UserType);
  }
}



      logout() { 
            localStorage.removeItem('token');
            this.setAuth(false);
             this.setUser({} as UserType)
    }
}