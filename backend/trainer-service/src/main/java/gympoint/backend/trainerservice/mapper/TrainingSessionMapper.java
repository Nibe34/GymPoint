package gympoint.backend.trainerservice.mapper;

import gympoint.backend.trainerservice.dto.TrainingSessionDto;
import gympoint.backend.trainerservice.entity.TrainingSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TrainingSessionMapper {
    TrainingSessionMapper INSTANCE = Mappers.getMapper(TrainingSessionMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "trainerId", source = "trainerId")
    @Mapping(target = "startTime", source = "startTime")
    @Mapping(target = "maxParticipants", source = "maxParticipants")
    TrainingSessionDto toDto(TrainingSession trainingSession);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "trainerId", source = "trainerId")
    @Mapping(target = "startTime", source = "startTime")
    @Mapping(target = "maxParticipants", source = "maxParticipants")
    TrainingSession toEntity(TrainingSessionDto trainingSessionDto);
} 