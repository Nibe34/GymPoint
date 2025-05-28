package gympoint.backend.workoutservice.entity;

import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "workout_bookings")
public class WorkoutBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "training_session_id", nullable = false)
    private Long trainingSessionId;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "trainer_id", nullable = false)
    private Long trainerId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
} 