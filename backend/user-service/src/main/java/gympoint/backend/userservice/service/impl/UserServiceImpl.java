package gympoint.backend.userservice.service.impl;

import gympoint.backend.userservice.dto.*;
import gympoint.backend.userservice.entity.Admin;
import gympoint.backend.userservice.entity.Client;
import gympoint.backend.userservice.entity.Trainer;
import gympoint.backend.userservice.entity.User;
import gympoint.backend.userservice.mapper.UserMapper;
import gympoint.backend.userservice.repository.UserRepository;
import gympoint.backend.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto createUser(UserCreateDto userCreateDto) {
        User user = userMapper.toUser(userCreateDto);
        User savedUser = userRepository.save(user);
        return userMapper.toUserDto(savedUser);
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return userMapper.toUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(Long id, UserCreateDto userCreateDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        User updatedUser = userMapper.toUser(userCreateDto);
        updatedUser.setId(existingUser.getId());
        
        User savedUser = userRepository.save(updatedUser);
        return userMapper.toUserDto(savedUser);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return userMapper.toUserDto(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public UserDto createUserWithProfile(RegisterDto registerDto) {
        if (registerDto.getRole() == null) {
            throw new IllegalArgumentException("Role is required");
        }

        User user;
        switch (registerDto.getRole()) {
            case ROLE_CLIENT -> {
                if (registerDto.getDateOfBirth() == null) {
                    throw new IllegalArgumentException("Date of birth is required for client");
                }
                Client client = new Client();
                setBaseUserFields(client, registerDto);
                client.setDateOfBirth(registerDto.getDateOfBirth());
                client.setPhoneNumber(registerDto.getPhoneNumber());
                client.setAddress(registerDto.getAddress());
                client.setEmergencyContact(registerDto.getEmergencyContact());
                user = userRepository.save(client);
            }
            case ROLE_TRAINER -> {
                Trainer trainer = new Trainer();
                setBaseUserFields(trainer, registerDto);
                trainer.setBio(registerDto.getBio());
                trainer.setSpecialization(registerDto.getSpecialization());
                trainer.setCertification(registerDto.getCertification());
                trainer.setRating(0.0); // Initial rating
                user = userRepository.save(trainer);
            }
            case ROLE_ADMIN -> {
                Admin admin = new Admin();
                setBaseUserFields(admin, registerDto);
                user = userRepository.save(admin);
            }
            default -> throw new IllegalArgumentException("Invalid role: " + registerDto.getRole());
        }

        return userMapper.toUserDto(user);
    }

    private void setBaseUserFields(User user, RegisterDto registerDto) {
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword()); // Password is already hashed by auth-service
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setRole(registerDto.getRole());
    }
} 