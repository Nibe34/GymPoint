package gympoint.backend.userservice.dto;

import gympoint.backend.common.enums.Role;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterDto {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Role role;
    
    // Client specific fields
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String address;
    private String emergencyContact;
    
    // Trainer specific fields
    private String bio;
    private String specialization;
    private String certification;
}