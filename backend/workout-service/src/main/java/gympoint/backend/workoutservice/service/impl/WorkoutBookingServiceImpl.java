package gympoint.backend.workoutservice.service.impl;

import gympoint.backend.workoutservice.config.JwtService;
import gympoint.backend.workoutservice.dto.WorkoutBookingDto;
import gympoint.backend.workoutservice.entity.WorkoutBooking;
import gympoint.backend.workoutservice.mapper.WorkoutBookingMapper;
import gympoint.backend.workoutservice.repository.WorkoutBookingRepository;
import gympoint.backend.workoutservice.service.WorkoutBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkoutBookingServiceImpl implements WorkoutBookingService {

    private final WorkoutBookingRepository workoutBookingRepository;
    private final WorkoutBookingMapper workoutBookingMapper;
    private final JwtService jwtService;
    private final HttpServletRequest request;

    private Long getCurrentClientId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("User is not authenticated");
        }

        String bearerToken = request.getHeader("Authorization");
        if (!StringUtils.hasText(bearerToken) || !bearerToken.startsWith("Bearer ")) {
            throw new AccessDeniedException("Invalid token format");
        }

        String token = bearerToken.substring(7);
        return jwtService.extractUserId(token);
    }

    @Override
    @Transactional
    public WorkoutBookingDto createWorkoutBooking(WorkoutBookingDto workoutBookingDto) {
        WorkoutBooking workoutBooking = workoutBookingMapper.toEntity(workoutBookingDto);
        WorkoutBooking savedWorkoutBooking = workoutBookingRepository.save(workoutBooking);
        return workoutBookingMapper.toDto(savedWorkoutBooking);
    }

    @Override
    @Transactional
    public WorkoutBookingDto updateWorkoutBooking(Long id, WorkoutBookingDto workoutBookingDto) {
        WorkoutBooking existingWorkoutBooking = workoutBookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Workout booking not found with id: " + id));
        
        workoutBookingMapper.updateEntityFromDto(workoutBookingDto, existingWorkoutBooking);
        WorkoutBooking updatedWorkoutBooking = workoutBookingRepository.save(existingWorkoutBooking);
        return workoutBookingMapper.toDto(updatedWorkoutBooking);
    }

    @Override
    @Transactional
    public void deleteWorkoutBooking(Long id) {
        if (!workoutBookingRepository.existsById(id)) {
            throw new EntityNotFoundException("Workout booking not found with id: " + id);
        }
        workoutBookingRepository.deleteById(id);
    }

    @Override
    public WorkoutBookingDto getWorkoutBookingById(Long id) {
        WorkoutBooking workoutBooking = workoutBookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Workout booking not found with id: " + id));
        return workoutBookingMapper.toDto(workoutBooking);
    }

    @Override
    public List<WorkoutBookingDto> getWorkoutBookingsByClientId(Long clientId) {
        return workoutBookingRepository.findByClientId(clientId).stream()
                .map(workoutBookingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkoutBookingDto> getWorkoutBookingsByTrainerId(Long trainerId) {
        return workoutBookingRepository.findByTrainerId(trainerId).stream()
                .map(workoutBookingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkoutBookingDto> getWorkoutBookingsByTrainingSessionId(Long trainingSessionId) {
        return workoutBookingRepository.findByTrainingSessionId(trainingSessionId).stream()
                .map(workoutBookingMapper::toDto)
                .collect(Collectors.toList());
    }
} 