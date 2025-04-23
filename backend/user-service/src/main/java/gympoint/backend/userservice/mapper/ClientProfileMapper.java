package gympoint.backend.userservice.mapper;


import gympoint.backend.userservice.dto.*;
import gympoint.backend.userservice.entity.ClientProfile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientProfileMapper {

    ClientProfile toEntity(ClientProfileRequest dto);

    ClientProfileResponse toResponse(ClientProfile entity);

    void updateFromDto(ClientProfileUpdateRequest dto, @MappingTarget ClientProfile entity);
}
