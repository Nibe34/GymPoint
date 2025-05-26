package gympoint.backend.trainingservice.repository;

import gympoint.backend.trainingservice.entity.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {
    List<TrainingSession> findByTrainerId(Long trainerId);
    List<TrainingSession> findByTrainerIdAndStartTimeAfter(Long trainerId, LocalDateTime startTime);
} 