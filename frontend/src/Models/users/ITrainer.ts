import type IUser from './IUser'
import type Roles from './Roles'

export default interface ITrainer extends IUser {
    bio: string,
    specialization: string,
    certification: string
     role: Roles.trainer;
 }