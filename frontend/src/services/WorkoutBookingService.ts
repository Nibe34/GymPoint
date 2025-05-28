// services/WorkoutBookingService.ts
import {$BookingApi} from "../http";
export default class WorkoutBookingService {
    static createBooking(data: { trainingSessionId: number, clientId: number, trainerId: number }) {
        return $BookingApi.post("/workout-bookings", data);
    }

   
    static fetchBookingsBySession(trainingSessionId: number) {
        return $BookingApi.get(`/workout-bookings/training-session/${trainingSessionId}`);
    }
}
