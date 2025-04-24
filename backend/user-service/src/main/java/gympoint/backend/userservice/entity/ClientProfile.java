package gympoint.backend.userservice.entity;


import jakarta.persistence.*;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Entity
@Table(name = "client_profiles")
public class ClientProfile {
    @Id
    private long id;

    @Column(nullable = false)
    private LocalDate dateOfBirth;


    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;


}
