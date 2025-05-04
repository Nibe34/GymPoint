package gympoint.backend.userservice.service;

import gympoint.backend.userservice.dto.UserCreateDto;
import gympoint.backend.userservice.dto.UserDto;
import gympoint.backend.userservice.entity.User;
import gympoint.backend.userservice.mapper.GenericMapper;
import gympoint.backend.userservice.repository.BaseUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public abstract class AbstractUserService<E extends User, D extends UserDto, C extends UserCreateDto, R extends BaseUserRepository<E>> {
    protected final R repository;
    protected final GenericMapper<E, D, C> mapper;

    protected AbstractUserService(R repository, GenericMapper<E, D, C> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public D createUser(C createDto) {
        E entity = mapper.toEntity(createDto);
        E savedEntity = repository.save(entity);
        return mapper.toDto(savedEntity);
    }

    public D getUserById(Long id) {
        E entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return mapper.toDto(entity);
    }

    public List<D> getAllUsers() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public D updateUser(Long id, C updateDto) {
        E existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        E updatedEntity = mapper.updateEntity(existingEntity, updateDto);
        E savedEntity = repository.save(updatedEntity);
        return mapper.toDto(savedEntity);
    }

    public void deleteUser(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        repository.deleteById(id);
    }

    public D getUserByEmail(String email) {
        E entity = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return mapper.toDto(entity);
    }

    public boolean existsByEmail(String email) {
        return repository.findByEmail(email).isPresent();
    }
} 