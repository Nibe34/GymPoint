package gympoint.backend.subscriptionservice.repository;

import gympoint.backend.subscriptionservice.entity.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {
    List<UserSubscription> findByUserEmail(String userEmail);
    Optional<UserSubscription> findFirstByUserEmailAndIsActiveTrue(String userEmail);
} 