package gympoint.backend.subscriptionservice.mapper;

import gympoint.backend.subscriptionservice.dto.UserSubscriptionDTO;
import gympoint.backend.subscriptionservice.entity.UserSubscription;
import org.springframework.stereotype.Component;

@Component
public class UserSubscriptionMapper {
    private final SubscriptionMapper subscriptionMapper;

    public UserSubscriptionMapper(SubscriptionMapper subscriptionMapper) {
        this.subscriptionMapper = subscriptionMapper;
    }

    public UserSubscriptionDTO toDTO(UserSubscription entity) {
        if (entity == null) {
            return null;
        }

        UserSubscriptionDTO dto = new UserSubscriptionDTO();
        dto.setId(entity.getId());
        dto.setUserEmail(entity.getUserEmail());
        dto.setSubscription(subscriptionMapper.toDTO(entity.getSubscription()));
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setIsActive(entity.getIsActive());
        return dto;
    }
} 