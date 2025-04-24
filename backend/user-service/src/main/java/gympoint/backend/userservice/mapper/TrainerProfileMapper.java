package gympoint.backend.userservice.mapper;


import gympoint.backend.userservice.dto.*;
import gympoint.backend.userservice.entity.TrainerProfile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TrainerProfileMapper {

    TrainerProfile toEntity(TrainerProfileDto dto);

    TrainerProfileDto toResponse(TrainerProfile entity);

    void updateFromDto(TrainerProfileDto dto, @MappingTarget TrainerProfile entity);
}
