package gympoint.backend.userservice.mapper;


import gympoint.backend.userservice.dto.*;
import gympoint.backend.userservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(
        componentModel = "spring",
        uses = {TrainerProfileMapper.class, ClientProfileMapper.class}
)
public interface UserMapper {

    User toEntity(UserRegistrationRequest dto);

    UserResponse toResponse(User entity);

    void updateFromDto(UserUpdateRequest dto, @MappingTarget User entity);
}
