package gympoint.backend.userservice.dto;

import gympoint.backend.common.enums.Role;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
}