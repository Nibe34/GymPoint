package gympoint.backend.trainingservice.repository;

import gympoint.backend.trainingservice.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByTrainerId(Long trainerId);
    List<Review> findByClientId(Long clientId);
    boolean existsByTrainerIdAndClientId(Long trainerId, Long clientId);
} 