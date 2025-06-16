package gympoint.backend.userservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import gympoint.backend.common.enums.Role;
import gympoint.backend.userservice.dto.ClientCreateDto;
import gympoint.backend.userservice.dto.ClientDto;
import gympoint.backend.userservice.entity.Client;
import gympoint.backend.userservice.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
class ClientIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClientRepository clientRepository;

    private ClientCreateDto clientCreateDto;

    @BeforeEach
    void setUp() {
        clientRepository.deleteAll();

        clientCreateDto = new ClientCreateDto();
        clientCreateDto.setEmail("client@example.com");
        clientCreateDto.setPassword("password123");
        clientCreateDto.setFirstName("John");
        clientCreateDto.setLastName("Doe");
        clientCreateDto.setRole(Role.ROLE_CLIENT);
        clientCreateDto.setDateOfBirth(LocalDate.of(1990, 1, 1));
        clientCreateDto.setPhoneNumber("+1234567890");
        clientCreateDto.setAddress("123 Main St");
        clientCreateDto.setEmergencyContact("Jane Doe +1987654321");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreateClient() throws Exception {
        // when
        mockMvc.perform(post("/api/users/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(clientCreateDto.getEmail()))
                .andExpect(jsonPath("$.firstName").value(clientCreateDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(clientCreateDto.getLastName()))
                .andExpect(jsonPath("$.dateOfBirth").value(clientCreateDto.getDateOfBirth().toString()))
                .andExpect(jsonPath("$.phoneNumber").value(clientCreateDto.getPhoneNumber()))
                .andExpect(jsonPath("$.address").value(clientCreateDto.getAddress()))
                .andExpect(jsonPath("$.emergencyContact").value(clientCreateDto.getEmergencyContact()));

        // then
        List<Client> clients = clientRepository.findAll();
        assertThat(clients).hasSize(1);
        assertThat(clients.get(0).getEmail()).isEqualTo(clientCreateDto.getEmail());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetClientById() throws Exception {
        // given
        Client savedClient = clientRepository.save(createClient());

        // when/then
        mockMvc.perform(get("/api/users/clients/{id}", savedClient.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedClient.getId()))
                .andExpect(jsonPath("$.email").value(savedClient.getEmail()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldUpdateClient() throws Exception {
        // given
        Client savedClient = clientRepository.save(createClient());
        ClientDto updateDto = new ClientDto();
        updateDto.setEmail("updated@example.com");
        updateDto.setFirstName("Jane");
        updateDto.setLastName("Smith");
        updateDto.setPhoneNumber("+1987654321");
        updateDto.setAddress("456 Oak St");
        updateDto.setEmergencyContact("John Smith +1234567890");

        // when/then
        mockMvc.perform(put("/api/users/clients/{id}", savedClient.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("updated@example.com"))
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.lastName").value("Smith"))
                .andExpect(jsonPath("$.phoneNumber").value("+1987654321"))
                .andExpect(jsonPath("$.address").value("456 Oak St"))
                .andExpect(jsonPath("$.emergencyContact").value("John Smith +1234567890"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteClient() throws Exception {
        // given
        Client savedClient = clientRepository.save(createClient());

        // when/then
        mockMvc.perform(delete("/api/users/clients/{id}", savedClient.getId()))
                .andExpect(status().isNoContent());

        assertThat(clientRepository.findById(savedClient.getId())).isEmpty();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetAllClients() throws Exception {
        // given
        Client client1 = createClient();
        client1.setEmail("client1@example.com");
        clientRepository.save(client1);

        Client client2 = createClient();
        client2.setEmail("client2@example.com");
        clientRepository.save(client2);

        // when/then
        mockMvc.perform(get("/api/users/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    private Client createClient() {
        Client client = new Client();
        client.setEmail("client@example.com");
        client.setPassword("password123");
        client.setFirstName("John");
        client.setLastName("Doe");
        client.setRole(Role.ROLE_CLIENT);
        client.setDateOfBirth(LocalDate.of(1990, 1, 1));
        client.setPhoneNumber("+1234567890");
        client.setAddress("123 Main St");
        client.setEmergencyContact("Jane Doe +1987654321");
        return client;
    }
} 