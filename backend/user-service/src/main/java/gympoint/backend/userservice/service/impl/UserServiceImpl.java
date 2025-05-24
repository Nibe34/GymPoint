package gympoint.backend.userservice.service.impl;

import gympoint.backend.userservice.dto.*;
import gympoint.backend.userservice.entity.Admin;
import gympoint.backend.userservice.entity.Client;
import gympoint.backend.userservice.entity.RefreshToken;
import gympoint.backend.userservice.entity.Trainer;
import gympoint.backend.userservice.entity.User;
import gympoint.backend.userservice.mapper.UserMapper;
import gympoint.backend.userservice.repository.RefreshTokenRepository;
import gympoint.backend.userservice.repository.UserRepository;
import gympoint.backend.userservice.security.JwtTokenProvider;
import gympoint.backend.userservice.service.RefreshTokenService;
import gympoint.backend.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final ResponseCookie.ResponseCookieBuilder accessTokenCookieBuilder;
    private final ResponseCookie.ResponseCookieBuilder refreshTokenCookieBuilder;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider, RefreshTokenService refreshTokenService,
                           AuthenticationManager authenticationManager,
                           ResponseCookie.ResponseCookieBuilder accessTokenCookieBuilder,
                           ResponseCookie.ResponseCookieBuilder refreshTokenCookieBuilder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenService = refreshTokenService;
        this.authenticationManager = authenticationManager;
        this.accessTokenCookieBuilder = accessTokenCookieBuilder;
        this.refreshTokenCookieBuilder = refreshTokenCookieBuilder;
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
    public AuthResponseDto createUserWithProfile(RegisterDto registerDto) {
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

        // Create authentication for the newly registered user
        org.springframework.security.core.userdetails.User userDetails = 
            new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new org.springframework.security.core.authority.SimpleGrantedAuthority(user.getRole().name()))
            );
        
        // Create authentication token
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        
        // Generate tokens
        String accessToken = jwtTokenProvider.generateToken(authentication);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        
        // Create cookies
        ResponseCookie accessTokenCookie = accessTokenCookieBuilder.value(accessToken).build();
        ResponseCookie refreshTokenCookie = refreshTokenCookieBuilder.value(refreshToken.getToken()).build();
        
        return new AuthResponseDto(accessTokenCookie.toString(), refreshTokenCookie.toString(), user.getId());
    }

    private void setBaseUserFields(User user, RegisterDto registerDto) {
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setRole(registerDto.getRole());
    }

    @Override
    public AuthResponseDto authenticateUser(LoginDto loginDto) {
        logger.debug("Starting authentication process for user: {}", loginDto.getEmail());
        
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(),
                            loginDto.getPassword()
                    )
            );
            logger.debug("Authentication successful for user: {}", loginDto.getEmail());
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String accessToken = jwtTokenProvider.generateToken(authentication);
            logger.debug("Generated access token for user: {}", loginDto.getEmail());
            
            User user = userRepository.findByEmail(loginDto.getEmail())
                    .orElseThrow(() -> {
                        logger.error("User not found after successful authentication: {}", loginDto.getEmail());
                        return new RuntimeException("User not found with email: " + loginDto.getEmail());
                    });
            
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
            logger.debug("Generated refresh token for user: {}", loginDto.getEmail());
            
            // Create cookies
            ResponseCookie accessTokenCookie = accessTokenCookieBuilder.value(accessToken).build();
            ResponseCookie refreshTokenCookie = refreshTokenCookieBuilder.value(refreshToken.getToken()).build();
            
            return new AuthResponseDto(accessTokenCookie.toString(), refreshTokenCookie.toString(), user.getId());
        } catch (AuthenticationException e) {
            logger.error("Authentication failed for user: {}", loginDto.getEmail(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error during authentication for user: {}", loginDto.getEmail(), e);
            throw new RuntimeException("Authentication failed", e);
        }
    }

    @Override
    public AuthResponseDto refreshToken(RefreshTokenRequestDto request, HttpServletRequest httpRequest) {
        logger.debug("Attempting to refresh token");
        
        // Get refresh token from cookies
        String refreshTokenValue = getRefreshTokenFromCookies(httpRequest);
        if (refreshTokenValue == null) {
            logger.error("No refresh token found in cookies");
            throw new IllegalArgumentException("Refresh token not found in cookies");
        }

        try {
            RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenValue)
                    .orElseThrow(() -> {
                        logger.error("Refresh token not found: {}", refreshTokenValue);
                        return new IllegalArgumentException("Invalid refresh token");
                    });

            refreshToken = refreshTokenService.verifyExpiration(refreshToken);
            User user = refreshToken.getUser();
            
            if (user == null) {
                logger.error("User not found for refresh token: {}", refreshTokenValue);
                throw new IllegalArgumentException("Invalid refresh token");
            }

            logger.debug("Generating new access token for user: {}", user.getEmail());
            
            // Create a proper UserDetails object
            org.springframework.security.core.userdetails.User userDetails = 
                new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    Collections.singletonList(new org.springframework.security.core.authority.SimpleGrantedAuthority(user.getRole().name()))
                );
            
            // Create authentication with UserDetails
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            
            String accessToken = jwtTokenProvider.generateToken(authentication);
            
            // Create new refresh token
            RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user);
            
            // Create cookies
            ResponseCookie accessTokenCookie = accessTokenCookieBuilder.value(accessToken).build();
            ResponseCookie refreshTokenCookie = refreshTokenCookieBuilder.value(newRefreshToken.getToken()).build();
            
            return new AuthResponseDto(accessTokenCookie.toString(), refreshTokenCookie.toString(), user.getId());
        } catch (IllegalArgumentException e) {
            logger.error("Invalid refresh token: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error during token refresh", e);
            throw new RuntimeException("Failed to refresh token", e);
        }
    }

    private String getRefreshTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .filter(cookie -> "refresh_token".equals(cookie.getName()))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
        }
        return null;
    }
} 