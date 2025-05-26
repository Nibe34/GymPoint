package gympoint.backend.trainerservice.repository;

import gympoint.backend.trainerservice.entity.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {
    List<TrainingSession> findByTrainerId(Long trainerId);
    List<TrainingSession> findByTrainerIdAndStartTimeAfter(Long trainerId, LocalDateTime startTime);
} 