package gympoint.backend.userservice.repository;


import gympoint.backend.userservice.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    List<Trainer> findBySpecialization(String specialization);
    List<Trainer> findByRatingGreaterThan(float rating);
}