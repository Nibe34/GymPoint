package gympoint.backend.subscriptionservice.mapper;

import gympoint.backend.subscriptionservice.dto.SubscriptionDTO;
import gympoint.backend.subscriptionservice.entity.Subscription;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionMapper {
    
    public SubscriptionDTO toDTO(Subscription entity) {
        if (entity == null) {
            return null;
        }
        
        SubscriptionDTO dto = new SubscriptionDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setDurationDays(entity.getDurationDays());
        dto.setVisitsNumber(entity.getVisitsNumber());
        return dto;
    }

    public Subscription toEntity(SubscriptionDTO dto) {
        if (dto == null) {
            return null;
        }

        Subscription entity = new Subscription();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setDurationDays(dto.getDurationDays());
        entity.setVisitsNumber(dto.getVisitsNumber());
        return entity;
    }
} 