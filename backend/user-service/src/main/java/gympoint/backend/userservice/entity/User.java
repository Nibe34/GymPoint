package gympoint.backend.userservice.entity;


import gympoint.backend.common.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Role role;


    @JoinColumn(nullable = true)
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private ClientProfile clientProfile;

    @JoinColumn(nullable = true)
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private TrainerProfile trainerProfile;

    @JoinColumn(nullable = true)
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private AdminProfile adminProfile;


}
