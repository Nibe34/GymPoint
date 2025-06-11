package gympoint.backend.userservice.mapper;

import gympoint.backend.userservice.dto.*;
import gympoint.backend.userservice.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @ObjectFactory
    default UserDto createUserDto(User user) {
        if (user instanceof Admin) {
            AdminDto dto = new AdminDto();
            mapCommonFields(user, dto);
            return dto;
        } else if (user instanceof Trainer) {
            TrainerDto dto = new TrainerDto();
            mapCommonFields(user, dto);
            Trainer trainer = (Trainer) user;
            dto.setBio(trainer.getBio());
            dto.setCertification(trainer.getCertification());
            dto.setSpecialization(trainer.getSpecialization());
            dto.setRating(trainer.getRating());
            return dto;
        } else if (user instanceof Client) {
            ClientDto dto = new ClientDto();
            mapCommonFields(user, dto);
            Client client = (Client) user;
            dto.setAddress(client.getAddress());
            dto.setDateOfBirth(client.getDateOfBirth());
            dto.setPhoneNumber(client.getPhoneNumber());
            dto.setEmergencyContact(client.getEmergencyContact());
            return dto;
        }
        throw new IllegalArgumentException("Unknown user type: " + user.getClass().getName());
    }

    default void mapCommonFields(User user, UserDto dto) {
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setRole(user.getRole());
    }

    @ObjectFactory
    default User createUser(UserCreateDto dto) {
        if (dto instanceof AdminCreateDto) {
            return new Admin();
        } else if (dto instanceof TrainerCreateDto) {
            return new Trainer();
        } else if (dto instanceof ClientCreateDto) {
            return new Client();
        }
        throw new IllegalArgumentException("Unknown user type: " + dto.getClass().getName());
    }

    UserDto toUserDto(User user);
    User toUser(UserCreateDto dto);

    default UserDetailsResponse toUserDetailsResponse(UserDto userDto) {
        UserDetailsResponse response = new UserDetailsResponse();
        response.setUsername(userDto.getEmail());
        response.setPassword(userDto.getPassword());
        response.setAuthorities(Collections.singletonList(userDto.getRole().name()));
        response.setFirstName(userDto.getFirstName());
        response.setLastName(userDto.getLastName());
        return response;
    }

}