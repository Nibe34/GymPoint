package gympoint.backend.userservice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClientCreateDto extends UserCreateDto {
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String address;
    private String emergencyContact;
}