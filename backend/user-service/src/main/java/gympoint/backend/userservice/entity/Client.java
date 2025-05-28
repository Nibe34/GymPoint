package gympoint.backend.userservice.entity;

import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "clients")
@DiscriminatorValue("ROLE_CLIENT")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Client extends User {
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @Column(name = "address")
    private String address;
    
    @Column(nullable = false)
    private String emergencyContact;
}