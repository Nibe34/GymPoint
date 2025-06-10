package gympoint.backend.userservice.dto;

import gympoint.backend.common.enums.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdminCreateDto extends UserCreateDto {
    public AdminCreateDto() {
        this.setRole(Role.ROLE_ADMIN);
    }
}