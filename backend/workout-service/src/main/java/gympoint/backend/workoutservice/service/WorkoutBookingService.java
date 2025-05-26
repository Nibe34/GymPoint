package gympoint.backend.workoutservice.service;

import gympoint.backend.workoutservice.dto.WorkoutBookingDto;
import java.util.List;

public interface WorkoutBookingService {
    WorkoutBookingDto createWorkoutBooking(WorkoutBookingDto workoutBookingDto);
    WorkoutBookingDto updateWorkoutBooking(Long id, WorkoutBookingDto workoutBookingDto);
    void deleteWorkoutBooking(Long id);
    WorkoutBookingDto getWorkoutBookingById(Long id);
    List<WorkoutBookingDto> getWorkoutBookingsByClientId(Long clientId);
    List<WorkoutBookingDto> getWorkoutBookingsByTrainerId(Long trainerId);
    List<WorkoutBookingDto> getWorkoutBookingsByTrainingSessionId(Long trainingSessionId);
} 