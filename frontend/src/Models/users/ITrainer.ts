import type IUser from './IUser'

export default interface ITrainer extends IUser {
    bio: string,
    specialization: string,
    certification: string
 }