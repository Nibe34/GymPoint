package gympoint.backend.userservice.service.impl;

import gympoint.backend.userservice.dto.ClientCreateDto;
import gympoint.backend.userservice.dto.ClientDto;
import gympoint.backend.userservice.entity.Client;
import gympoint.backend.userservice.mapper.ClientMapper;
import gympoint.backend.userservice.repository.ClientRepository;
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
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    private Client client;
    private ClientDto clientDto;
    private ClientCreateDto clientCreateDto;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setId(1L);
        client.setEmail("client@example.com");
        client.setFirstName("John");
        client.setLastName("Doe");

        clientDto = new ClientDto();
        clientDto.setId(1L);
        clientDto.setEmail("client@example.com");
        clientDto.setFirstName("John");
        clientDto.setLastName("Doe");

        clientCreateDto = new ClientCreateDto();
        clientCreateDto.setEmail("client@example.com");
        clientCreateDto.setFirstName("John");
        clientCreateDto.setLastName("Doe");
    }

    @Test
    void register_Success() {
        when(clientMapper.toEntity(any(ClientCreateDto.class))).thenReturn(client);
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        when(clientMapper.toDto(any(Client.class))).thenReturn(clientDto);

        ClientDto result = clientService.register(clientCreateDto);

        assertNotNull(result);
        assertEquals(clientDto.getEmail(), result.getEmail());
        verify(clientRepository).save(any(Client.class));
    }

    @Test
    void getById_Success() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientMapper.toDto(any(Client.class))).thenReturn(clientDto);

        ClientDto result = clientService.getById(1L);

        assertNotNull(result);
        assertEquals(clientDto.getId(), result.getId());
    }

    @Test
    void getById_NotFound() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> clientService.getById(1L));
    }

    @Test
    void getAll_Success() {
        List<Client> clients = Arrays.asList(client);
        when(clientRepository.findAll()).thenReturn(clients);
        when(clientMapper.toDto(any(Client.class))).thenReturn(clientDto);

        List<ClientDto> results = clientService.getAll();

        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(clientDto.getEmail(), results.get(0).getEmail());
    }

    @Test
    void update_Success() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientMapper.updateEntity(any(Client.class), any(ClientCreateDto.class))).thenReturn(client);
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        when(clientMapper.toDto(any(Client.class))).thenReturn(clientDto);

        ClientDto result = clientService.update(1L, clientDto);

        assertNotNull(result);
        assertEquals(clientDto.getEmail(), result.getEmail());
        verify(clientRepository).save(any(Client.class));
    }

    @Test
    void deleteById_Success() {
        when(clientRepository.existsById(1L)).thenReturn(true);
        doNothing().when(clientRepository).deleteById(1L);

        assertDoesNotThrow(() -> clientService.deleteById(1L));
        verify(clientRepository).deleteById(1L);
    }

    @Test
    void deleteById_NotFound() {
        when(clientRepository.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> clientService.deleteById(1L));
    }
} 