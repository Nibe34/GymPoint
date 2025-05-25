
import type { AxiosResponse } from "axios";
import $api from "../http";

import type { UserType } from "../Models/users/UserType";


export default class UserService { 
    static fetchUsers(): Promise<AxiosResponse<UserType[]>> { 
        return $api.get<UserType[]>('users')
    }

    static fetchCurrentUser(): Promise<AxiosResponse<UserType>> {
    return $api.get<UserType>('users/me');
}

    static fetchUserById(id: string): Promise<AxiosResponse<UserType>> {
    return $api.get<UserType>(`users/${id}`);
}
}