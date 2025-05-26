package gympoint.backend.workoutservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkoutBookingDto {
    private Long id;
    private Long trainingSessionId;
    private Long clientId;
    private Long trainerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 