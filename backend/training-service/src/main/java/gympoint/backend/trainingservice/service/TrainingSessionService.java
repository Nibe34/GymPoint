package gympoint.backend.trainingservice.service;

import gympoint.backend.trainingservice.dto.TrainingSessionDto;
import java.util.List;

public interface TrainingSessionService {
    TrainingSessionDto createSession(TrainingSessionDto sessionDto);
    TrainingSessionDto updateSession(Long id, TrainingSessionDto sessionDto);
    void deleteSession(Long id);
    TrainingSessionDto getSessionById(Long id);
    List<TrainingSessionDto> getSessionsByTrainerId(Long trainerId);
} 