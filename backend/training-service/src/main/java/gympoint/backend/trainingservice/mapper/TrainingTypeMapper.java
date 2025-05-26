package gympoint.backend.trainingservice.mapper;

import gympoint.backend.trainingservice.dto.TrainingTypeDto;
import gympoint.backend.trainingservice.entity.TrainingType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TrainingTypeMapper {
    TrainingTypeMapper INSTANCE = Mappers.getMapper(TrainingTypeMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "trainerId", source = "trainerId")
    TrainingTypeDto toDto(TrainingType trainingType);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "trainerId", source = "trainerId")
    TrainingType toEntity(TrainingTypeDto trainingTypeDto);
} 