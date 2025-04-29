package gympoint.backend.userservice.dto;

import gympoint.backend.common.enums.Role;
import lombok.Data;

@Data
public abstract class UserCreateDto {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Role role;
}