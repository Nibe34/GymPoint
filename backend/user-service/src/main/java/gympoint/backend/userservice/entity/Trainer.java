package gympoint.backend.userservice.entity;

import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "trainers")
@DiscriminatorValue("ROLE_TRAINER")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Trainer extends User {
    @Column(name = "specialization")
    private String specialization;

    @Column(nullable = false)
    private String bio;

    @Column(nullable = false)
    private String certification;

    @Column(nullable = false)
    private Double rating;
}