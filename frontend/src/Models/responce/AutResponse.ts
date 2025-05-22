import type IUser from "../users/IUser"

export interface AuthResponse {
    accessToken: string;
    refreshToken: string;
    tokenType: string;
}