import type { AxiosResponse } from "axios";
import $api from "../http";
import type { AuthResponse } from "../Models/responce/AutResponse";

import type IAdmin from "../Models/users/IAdmin"
import type ITrainer from "../Models/users/ITrainer"
import type IClient from "../Models/users/IClient";

type requestUser = IAdmin | ITrainer | IClient;


export default class AuthService {
    static async login(email:string, password:string): Promise<AxiosResponse<AuthResponse>> {
        return $api.post<AuthResponse>('auth/login', {email, password})
    }

     static async registration(user: requestUser): Promise<AxiosResponse<AuthResponse>> {
        return $api.post<AuthResponse>('auth/register', {...user})
    }

     static async logout(): Promise<void> {
        return $api.post('/logout')
    }
}
