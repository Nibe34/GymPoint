package gympoint.backend.userservice.entity;


import jakarta.persistence.*;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "clients")
public class Client extends User {
    @Column(nullable = false)
    private LocalDate dateOfBirth;
}