package gympoint.backend.subscriptionservice.service;

import gympoint.backend.subscriptionservice.dto.UserSubscriptionDTO;
import gympoint.backend.subscriptionservice.entity.Subscription;
import gympoint.backend.subscriptionservice.entity.UserSubscription;
import gympoint.backend.subscriptionservice.mapper.UserSubscriptionMapper;
import gympoint.backend.subscriptionservice.repository.UserSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserSubscriptionService {
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final SubscriptionService subscriptionService;
    private final UserSubscriptionMapper userSubscriptionMapper;

    public List<UserSubscriptionDTO> getUserSubscriptions(String userEmail) {
        return userSubscriptionRepository.findByUserEmail(userEmail).stream()
                .map(userSubscriptionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserSubscriptionDTO getActiveSubscription(String userEmail) {
        UserSubscription activeSubscription = userSubscriptionRepository.findFirstByUserEmailAndIsActiveTrue(userEmail)
                .orElseThrow(() -> new RuntimeException("No active subscription found"));
        
        // Update isActive status based on current time
        if (LocalDateTime.now().isAfter(activeSubscription.getEndDate())) {
            activeSubscription.setIsActive(false);
            userSubscriptionRepository.save(activeSubscription);
        }
        
        return userSubscriptionMapper.toDTO(activeSubscription);
    }

    @Transactional
    public UserSubscriptionDTO purchaseSubscription(String userEmail, Long subscriptionId) {
        // Check if user already has an active subscription
        if (userSubscriptionRepository.findFirstByUserEmailAndIsActiveTrue(userEmail).isPresent()) {
            throw new RuntimeException("User already has an active subscription");
        }

        Subscription subscription = subscriptionService.getSubscriptionById(subscriptionId);
        LocalDateTime now = LocalDateTime.now();
        
        UserSubscription userSubscription = new UserSubscription();
        userSubscription.setUserEmail(userEmail);
        userSubscription.setSubscription(subscription);
        userSubscription.setStartDate(now);
        userSubscription.setEndDate(now.plusDays(subscription.getDurationDays()));
        userSubscription.setIsActive(true);

        return userSubscriptionMapper.toDTO(userSubscriptionRepository.save(userSubscription));
    }
} 