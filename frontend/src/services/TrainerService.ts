import $api from "../http";

import type { UserType } from "../Models/users/UserType";


export default class TrainerService { 
    static updateTrainer(id: string, data: Partial<UserType>) {
  return $api.put(`/trainers/${id}`, data);
}
}