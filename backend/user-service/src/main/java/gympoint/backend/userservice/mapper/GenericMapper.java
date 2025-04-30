package gympoint.backend.userservice.mapper;

import gympoint.backend.userservice.dto.UserCreateDto;
import gympoint.backend.userservice.dto.UserDto;
import gympoint.backend.userservice.entity.User;

public interface GenericMapper<E extends User, D extends UserDto, C extends UserCreateDto> {
    D toDto(E entity);
    E toEntity(C createDto);
    E updateEntity(E existingEntity, C updateDto);
} 