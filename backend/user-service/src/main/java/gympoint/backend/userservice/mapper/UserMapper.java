package gympoint.backend.userservice.mapper;

import gympoint.backend.userservice.dto.*;
import gympoint.backend.userservice.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @ObjectFactory
    default UserDto createUserDto(User user) {
        if (user instanceof Admin) {
            return new AdminDto();
        } else if (user instanceof Trainer) {
            return new TrainerDto();
        } else if (user instanceof Client) {
            return new ClientDto();
        }
        throw new IllegalArgumentException("Unknown user type: " + user.getClass().getName());
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

    // USER
    UserDto toUserDto(User user);
    User toUser(UserCreateDto dto);

    // CLIENT
    ClientDto toClientDto(Client client);
    Client toClient(ClientCreateDto dto);
    Client toClient(ClientDto dto);

    // TRAINER
    TrainerDto toTrainerDto(Trainer trainer);
    Trainer toTrainer(TrainerCreateDto dto);

    // ADMIN
    AdminDto toAdminDto(Admin admin);
    Admin toAdmin(AdminCreateDto dto);
}