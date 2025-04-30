package gympoint.backend.userservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "trainers")
@DiscriminatorValue("TRAINER")
public class Trainer extends User {
    @Column(nullable = false)
    private String bio;

    @Column(nullable = false)
    private String specialization;

    @Column(nullable = false)
    private String certification;

    @Column(nullable = false)
    private Double rating;
}