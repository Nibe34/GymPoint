package gympoint.backend.trainingservice.service.impl;

import gympoint.backend.trainingservice.dto.TrainingTypeDto;
import gympoint.backend.trainingservice.entity.TrainingType;
import gympoint.backend.trainingservice.mapper.TrainingTypeMapper;
import gympoint.backend.trainingservice.repository.TrainingTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainingTypeServiceImplTest {

    @Mock
    private TrainingTypeRepository trainingTypeRepository;

    @Mock
    private TrainingTypeMapper trainingTypeMapper;

    @InjectMocks
    private TrainingTypeServiceImpl trainingTypeService;

    private TrainingType trainingType;
    private TrainingTypeDto trainingTypeDto;

    @BeforeEach
    void setUp() {
        trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setName("Yoga");
        trainingType.setDescription("Yoga training session");
        trainingType.setPrice(50.0);
        trainingType.setTrainerId(1L);

        trainingTypeDto = new TrainingTypeDto();
        trainingTypeDto.setId(1L);
        trainingTypeDto.setName("Yoga");
        trainingTypeDto.setDescription("Yoga training session");
        trainingTypeDto.setPrice(50.0);
        trainingTypeDto.setTrainerId(1L);
    }

    @Test
    void createTrainingType_Success() {
        when(trainingTypeMapper.toEntity(any())).thenReturn(trainingType);
        when(trainingTypeRepository.save(any())).thenReturn(trainingType);
        when(trainingTypeMapper.toDto(any())).thenReturn(trainingTypeDto);

        TrainingTypeDto result = trainingTypeService.createTrainingType(trainingTypeDto);

        assertNotNull(result);
        assertEquals(trainingTypeDto, result);
        verify(trainingTypeRepository).save(any());
    }

    @Test
    void updateTrainingType_Success() {
        when(trainingTypeRepository.findById(any())).thenReturn(Optional.of(trainingType));
        when(trainingTypeRepository.save(any())).thenReturn(trainingType);
        when(trainingTypeMapper.toDto(any())).thenReturn(trainingTypeDto);

        TrainingTypeDto result = trainingTypeService.updateTrainingType(1L, trainingTypeDto);

        assertNotNull(result);
        assertEquals(trainingTypeDto, result);
        verify(trainingTypeRepository).save(any());
    }

    @Test
    void updateTrainingType_NotFound() {
        when(trainingTypeRepository.findById(any())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> trainingTypeService.updateTrainingType(1L, trainingTypeDto));
        assertEquals("Training type not found", exception.getMessage());
    }

    @Test
    void deleteTrainingType_Success() {
        doNothing().when(trainingTypeRepository).deleteById(any());

        assertDoesNotThrow(() -> trainingTypeService.deleteTrainingType(1L));
        verify(trainingTypeRepository).deleteById(1L);
    }

    @Test
    void getTrainingTypeById_Success() {
        when(trainingTypeRepository.findById(any())).thenReturn(Optional.of(trainingType));
        when(trainingTypeMapper.toDto(any())).thenReturn(trainingTypeDto);

        TrainingTypeDto result = trainingTypeService.getTrainingTypeById(1L);

        assertNotNull(result);
        assertEquals(trainingTypeDto, result);
    }

    @Test
    void getTrainingTypeById_NotFound() {
        when(trainingTypeRepository.findById(any())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> trainingTypeService.getTrainingTypeById(1L));
        assertEquals("Training type not found", exception.getMessage());
    }

    @Test
    void getTrainingTypesByTrainerId_Success() {
        List<TrainingType> trainingTypes = Arrays.asList(trainingType);
        when(trainingTypeRepository.findByTrainerId(any())).thenReturn(trainingTypes);
        when(trainingTypeMapper.toDto(any())).thenReturn(trainingTypeDto);

        List<TrainingTypeDto> result = trainingTypeService.getTrainingTypesByTrainerId(1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(trainingTypeDto, result.get(0));
    }
} 