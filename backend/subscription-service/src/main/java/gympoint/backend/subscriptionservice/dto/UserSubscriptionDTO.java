package gympoint.backend.subscriptionservice.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserSubscriptionDTO {
    private Long id;
    private String userEmail;
    private SubscriptionDTO subscription;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isActive;
} 