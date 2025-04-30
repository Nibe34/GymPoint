package gympoint.backend.userservice.mapper;

import gympoint.backend.userservice.dto.TrainerCreateDto;
import gympoint.backend.userservice.dto.TrainerDto;
import gympoint.backend.userservice.entity.Trainer;
import org.springframework.stereotype.Component;

@Component
public class TrainerMapper implements GenericMapper<Trainer, TrainerDto, TrainerCreateDto> {
    @Override
    public TrainerDto toDto(Trainer entity) {
        TrainerDto dto = new TrainerDto();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setRole(entity.getRole());
        dto.setBio(entity.getBio());
        dto.setCertification(entity.getCertification());
        dto.setSpecialization(entity.getSpecialization());
        dto.setRating(entity.getRating());
        return dto;
    }

    @Override
    public Trainer toEntity(TrainerCreateDto createDto) {
        Trainer entity = new Trainer();
        entity.setEmail(createDto.getEmail());
        entity.setPassword(createDto.getPassword());
        entity.setFirstName(createDto.getFirstName());
        entity.setLastName(createDto.getLastName());
        entity.setRole(createDto.getRole());
        entity.setBio(createDto.getBio());
        entity.setCertification(createDto.getCertification());
        entity.setSpecialization(createDto.getSpecialization());
        entity.setRating(0.0); // Initial rating
        return entity;
    }

    @Override
    public Trainer updateEntity(Trainer existingEntity, TrainerCreateDto updateDto) {
        existingEntity.setEmail(updateDto.getEmail());
        existingEntity.setPassword(updateDto.getPassword());
        existingEntity.setFirstName(updateDto.getFirstName());
        existingEntity.setLastName(updateDto.getLastName());
        existingEntity.setRole(updateDto.getRole());
        existingEntity.setBio(updateDto.getBio());
        existingEntity.setCertification(updateDto.getCertification());
        existingEntity.setSpecialization(updateDto.getSpecialization());
        return existingEntity;
    }
} 