package gympoint.backend.trainerservice.service.impl;

import gympoint.backend.trainerservice.dto.TrainingSessionDto;
import gympoint.backend.trainerservice.entity.TrainingSession;
import gympoint.backend.trainerservice.mapper.TrainingSessionMapper;
import gympoint.backend.trainerservice.repository.TrainingSessionRepository;
import gympoint.backend.trainerservice.service.TrainingSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TrainingSessionServiceImpl implements TrainingSessionService {

    private final TrainingSessionRepository trainingSessionRepository;
    private final TrainingSessionMapper trainingSessionMapper;

    @Autowired
    public TrainingSessionServiceImpl(TrainingSessionRepository trainingSessionRepository,
                                    TrainingSessionMapper trainingSessionMapper) {
        this.trainingSessionRepository = trainingSessionRepository;
        this.trainingSessionMapper = trainingSessionMapper;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_TRAINER')")
    public TrainingSessionDto createSession(TrainingSessionDto sessionDto) {
        TrainingSession session = trainingSessionMapper.toEntity(sessionDto);
        TrainingSession savedSession = trainingSessionRepository.save(session);
        return trainingSessionMapper.toDto(savedSession);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_TRAINER')")
    public TrainingSessionDto updateSession(Long id, TrainingSessionDto sessionDto) {
        TrainingSession session = trainingSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Training session not found"));
        
        session.setStartTime(sessionDto.getStartTime());
        session.setMaxParticipants(sessionDto.getMaxParticipants());
        
        TrainingSession updatedSession = trainingSessionRepository.save(session);
        return trainingSessionMapper.toDto(updatedSession);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_TRAINER')")
    public void deleteSession(Long id) {
        trainingSessionRepository.deleteById(id);
    }

    @Override
    public TrainingSessionDto getSessionById(Long id) {
        TrainingSession session = trainingSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Training session not found"));
        return trainingSessionMapper.toDto(session);
    }

    @Override
    public List<TrainingSessionDto> getSessionsByTrainerId(Long trainerId) {
        return trainingSessionRepository.findByTrainerId(trainerId)
                .stream()
                .map(trainingSessionMapper::toDto)
                .collect(Collectors.toList());
    }

} 