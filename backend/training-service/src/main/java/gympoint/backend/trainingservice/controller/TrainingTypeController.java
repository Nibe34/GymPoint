package gympoint.backend.trainingservice.controller;

import gympoint.backend.trainingservice.dto.TrainingTypeDto;
import gympoint.backend.trainingservice.service.TrainingTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/training-types")
@Tag(name = "Training Type", description = "Training type management APIs")
public class TrainingTypeController {

    private final TrainingTypeService trainingTypeService;

    @Autowired
    public TrainingTypeController(TrainingTypeService trainingTypeService) {
        this.trainingTypeService = trainingTypeService;
    }

    @PostMapping
    @Operation(summary = "Create a new training type")
    public ResponseEntity<TrainingTypeDto> createTrainingType(@RequestBody TrainingTypeDto trainingTypeDto) {
        return ResponseEntity.ok(trainingTypeService.createTrainingType(trainingTypeDto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing training type")
    public ResponseEntity<TrainingTypeDto> updateTrainingType(@PathVariable Long id, @RequestBody TrainingTypeDto trainingTypeDto) {
        return ResponseEntity.ok(trainingTypeService.updateTrainingType(id, trainingTypeDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a training type")
    public ResponseEntity<Void> deleteTrainingType(@PathVariable Long id) {
        trainingTypeService.deleteTrainingType(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a training type by ID")
    public ResponseEntity<TrainingTypeDto> getTrainingTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(trainingTypeService.getTrainingTypeById(id));
    }

    @GetMapping("/trainer/{trainerId}")
    @Operation(summary = "Get all training types for a trainer")
    public ResponseEntity<List<TrainingTypeDto>> getTrainingTypesByTrainerId(@PathVariable Long trainerId) {
        return ResponseEntity.ok(trainingTypeService.getTrainingTypesByTrainerId(trainerId));
    }
} 