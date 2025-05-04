package gympoint.backend.userservice.controller;

import gympoint.backend.userservice.dto.TrainerCreateDto;
import gympoint.backend.userservice.dto.TrainerDto;
import gympoint.backend.userservice.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainers")
public class TrainerController {

    private final TrainerService trainerService;

    @Autowired
    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping
    public ResponseEntity<TrainerDto> createTrainer(@RequestBody TrainerCreateDto trainerCreateDto) {
        TrainerDto createdTrainer = trainerService.createTrainer(trainerCreateDto);
        return new ResponseEntity<>(createdTrainer, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainerDto> getTrainerById(@PathVariable Long id) {
        TrainerDto trainer = trainerService.getTrainerById(id);
        return ResponseEntity.ok(trainer);
    }

    @GetMapping
    public ResponseEntity<List<TrainerDto>> getAllTrainers() {
        List<TrainerDto> trainers = trainerService.getAllTrainers();
        return ResponseEntity.ok(trainers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainerDto> updateTrainer(@PathVariable Long id, @RequestBody TrainerCreateDto trainerCreateDto) {
        TrainerDto updatedTrainer = trainerService.updateTrainer(id, trainerCreateDto);
        return ResponseEntity.ok(updatedTrainer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainer(@PathVariable Long id) {
        trainerService.deleteTrainer(id);
        return ResponseEntity.noContent().build();
    }
} 