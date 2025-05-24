package gympoint.backend.trainerservice.service.impl;

import gympoint.backend.trainerservice.dto.TrainingTypeDto;
import gympoint.backend.trainerservice.entity.TrainingType;
import gympoint.backend.trainerservice.mapper.TrainingTypeMapper;
import gympoint.backend.trainerservice.repository.TrainingTypeRepository;
import gympoint.backend.trainerservice.service.TrainingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TrainingTypeServiceImpl implements TrainingTypeService {

    private final TrainingTypeRepository trainingTypeRepository;
    private final TrainingTypeMapper trainingTypeMapper;

    @Autowired
    public TrainingTypeServiceImpl(TrainingTypeRepository trainingTypeRepository,
                                 TrainingTypeMapper trainingTypeMapper) {
        this.trainingTypeRepository = trainingTypeRepository;
        this.trainingTypeMapper = trainingTypeMapper;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_TRAINER')")
    public TrainingTypeDto createTrainingType(TrainingTypeDto trainingTypeDto) {
        TrainingType trainingType = trainingTypeMapper.toEntity(trainingTypeDto);
        TrainingType savedTrainingType = trainingTypeRepository.save(trainingType);
        return trainingTypeMapper.toDto(savedTrainingType);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_TRAINER')")
    public TrainingTypeDto updateTrainingType(Long id, TrainingTypeDto trainingTypeDto) {
        TrainingType trainingType = trainingTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Training type not found"));
        
        trainingType.setName(trainingTypeDto.getName());
        trainingType.setDescription(trainingTypeDto.getDescription());
        trainingType.setPrice(trainingTypeDto.getPrice());
        
        TrainingType updatedTrainingType = trainingTypeRepository.save(trainingType);
        return trainingTypeMapper.toDto(updatedTrainingType);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_TRAINER')")
    public void deleteTrainingType(Long id) {
        trainingTypeRepository.deleteById(id);
    }

    @Override
    public TrainingTypeDto getTrainingTypeById(Long id) {
        TrainingType trainingType = trainingTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Training type not found"));
        return trainingTypeMapper.toDto(trainingType);
    }

    @Override
    public List<TrainingTypeDto> getTrainingTypesByTrainerId(Long trainerId) {
        return trainingTypeRepository.findByTrainerId(trainerId)
                .stream()
                .map(trainingTypeMapper::toDto)
                .collect(Collectors.toList());
    }
} 