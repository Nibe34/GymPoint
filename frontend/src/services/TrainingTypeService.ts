// services/TrainingTypeService.ts
import {$trainerApi} from "../http";
import type { AxiosResponse } from "axios";

export interface TrainingTypeDto {
  id: number;
  trainerId: number;
  name: string;
  description: string;
}

export default class TrainingTypeService {
  static fetchByTrainer(trainerId: number): Promise<AxiosResponse<TrainingTypeDto[]>> {
    return $trainerApi.get(`/training-types/trainer/${trainerId}`);
  }

  static fetchById(id: number): Promise<AxiosResponse<TrainingTypeDto>> {
    return $trainerApi.get(`/training-types/${id}`);
  }

  static create(trainingType: TrainingTypeDto): Promise<AxiosResponse<TrainingTypeDto>> {
    return $trainerApi.post("/training-types", trainingType);
  }

  static update(id: number, trainingType: TrainingTypeDto): Promise<AxiosResponse<TrainingTypeDto>> {
    return $trainerApi.put(`/training-types/${id}`, trainingType);
  }

  static delete(id: number) {
    return $trainerApi.delete(`/training-types/${id}`);
  }
}
