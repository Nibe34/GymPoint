package gympoint.backend.authservice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClientRegisterRequest extends RegisterRequest {
    private String address;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String emergencyContact;
} 