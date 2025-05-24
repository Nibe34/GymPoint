package gympoint.backend.trainerservice.service;

import gympoint.backend.trainerservice.dto.TrainingSessionDto;
import java.util.List;

public interface TrainingSessionService {
    TrainingSessionDto createSession(TrainingSessionDto sessionDto);
    TrainingSessionDto updateSession(Long id, TrainingSessionDto sessionDto);
    void deleteSession(Long id);
    TrainingSessionDto getSessionById(Long id);
    List<TrainingSessionDto> getSessionsByTrainerId(Long trainerId);
    List<TrainingSessionDto> getAvailableSessions();
    TrainingSessionDto updateAvailability(Long id, boolean isAvailable);
} 