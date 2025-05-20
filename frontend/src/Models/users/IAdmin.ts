import type IUser from './IUser'
import type Roles from './Roles'

export default interface IAdmin extends IUser{
    role: Roles.admin;
}