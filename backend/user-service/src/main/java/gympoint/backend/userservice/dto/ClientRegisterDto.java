package gympoint.backend.userservice.dto;

import gympoint.backend.common.enums.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClientRegisterDto extends UserCreateDto {
    private String phoneNumber;
    private String address;
    private String emergencyContact;

    public ClientRegisterDto() {
        this.setRole(Role.CLIENT);
    }
} 