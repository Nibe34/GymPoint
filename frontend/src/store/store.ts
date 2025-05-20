import { makeAutoObservable } from "mobx";
import type IUser from "../Models/users/IUser";
import AuthService from "../services/AuthService";

import type IAdmin from "../Models/users/IAdmin";
import type ITrainer from "../Models/users/ITrainer";
import type IClient from "../Models/users/IClient";

 type requestUser = IAdmin | ITrainer | IClient;

export default class Store {
    user = {} as IUser;
    isAuth = false;

    constructor() { 
        makeAutoObservable(this);
    }

    setAuth(bool:boolean) {
        this.isAuth = bool;
    }

    setUser(user:IUser) {
        this.user = user;
    }


    async login(email:string, password: string) { 
        try {
            const response = await AuthService.login(email, password);
            localStorage.setItem('token', response.data.accessToken);
            this.setAuth(true);
            // this.setUser()
        } catch (error) {
            console.log("error at login" + error);
            
        }
    }

     async registration(userData: requestUser) { 
        try {
            const response = await AuthService.registration(userData);
            localStorage.setItem('token', response.data.accessToken);
            this.setAuth(true);
            // this.setUser()
        } catch (error) {
            console.log("error at login" + error);
            
        }
    }

     async logout() { 
        try {
            const response = await AuthService.logout();
            localStorage.removeItem('token');
            this.setAuth(false);
            // this.setUser()
        } catch (error) {
            console.log("error at login" + error);
            
        }
    }
}