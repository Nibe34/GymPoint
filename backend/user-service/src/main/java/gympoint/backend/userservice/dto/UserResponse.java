package gympoint.backend.userservice.dto;


import gympoint.backend.common.enums.Role;

public class UserResponse {
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;

    private TrainerProfileResponse trainerProfile;
    private ClientProfileResponse clientProfile;
}
