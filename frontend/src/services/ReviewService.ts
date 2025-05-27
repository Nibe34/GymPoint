// services/ReviewService.ts
import {$trainerApi} from "../http";
import type { AxiosResponse } from "axios";

export interface ReviewDto {
  id: number;
  trainerId: number;
  clientId: number;
  rating: number;
  comment: string;
  // ...інші поля
}

export default class ReviewService {
  static fetchByTrainer(trainerId: number): Promise<AxiosResponse<ReviewDto[]>> {
    return $trainerApi.get(`/reviews/trainer/${trainerId}`);
  }

  static fetchByClient(clientId: number): Promise<AxiosResponse<ReviewDto[]>> {
    return $trainerApi.get(`/reviews/client/${clientId}`);
  }

  static create(review: ReviewDto): Promise<AxiosResponse<ReviewDto>> {
    return $trainerApi.post("/reviews", review);
  }

  static update(id: number, review: ReviewDto): Promise<AxiosResponse<ReviewDto>> {
    return $trainerApi.put(`/reviews/${id}`, review);
  }

  static delete(id: number) {
    return $trainerApi.delete(`/reviews/${id}`);
  }
}
