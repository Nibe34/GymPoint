package gympoint.backend.userservice.service;

import gympoint.backend.userservice.dto.*;

import java.util.List;

public interface UserService {
    UserDto createUser(UserCreateDto userCreateDto);
    UserDto getUserById(Long id);
    List<UserDto> getAllUsers();
    UserDto updateUser(Long id, UserCreateDto userCreateDto);
    void deleteUser(Long id);
    UserDto getUserByEmail(String email);
    boolean existsByEmail(String email);
    UserDto createUserWithProfile(RegisterDto registerDto);
}