package gympoint.backend.userservice.service;

import gympoint.backend.userservice.dto.TrainerCreateDto;
import gympoint.backend.userservice.dto.TrainerDto;

import java.util.List;

public interface TrainerService {
    TrainerDto createTrainer(TrainerCreateDto trainerCreateDto);
    TrainerDto getTrainerById(Long id);
    List<TrainerDto> getAllTrainers();
    TrainerDto updateTrainer(Long id, TrainerCreateDto trainerCreateDto);
    void deleteTrainer(Long id);
} 