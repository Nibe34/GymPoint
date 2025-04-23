package gympoint.backend.userservice.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "trainer_profiles")
public class TrainerProfile {
    @Id
    private long id;

    @Column(unique = false, nullable = false)
    private String bio;
    @Column(unique = false, nullable = false)
    private String specialization;
    @Column(unique = false, nullable = false)
    private int rating;


    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}
