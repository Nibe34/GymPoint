package gympoint.backend.userservice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TrainerDto extends UserDto {
    private String bio;
    private String certification;
    private String specialization;
    private Double rating;
}