package gympoint.backend.userservice.service.impl;

import gympoint.backend.userservice.dto.ClientCreateDto;
import gympoint.backend.userservice.dto.ClientDto;
import gympoint.backend.userservice.entity.Client;
import gympoint.backend.userservice.mapper.UserMapper;
import gympoint.backend.userservice.repository.ClientRepository;
import gympoint.backend.userservice.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final UserMapper userMapper;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, UserMapper userMapper) {
        this.clientRepository = clientRepository;
        this.userMapper = userMapper;
    }

    @Override
    public ClientDto register(ClientCreateDto dto) {
        Client client = userMapper.toClient(dto);
        Client savedClient = clientRepository.save(client);
        return userMapper.toClientDto(savedClient);
    }

    @Override
    public ClientDto getById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
        return userMapper.toClientDto(client);
    }

    @Override
    public List<ClientDto> getAll() {
        return clientRepository.findAll().stream()
                .map(userMapper::toClientDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClientDto update(Long id, ClientDto dto) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
        
        Client updatedClient = userMapper.toClient(dto);
        updatedClient.setId(existingClient.getId());
        
        Client savedClient = clientRepository.save(updatedClient);
        return userMapper.toClientDto(savedClient);
    }

    @Override
    public void deleteById(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new RuntimeException("Client not found with id: " + id);
        }
        clientRepository.deleteById(id);
    }
} 