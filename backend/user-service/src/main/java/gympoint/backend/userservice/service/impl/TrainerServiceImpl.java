package gympoint.backend.userservice.service.impl;

import gympoint.backend.userservice.dto.TrainerCreateDto;
import gympoint.backend.userservice.dto.TrainerDto;
import gympoint.backend.userservice.entity.Trainer;
import gympoint.backend.userservice.mapper.TrainerMapper;
import gympoint.backend.userservice.repository.TrainerRepository;
import gympoint.backend.userservice.service.AbstractUserService;
import gympoint.backend.userservice.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TrainerServiceImpl extends AbstractUserService<Trainer, TrainerDto, TrainerCreateDto, TrainerRepository> implements TrainerService {

    @Autowired
    public TrainerServiceImpl(TrainerRepository trainerRepository, TrainerMapper trainerMapper) {
        super(trainerRepository, trainerMapper);
    }

    @Override
    public TrainerDto createTrainer(TrainerCreateDto trainerCreateDto) {
        return createUser(trainerCreateDto);
    }

    @Override
    public TrainerDto getTrainerById(Long id) {
        return getUserById(id);
    }

    @Override
    public List<TrainerDto> getAllTrainers() {
        return getAllUsers();
    }

    @Override
    public TrainerDto updateTrainer(Long id, TrainerCreateDto trainerCreateDto) {
        Trainer existingTrainer = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trainer not found with id: " + id));
        
        trainerCreateDto.setPassword(existingTrainer.getPassword()); // Preserve existing password
        return updateUser(id, trainerCreateDto);
    }

    @Override
    public void deleteTrainer(Long id) {
        deleteUser(id);
    }
} 