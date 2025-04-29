package gympoint.backend.userservice.dto;

import gympoint.backend.common.enums.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdminRegisterDto extends UserCreateDto {
    public AdminRegisterDto() {
        this.setRole(Role.ADMIN);
    }
} 