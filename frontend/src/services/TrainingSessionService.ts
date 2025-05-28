// services/TrainingSessionService.ts
import {$trainerApi} from "../http";
import type { AxiosResponse } from "axios";

export interface TrainingSessionDto {
  id?: number;
  trainerId: number;
  startTime: string;
  endTime?: string;
  isAvailable?: boolean;
  maxParticipants: number;
}

export default class TrainingSessionService {
  static fetchAllAvailable(): Promise<AxiosResponse<TrainingSessionDto[]>> {
    return $trainerApi.get("/training-sessions/available");
  }

  static fetchByTrainer(trainerId: number): Promise<AxiosResponse<TrainingSessionDto[]>> {
    return $trainerApi.get(`/training-sessions/trainer/${trainerId}`);
  }

  static createSession(session: TrainingSessionDto): Promise<AxiosResponse<TrainingSessionDto>> {
    return $trainerApi.post("/training-sessions", session);
  }

  static updateSession(id: number, session: TrainingSessionDto): Promise<AxiosResponse<TrainingSessionDto>> {
    return $trainerApi.put(`/training-sessions/${id}`, session);
  }

  static updateAvailability(id: number, isAvailable: boolean): Promise<AxiosResponse<TrainingSessionDto>> {
    return $trainerApi.patch(`/training-sessions/${id}/availability?isAvailable=${isAvailable}`);
  }

  static deleteSession(id: number) {
    return $trainerApi.delete(`/training-sessions/${id}`);
  }
}
