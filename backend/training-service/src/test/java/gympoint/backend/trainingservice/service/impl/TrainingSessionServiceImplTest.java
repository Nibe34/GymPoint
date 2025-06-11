package gympoint.backend.trainingservice.service.impl;

import gympoint.backend.trainingservice.dto.TrainingSessionDto;
import gympoint.backend.trainingservice.entity.TrainingSession;
import gympoint.backend.trainingservice.mapper.TrainingSessionMapper;
import gympoint.backend.trainingservice.repository.TrainingSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainingSessionServiceImplTest {

    @Mock
    private TrainingSessionRepository trainingSessionRepository;

    @Mock
    private TrainingSessionMapper trainingSessionMapper;

    @InjectMocks
    private TrainingSessionServiceImpl trainingSessionService;

    private TrainingSession trainingSession;
    private TrainingSessionDto trainingSessionDto;

    @BeforeEach
    void setUp() {
        trainingSession = new TrainingSession();
        trainingSession.setId(1L);
        trainingSession.setTrainerId(1L);
        trainingSession.setStartTime(LocalDateTime.now().plusHours(1));
        trainingSession.setMaxParticipants(10);

        trainingSessionDto = new TrainingSessionDto();
        trainingSessionDto.setId(1L);
        trainingSessionDto.setTrainerId(1L);
        trainingSessionDto.setStartTime(LocalDateTime.now().plusHours(1));
        trainingSessionDto.setMaxParticipants(10);
    }

    @Test
    void createSession_Success() {
        when(trainingSessionMapper.toEntity(any(TrainingSessionDto.class))).thenReturn(trainingSession);
        when(trainingSessionRepository.save(any(TrainingSession.class))).thenReturn(trainingSession);
        when(trainingSessionMapper.toDto(any(TrainingSession.class))).thenReturn(trainingSessionDto);

        TrainingSessionDto result = trainingSessionService.createSession(trainingSessionDto);

        assertNotNull(result);
        assertEquals(trainingSessionDto.getTrainerId(), result.getTrainerId());
        verify(trainingSessionRepository).save(any(TrainingSession.class));
    }

    @Test
    void updateSession_Success() {
        when(trainingSessionRepository.findById(1L)).thenReturn(Optional.of(trainingSession));
        when(trainingSessionRepository.save(any(TrainingSession.class))).thenReturn(trainingSession);
        when(trainingSessionMapper.toDto(any(TrainingSession.class))).thenReturn(trainingSessionDto);

        TrainingSessionDto result = trainingSessionService.updateSession(1L, trainingSessionDto);

        assertNotNull(result);
        assertEquals(trainingSessionDto.getTrainerId(), result.getTrainerId());
        verify(trainingSessionRepository).save(any(TrainingSession.class));
    }

    @Test
    void updateSession_NotFound() {
        when(trainingSessionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> trainingSessionService.updateSession(1L, trainingSessionDto));
    }

    @Test
    void deleteSession_Success() {
        doNothing().when(trainingSessionRepository).deleteById(1L);

        assertDoesNotThrow(() -> trainingSessionService.deleteSession(1L));
        verify(trainingSessionRepository).deleteById(1L);
    }

    @Test
    void getSessionById_Success() {
        when(trainingSessionRepository.findById(1L)).thenReturn(Optional.of(trainingSession));
        when(trainingSessionMapper.toDto(any(TrainingSession.class))).thenReturn(trainingSessionDto);

        TrainingSessionDto result = trainingSessionService.getSessionById(1L);

        assertNotNull(result);
        assertEquals(trainingSessionDto.getId(), result.getId());
    }

    @Test
    void getSessionById_NotFound() {
        when(trainingSessionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> trainingSessionService.getSessionById(1L));
    }

    @Test
    void getSessionsByTrainerId_Success() {
        List<TrainingSession> sessions = Arrays.asList(trainingSession);
        when(trainingSessionRepository.findByTrainerId(1L)).thenReturn(sessions);
        when(trainingSessionMapper.toDto(any(TrainingSession.class))).thenReturn(trainingSessionDto);

        List<TrainingSessionDto> results = trainingSessionService.getSessionsByTrainerId(1L);

        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(trainingSessionDto.getTrainerId(), results.get(0).getTrainerId());
    }
} 