package gympoint.backend.userservice.service;

import gympoint.backend.userservice.dto.UserCreateDto;
import gympoint.backend.userservice.dto.UserDto;
import gympoint.backend.userservice.entity.User;

import java.util.List;

public interface UserService {
    UserDto createUser(UserCreateDto userCreateDto);
    UserDto getUserById(Long id);
    List<UserDto> getAllUsers();
    UserDto updateUser(Long id, UserCreateDto userCreateDto);
    void deleteUser(Long id);
    UserDto getUserByEmail(String email);
    boolean existsByEmail(String email);
} 