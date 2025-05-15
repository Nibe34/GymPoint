import type IUser from '../Models/IUser'
import type { AxiosResponse } from "axios";
import $api from "../http";


export default class UserService { 
    static fetchUsers(): Promise<AxiosResponse<IUser[]>> { 
        return $api.get<IUser[]>('users')
    }
}