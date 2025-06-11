package gympoint.backend.userservice.dto;

import gympoint.backend.common.enums.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TrainerRegisterRequest extends UserCreateDto {
    private String bio;
    private String certification;
    private String specialization;

    public TrainerRegisterRequest() {
        this.setRole(Role.ROLE_TRAINER);
    }
} 