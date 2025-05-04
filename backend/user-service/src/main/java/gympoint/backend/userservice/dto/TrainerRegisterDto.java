package gympoint.backend.userservice.dto;

import gympoint.backend.common.enums.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TrainerRegisterDto extends UserCreateDto {
    private String specialization;
    private String certification;

    public TrainerRegisterDto() {
        this.setRole(Role.TRAINER);
    }
} 