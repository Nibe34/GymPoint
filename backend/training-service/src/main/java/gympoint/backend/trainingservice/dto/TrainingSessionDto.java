package gympoint.backend.trainingservice.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TrainingSessionDto {
    private Long id;
    private Long trainerId;
    private LocalDateTime startTime;
    private Integer maxParticipants;
} 