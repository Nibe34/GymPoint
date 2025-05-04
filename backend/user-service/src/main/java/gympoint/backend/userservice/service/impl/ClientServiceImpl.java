package gympoint.backend.userservice.service.impl;

import gympoint.backend.userservice.dto.ClientCreateDto;
import gympoint.backend.userservice.dto.ClientDto;
import gympoint.backend.userservice.entity.Client;
import gympoint.backend.userservice.mapper.ClientMapper;
import gympoint.backend.userservice.repository.ClientRepository;
import gympoint.backend.userservice.service.AbstractUserService;
import gympoint.backend.userservice.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClientServiceImpl extends AbstractUserService<Client, ClientDto, ClientCreateDto, ClientRepository> implements ClientService {

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
        super(clientRepository, clientMapper);
    }

    @Override
    public ClientDto register(ClientCreateDto dto) {
        return createUser(dto);
    }

    @Override
    public ClientDto getById(Long id) {
        return getUserById(id);
    }

    @Override
    public List<ClientDto> getAll() {
        return getAllUsers();
    }

    @Override
    public ClientDto update(Long id, ClientDto dto) {
        Client existingClient = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + id));

        ClientCreateDto createDto = new ClientCreateDto();
        createDto.setEmail(dto.getEmail());
        createDto.setPassword(existingClient.getPassword());
        createDto.setFirstName(dto.getFirstName());
        createDto.setLastName(dto.getLastName());
        createDto.setRole(dto.getRole());
        createDto.setAddress(dto.getAddress());
        createDto.setDateOfBirth(dto.getDateOfBirth());
        createDto.setPhoneNumber(dto.getPhoneNumber());
        createDto.setEmergencyContact(dto.getEmergencyContact());
        return updateUser(id, createDto);
    }

    @Override
    public void deleteById(Long id) {
        deleteUser(id);
    }
} 