import type IUser from './IUser'
import type Roles from './Roles'

export default interface IClient extends IUser {
    dateOfBirth: string;
    phoneNumber: string;
    address: string;
    emergencyContact: string;
     role: Roles.client;
}