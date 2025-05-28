import $api from "../http";
import type ITrainer from "../Models/users/ITrainer";

import type { UserType } from "../Models/users/UserType";


export default class TrainerService { 
    static updateTrainer(id: string, data: Partial<UserType>) {
  return $api.put(`/trainers/${id}`, data);
  
}

  static async fetchTrainers() {
    return $api.get<ITrainer[]>("/trainers");
  }

  static async fetchTrainerById(id: string) {
  return $api.get<ITrainer>(`/trainers/${id}`);
}

}