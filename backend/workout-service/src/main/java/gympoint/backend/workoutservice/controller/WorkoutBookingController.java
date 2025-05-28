package gympoint.backend.workoutservice.controller;

import gympoint.backend.workoutservice.dto.WorkoutBookingDto;
import gympoint.backend.workoutservice.service.WorkoutBookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workout-bookings")
@RequiredArgsConstructor
@Tag(name = "Workout Booking", description = "Workout Booking management APIs")
@SecurityRequirement(name = "bearer-key")
public class WorkoutBookingController {

    private final WorkoutBookingService workoutBookingService;

    @PostMapping
    @Operation(summary = "Create a new workout booking")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<WorkoutBookingDto> createWorkoutBooking(@Valid @RequestBody WorkoutBookingDto workoutBookingDto) {
        return new ResponseEntity<>(workoutBookingService.createWorkoutBooking(workoutBookingDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing workout booking")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<WorkoutBookingDto> updateWorkoutBooking(
            @Parameter(description = "ID of the workout booking to update") @PathVariable Long id,
            @Valid @RequestBody WorkoutBookingDto workoutBookingDto) {
        return ResponseEntity.ok(workoutBookingService.updateWorkoutBooking(id, workoutBookingDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a workout booking")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Void> deleteWorkoutBooking(
            @Parameter(description = "ID of the workout booking to delete") @PathVariable Long id) {
        workoutBookingService.deleteWorkoutBooking(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a workout booking by ID")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<WorkoutBookingDto> getWorkoutBookingById(
            @Parameter(description = "ID of the workout booking to retrieve") @PathVariable Long id) {
        return ResponseEntity.ok(workoutBookingService.getWorkoutBookingById(id));
    }

    @GetMapping("/client/{clientId}")
    @Operation(summary = "Get all workout bookings for a client")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<WorkoutBookingDto>> getWorkoutBookingsByClientId(
            @Parameter(description = "ID of the client") @PathVariable Long clientId) {
        return ResponseEntity.ok(workoutBookingService.getWorkoutBookingsByClientId(clientId));
    }

    @GetMapping("/trainer/{trainerId}")
    @Operation(summary = "Get all workout bookings for a trainer")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<WorkoutBookingDto>> getWorkoutBookingsByTrainerId(
            @Parameter(description = "ID of the trainer") @PathVariable Long trainerId) {
        return ResponseEntity.ok(workoutBookingService.getWorkoutBookingsByTrainerId(trainerId));
    }

    @GetMapping("/training-session/{trainingSessionId}")
    @Operation(summary = "Get all workout bookings for a training session")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<WorkoutBookingDto>> getWorkoutBookingsByTrainingSessionId(
            @Parameter(description = "ID of the training session") @PathVariable Long trainingSessionId) {
        return ResponseEntity.ok(workoutBookingService.getWorkoutBookingsByTrainingSessionId(trainingSessionId));
    }
} 