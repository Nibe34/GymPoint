package gympoint.backend.userservice.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "admin_profiles")
public class AdminProfile {
    @Id
    private long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}
