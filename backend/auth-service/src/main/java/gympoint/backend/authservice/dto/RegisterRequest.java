package gympoint.backend.authservice.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import gympoint.backend.common.enums.Role;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "role", visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = TrainerRegisterRequest.class, name = "ROLE_TRAINER"),
    @JsonSubTypes.Type(value = ClientRegisterRequest.class, name = "ROLE_CLIENT"),
    @JsonSubTypes.Type(value = AdminRegisterRequest.class, name = "ROLE_ADMIN")
})
public class RegisterRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Role role;
} 