package gympoint.backend.userservice.service;


import gympoint.backend.userservice.dto.ClientCreateDto;
import gympoint.backend.userservice.dto.ClientDto;

import java.util.List;

public interface ClientService {
    ClientDto register(ClientCreateDto dto);
    ClientDto getById(Long id);
    List<ClientDto> getAll();
    ClientDto update(Long id, ClientDto dto);
    void deleteById(Long id);
}
