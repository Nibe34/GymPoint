package gympoint.backend.userservice.repository;

import gympoint.backend.userservice.entity.Trainer;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends BaseUserRepository<Trainer> {
}