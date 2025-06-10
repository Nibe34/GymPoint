package gympoint.backend.authservice.dto;

import gympoint.backend.authservice.entity.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private UserRole role;
} 