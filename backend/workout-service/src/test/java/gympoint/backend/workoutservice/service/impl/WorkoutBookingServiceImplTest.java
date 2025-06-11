package gympoint.backend.workoutservice.service.impl;

import gympoint.backend.workoutservice.config.JwtService;
import gympoint.backend.workoutservice.dto.WorkoutBookingDto;
import gympoint.backend.workoutservice.entity.WorkoutBooking;
import gympoint.backend.workoutservice.mapper.WorkoutBookingMapper;
import gympoint.backend.workoutservice.repository.WorkoutBookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkoutBookingServiceImplTest {

    @Mock
    private WorkoutBookingRepository workoutBookingRepository;

    @Mock
    private WorkoutBookingMapper workoutBookingMapper;

    @Mock
    private JwtService jwtService;

    @Mock
    private MockHttpServletRequest request;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private WorkoutBookingServiceImpl workoutBookingService;

    private WorkoutBooking workoutBooking;
    private WorkoutBookingDto workoutBookingDto;

    @BeforeEach
    void setUp() {
        workoutBooking = new WorkoutBooking();
        workoutBooking.setId(1L);
        workoutBooking.setTrainingSessionId(1L);
        workoutBooking.setClientId(1L);
        workoutBooking.setTrainerId(1L);
        workoutBooking.setCreatedAt(LocalDateTime.now());
        workoutBooking.setUpdatedAt(LocalDateTime.now());

        workoutBookingDto = new WorkoutBookingDto();
        workoutBookingDto.setId(1L);
        workoutBookingDto.setTrainingSessionId(1L);
        workoutBookingDto.setClientId(1L);
        workoutBookingDto.setTrainerId(1L);
        workoutBookingDto.setCreatedAt(LocalDateTime.now());
        workoutBookingDto.setUpdatedAt(LocalDateTime.now());

        // Setup security context with lenient stubbings
        SecurityContextHolder.setContext(securityContext);
        lenient().when(securityContext.getAuthentication()).thenReturn(authentication);
        lenient().when(authentication.isAuthenticated()).thenReturn(true);
        lenient().when(request.getHeader("Authorization")).thenReturn("Bearer test-token");
        lenient().when(jwtService.extractUserId("test-token")).thenReturn(1L);
    }

    @Test
    void createWorkoutBooking_Success() {
        when(workoutBookingMapper.toEntity(any(WorkoutBookingDto.class))).thenReturn(workoutBooking);
        when(workoutBookingRepository.save(any(WorkoutBooking.class))).thenReturn(workoutBooking);
        when(workoutBookingMapper.toDto(any(WorkoutBooking.class))).thenReturn(workoutBookingDto);

        WorkoutBookingDto result = workoutBookingService.createWorkoutBooking(workoutBookingDto);

        assertNotNull(result);
        assertEquals(workoutBookingDto.getTrainingSessionId(), result.getTrainingSessionId());
        verify(workoutBookingRepository).save(any(WorkoutBooking.class));
    }

    @Test
    void updateWorkoutBooking_Success() {
        when(workoutBookingRepository.findById(1L)).thenReturn(Optional.of(workoutBooking));
        when(workoutBookingRepository.save(any(WorkoutBooking.class))).thenReturn(workoutBooking);
        when(workoutBookingMapper.toDto(any(WorkoutBooking.class))).thenReturn(workoutBookingDto);

        WorkoutBookingDto result = workoutBookingService.updateWorkoutBooking(1L, workoutBookingDto);

        assertNotNull(result);
        assertEquals(workoutBookingDto.getTrainingSessionId(), result.getTrainingSessionId());
        verify(workoutBookingRepository).save(any(WorkoutBooking.class));
    }

    @Test
    void updateWorkoutBooking_NotFound() {
        when(workoutBookingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> workoutBookingService.updateWorkoutBooking(1L, workoutBookingDto));
    }

    @Test
    void deleteWorkoutBooking_Success() {
        when(workoutBookingRepository.existsById(1L)).thenReturn(true);
        doNothing().when(workoutBookingRepository).deleteById(1L);

        assertDoesNotThrow(() -> workoutBookingService.deleteWorkoutBooking(1L));
        verify(workoutBookingRepository).deleteById(1L);
    }

    @Test
    void deleteWorkoutBooking_NotFound() {
        when(workoutBookingRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> workoutBookingService.deleteWorkoutBooking(1L));
    }

    @Test
    void getWorkoutBookingById_Success() {
        when(workoutBookingRepository.findById(1L)).thenReturn(Optional.of(workoutBooking));
        when(workoutBookingMapper.toDto(any(WorkoutBooking.class))).thenReturn(workoutBookingDto);

        WorkoutBookingDto result = workoutBookingService.getWorkoutBookingById(1L);

        assertNotNull(result);
        assertEquals(workoutBookingDto.getId(), result.getId());
    }

    @Test
    void getWorkoutBookingById_NotFound() {
        when(workoutBookingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> workoutBookingService.getWorkoutBookingById(1L));
    }

    @Test
    void getWorkoutBookingsByClientId_Success() {
        List<WorkoutBooking> bookings = Arrays.asList(workoutBooking);
        when(workoutBookingRepository.findByClientId(1L)).thenReturn(bookings);
        when(workoutBookingMapper.toDto(any(WorkoutBooking.class))).thenReturn(workoutBookingDto);

        List<WorkoutBookingDto> results = workoutBookingService.getWorkoutBookingsByClientId(1L);

        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(workoutBookingDto.getClientId(), results.get(0).getClientId());
    }

    @Test
    void getWorkoutBookingsByTrainerId_Success() {
        List<WorkoutBooking> bookings = Arrays.asList(workoutBooking);
        when(workoutBookingRepository.findByTrainerId(1L)).thenReturn(bookings);
        when(workoutBookingMapper.toDto(any(WorkoutBooking.class))).thenReturn(workoutBookingDto);

        List<WorkoutBookingDto> results = workoutBookingService.getWorkoutBookingsByTrainerId(1L);

        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(workoutBookingDto.getTrainerId(), results.get(0).getTrainerId());
    }

    @Test
    void getWorkoutBookingsByTrainingSessionId_Success() {
        List<WorkoutBooking> bookings = Arrays.asList(workoutBooking);
        when(workoutBookingRepository.findByTrainingSessionId(1L)).thenReturn(bookings);
        when(workoutBookingMapper.toDto(any(WorkoutBooking.class))).thenReturn(workoutBookingDto);

        List<WorkoutBookingDto> results = workoutBookingService.getWorkoutBookingsByTrainingSessionId(1L);

        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(workoutBookingDto.getTrainingSessionId(), results.get(0).getTrainingSessionId());
    }
} 