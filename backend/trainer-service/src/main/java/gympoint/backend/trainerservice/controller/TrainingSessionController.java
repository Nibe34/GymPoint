package gympoint.backend.trainerservice.controller;

import gympoint.backend.trainerservice.dto.TrainingSessionDto;
import gympoint.backend.trainerservice.service.TrainingSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/training-sessions")
@Tag(name = "Training Session", description = "Training session management APIs")
public class TrainingSessionController {

    private final TrainingSessionService trainingSessionService;

    @Autowired
    public TrainingSessionController(TrainingSessionService trainingSessionService) {
        this.trainingSessionService = trainingSessionService;
    }

    @PostMapping
    @Operation(summary = "Create a new training session")
    public ResponseEntity<TrainingSessionDto> createSession(@RequestBody TrainingSessionDto sessionDto) {
        return ResponseEntity.ok(trainingSessionService.createSession(sessionDto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing training session")
    public ResponseEntity<TrainingSessionDto> updateSession(@PathVariable Long id, @RequestBody TrainingSessionDto sessionDto) {
        return ResponseEntity.ok(trainingSessionService.updateSession(id, sessionDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a training session")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        trainingSessionService.deleteSession(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a training session by ID")
    public ResponseEntity<TrainingSessionDto> getSessionById(@PathVariable Long id) {
        return ResponseEntity.ok(trainingSessionService.getSessionById(id));
    }

    @GetMapping("/trainer/{trainerId}")
    @Operation(summary = "Get all training sessions for a trainer")
    public ResponseEntity<List<TrainingSessionDto>> getSessionsByTrainerId(@PathVariable Long trainerId) {
        return ResponseEntity.ok(trainingSessionService.getSessionsByTrainerId(trainerId));
    }

    @GetMapping("/available")
    @Operation(summary = "Get all available training sessions")
    public ResponseEntity<List<TrainingSessionDto>> getAvailableSessions() {
        return ResponseEntity.ok(trainingSessionService.getAvailableSessions());
    }

    @PatchMapping("/{id}/availability")
    @Operation(summary = "Update training session availability")
    public ResponseEntity<TrainingSessionDto> updateAvailability(@PathVariable Long id, @RequestParam boolean isAvailable) {
        return ResponseEntity.ok(trainingSessionService.updateAvailability(id, isAvailable));
    }
}

