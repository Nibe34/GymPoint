package gympoint.backend.trainerservice.service;

import gympoint.backend.trainerservice.dto.TrainingTypeDto;
import java.util.List;

public interface TrainingTypeService {
    TrainingTypeDto createTrainingType(TrainingTypeDto trainingTypeDto);
    TrainingTypeDto updateTrainingType(Long id, TrainingTypeDto trainingTypeDto);
    void deleteTrainingType(Long id);
    TrainingTypeDto getTrainingTypeById(Long id);
    List<TrainingTypeDto> getTrainingTypesByTrainerId(Long trainerId);
} 