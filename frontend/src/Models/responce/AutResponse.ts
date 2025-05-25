import type IUser from "../users/IUser"

export interface AuthResponse {
    accessTokenCookie?: string;
    accessToken: string;
    refreshToken: string;
    userId: string;
}