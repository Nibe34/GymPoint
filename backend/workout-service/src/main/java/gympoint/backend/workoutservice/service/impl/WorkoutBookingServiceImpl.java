package gympoint.backend.workoutservice.service.impl;

import gympoint.backend.workoutservice.config.JwtService;
import gympoint.backend.workoutservice.dto.WorkoutBookingDto;
import gympoint.backend.workoutservice.entity.WorkoutBooking;
import gympoint.backend.workoutservice.mapper.WorkoutBookingMapper;
import gympoint.backend.workoutservice.repository.WorkoutBookingRepository;
import gympoint.backend.workoutservice.service.WorkoutBookingService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

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
    public WorkoutBookingDto createWorkoutBooking(WorkoutBookingDto workoutBookingDto) {
        Long clientId = getCurrentClientId();
        workoutBookingDto.setClientId(clientId);
        
        WorkoutBooking workoutBooking = workoutBookingMapper.toEntity(workoutBookingDto);
        WorkoutBooking savedWorkoutBooking = workoutBookingRepository.save(workoutBooking);
        return workoutBookingMapper.toDto(savedWorkoutBooking);
    }

    @Override
    public WorkoutBookingDto updateWorkoutBooking(Long id, WorkoutBookingDto workoutBookingDto) {
        WorkoutBooking existingWorkoutBooking = workoutBookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("WorkoutBooking not found with id: " + id));

        Long clientId = getCurrentClientId();
        if (!existingWorkoutBooking.getClientId().equals(clientId)) {
            throw new AccessDeniedException("You can only update your own bookings");
        }

        workoutBookingMapper.updateEntityFromDto(workoutBookingDto, existingWorkoutBooking);
        WorkoutBooking updatedWorkoutBooking = workoutBookingRepository.save(existingWorkoutBooking);
        return workoutBookingMapper.toDto(updatedWorkoutBooking);
    }

    @Override
    public void deleteWorkoutBooking(Long id) {
        WorkoutBooking workoutBooking = workoutBookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("WorkoutBooking not found with id: " + id));

        Long clientId = getCurrentClientId();
        if (!workoutBooking.getClientId().equals(clientId)) {
            throw new AccessDeniedException("You can only delete your own bookings");
        }

        workoutBookingRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public WorkoutBookingDto getWorkoutBookingById(Long id) {
        WorkoutBooking workoutBooking = workoutBookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("WorkoutBooking not found with id: " + id));

        Long clientId = getCurrentClientId();
        if (!workoutBooking.getClientId().equals(clientId)) {
            throw new AccessDeniedException("You can only view your own bookings");
        }

        return workoutBookingMapper.toDto(workoutBooking);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkoutBookingDto> getWorkoutBookingsByClientId(Long clientId) {
        Long currentClientId = getCurrentClientId();
        if (!clientId.equals(currentClientId)) {
            throw new AccessDeniedException("You can only view your own bookings");
        }

        List<WorkoutBooking> workoutBookings = workoutBookingRepository.findByClientId(clientId);
        return workoutBookings.stream()
                .map(workoutBookingMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkoutBookingDto> getWorkoutBookingsByTrainerId(Long trainerId) {
        Long clientId = getCurrentClientId();
        List<WorkoutBooking> workoutBookings = workoutBookingRepository.findByTrainerId(trainerId);
        return workoutBookings.stream()
                .filter(booking -> booking.getClientId().equals(clientId))
                .map(workoutBookingMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkoutBookingDto> getWorkoutBookingsByTrainingSessionId(Long trainingSessionId) {
        Long clientId = getCurrentClientId();
        List<WorkoutBooking> workoutBookings = workoutBookingRepository.findByTrainingSessionId(trainingSessionId);
        return workoutBookings.stream()
                .filter(booking -> booking.getClientId().equals(clientId))
                .map(workoutBookingMapper::toDto)
                .toList();
    }
} 