package gympoint.backend.userservice.service.impl;

import gympoint.backend.userservice.dto.TrainerCreateDto;
import gympoint.backend.userservice.dto.TrainerDto;
import gympoint.backend.userservice.entity.Trainer;
import gympoint.backend.userservice.mapper.UserMapper;
import gympoint.backend.userservice.repository.TrainerRepository;
import gympoint.backend.userservice.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;
    private final UserMapper userMapper;

    @Autowired
    public TrainerServiceImpl(TrainerRepository trainerRepository, UserMapper userMapper) {
        this.trainerRepository = trainerRepository;
        this.userMapper = userMapper;
    }

    @Override
    public TrainerDto createTrainer(TrainerCreateDto trainerCreateDto) {
        Trainer trainer = userMapper.toTrainer(trainerCreateDto);
        Trainer savedTrainer = trainerRepository.save(trainer);
        return userMapper.toTrainerDto(savedTrainer);
    }

    @Override
    public TrainerDto getTrainerById(Long id) {
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trainer not found with id: " + id));
        return userMapper.toTrainerDto(trainer);
    }

    @Override
    public List<TrainerDto> getAllTrainers() {
        return trainerRepository.findAll().stream()
                .map(userMapper::toTrainerDto)
                .collect(Collectors.toList());
    }

    @Override
    public TrainerDto updateTrainer(Long id, TrainerCreateDto trainerCreateDto) {
        Trainer existingTrainer = trainerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trainer not found with id: " + id));
        
        Trainer updatedTrainer = userMapper.toTrainer(trainerCreateDto);
        updatedTrainer.setId(existingTrainer.getId());
        
        Trainer savedTrainer = trainerRepository.save(updatedTrainer);
        return userMapper.toTrainerDto(savedTrainer);
    }

    @Override
    public void deleteTrainer(Long id) {
        if (!trainerRepository.existsById(id)) {
            throw new RuntimeException("Trainer not found with id: " + id);
        }
        trainerRepository.deleteById(id);
    }
} 