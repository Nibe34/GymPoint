package gympoint.backend.trainerservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "training_sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trainer_id", nullable = false)
    private Long trainerId;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "max_participants")
    private Integer maxParticipants;
} 