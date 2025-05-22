package gympoint.backend.userservice.service;

import gympoint.backend.userservice.dto.AuthResponseDto;
import gympoint.backend.userservice.dto.LoginDto;
import gympoint.backend.userservice.dto.RegisterDto;
import gympoint.backend.userservice.dto.RefreshTokenRequestDto;
import gympoint.backend.userservice.dto.UserCreateDto;
import gympoint.backend.userservice.dto.UserDto;
import gympoint.backend.userservice.entity.User;

import java.util.List;

public interface UserService {
    UserDto createUser(UserCreateDto userCreateDto);
    AuthResponseDto createUserWithProfile(RegisterDto registerDto);
    UserDto getUserById(Long id);
    List<UserDto> getAllUsers();
    UserDto updateUser(Long id, UserCreateDto userCreateDto);
    void deleteUser(Long id);
    UserDto getUserByEmail(String email);
    boolean existsByEmail(String email);
    AuthResponseDto authenticateUser(LoginDto loginDto);
    AuthResponseDto refreshToken(RefreshTokenRequestDto request);
    UserDto getCurrentUser();
} 