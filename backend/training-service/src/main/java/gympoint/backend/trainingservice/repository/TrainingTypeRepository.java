package gympoint.backend.trainingservice.repository;

import gympoint.backend.trainingservice.entity.TrainingType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TrainingTypeRepository extends JpaRepository<TrainingType, Long> {
    List<TrainingType> findByTrainerId(Long trainerId);
} 