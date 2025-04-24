package gympoint.backend.userservice.dto;


import gympoint.backend.common.enums.Role;
import lombok.Getter;

@Getter
public class UserRegistrationRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Role role;

    private TrainerProfileDto trainerProfile;
    private ClientProfileDto clientProfile;
}
