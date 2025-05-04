package gympoint.backend.userservice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TrainerCreateDto extends UserCreateDto {
    private String bio;
    private String certification;
    private String specialization;
}