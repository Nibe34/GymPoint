package gympoint.backend.userservice.mapper;


import gympoint.backend.userservice.dto.*;
import gympoint.backend.userservice.entity.ClientProfile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientProfileMapper {

    ClientProfile toEntity(ClientProfileDto dto);

    ClientProfileDto toResponse(ClientProfile entity);

    void updateFromDto(ClientProfileDto dto, @MappingTarget ClientProfile entity);
}
