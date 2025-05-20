import type IUser from './IUser'

export default interface IClient extends IUser {
    dateOfBirth: string;
    phoneNumber: string;
    address: string;
    emergencyContact: string;
}