package gympoint.backend.userservice.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "trainers")
public class Trainer extends User {
    @Column(nullable = false)
    private String bio;

    @Column(nullable = false)
    private String specialization;

    @Column(nullable = false)
    private float rating;
}