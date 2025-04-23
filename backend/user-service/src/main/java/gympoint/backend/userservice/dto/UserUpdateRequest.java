package gympoint.backend.userservice.dto;


import gympoint.backend.common.enums.Role;
import lombok.Getter;

@Getter
public class UserUpdateRequest {
    private String firstName;
    private String lastName;

    private TrainerProfileUpdateRequest trainerProfileUpdateRequest;
    private ClientProfileUpdateRequest clientProfileUpdateRequest;
}
