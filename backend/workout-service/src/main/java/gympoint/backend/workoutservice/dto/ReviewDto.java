package gympoint.backend.workoutservice.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewDto {
    private Long id;

    @NotNull(message = "Training session ID is required")
    private Long trainingSessionId;

    @NotNull(message = "Client ID is required")
    private Long clientId;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;

    private String comment;
} 