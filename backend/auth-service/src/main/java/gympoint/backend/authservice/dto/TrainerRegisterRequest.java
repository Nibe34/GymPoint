package gympoint.backend.authservice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TrainerRegisterRequest extends RegisterRequest {
    private String bio;
    private String certification;
    private String specialization;
} 