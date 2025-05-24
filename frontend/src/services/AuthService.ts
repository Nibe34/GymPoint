import type { AxiosResponse } from "axios";
import $api from "../http";
import type { AuthResponse } from "../Models/responce/AutResponse";

import type { UserType } from "../Models/users/UserType";



export default class AuthService {
    static async login(email:string, password:string): Promise<AxiosResponse<AuthResponse>> {
        return $api.post<AuthResponse>('auth/login', {email , password})
    }

     static async registration(user: UserType): Promise<AxiosResponse<AuthResponse>> {
        return $api.post<AuthResponse>('auth/register', user)
    }

}
