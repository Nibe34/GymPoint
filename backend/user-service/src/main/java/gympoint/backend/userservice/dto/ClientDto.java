package gympoint.backend.userservice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClientDto extends UserDto {
    private String address;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String emergencyContact;
}