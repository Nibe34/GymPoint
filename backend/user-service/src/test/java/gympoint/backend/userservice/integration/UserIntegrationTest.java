package gympoint.backend.userservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import gympoint.backend.common.enums.Role;
import gympoint.backend.userservice.dto.ClientCreateDto;
import gympoint.backend.userservice.dto.UserDto;
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
class UserIntegrationTest {

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

    private ClientCreateDto userCreateDto;

    @BeforeEach
    void setUp() {
        clientRepository.deleteAll();

        userCreateDto = new ClientCreateDto();
        userCreateDto.setEmail("test@example.com");
        userCreateDto.setPassword("password123");
        userCreateDto.setFirstName("John");
        userCreateDto.setLastName("Doe");
        userCreateDto.setRole(Role.ROLE_CLIENT);
        userCreateDto.setDateOfBirth(LocalDate.of(1990, 1, 1));
        userCreateDto.setPhoneNumber("+1234567890");
        userCreateDto.setAddress("123 Main St");
        userCreateDto.setEmergencyContact("Jane Doe +1987654321");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreateUser() throws Exception {
        // when
        mockMvc.perform(post("/api/users/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(userCreateDto.getEmail()))
                .andExpect(jsonPath("$.firstName").value(userCreateDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(userCreateDto.getLastName()));

        // then
        List<Client> users = clientRepository.findAll();
        assertThat(users).hasSize(1);
        assertThat(users.get(0).getEmail()).isEqualTo(userCreateDto.getEmail());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetUserById() throws Exception {
        // given
        Client savedUser = clientRepository.save(createUser());

        // when/then
        mockMvc.perform(get("/api/users/clients/{id}", savedUser.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedUser.getId()))
                .andExpect(jsonPath("$.email").value(savedUser.getEmail()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetUserByEmail() throws Exception {
        // given
        Client savedUser = clientRepository.save(createUser());

        // when/then
        mockMvc.perform(get("/api/users/email/{email}", savedUser.getEmail()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(savedUser.getEmail()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldUpdateUser() throws Exception {
        // given
        Client savedUser = clientRepository.save(createUser());
        ClientCreateDto updateDto = new ClientCreateDto();
        updateDto.setEmail("updated@example.com");
        updateDto.setPassword("password123");
        updateDto.setFirstName("Jane");
        updateDto.setLastName("Smith");
        updateDto.setRole(Role.ROLE_CLIENT);
        updateDto.setDateOfBirth(LocalDate.of(1990, 1, 1));
        updateDto.setPhoneNumber("+1987654321");
        updateDto.setAddress("456 Oak St");
        updateDto.setEmergencyContact("John Smith +1234567890");

        // when/then
        mockMvc.perform(put("/api/users/clients/{id}", savedUser.getId())
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
    void shouldDeleteUser() throws Exception {
        // given
        Client savedUser = clientRepository.save(createUser());

        // when/then
        mockMvc.perform(delete("/api/users/clients/{id}", savedUser.getId()))
                .andExpect(status().isNoContent());

        assertThat(clientRepository.findById(savedUser.getId())).isEmpty();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetAllUsers() throws Exception {
        // given
        Client user1 = createUser();
        Client user2 = createUser();
        user2.setEmail("test2@example.com"); // Use a different email for the second user
        clientRepository.save(user1);
        clientRepository.save(user2);

        // when/then
        mockMvc.perform(get("/api/users/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    private Client createUser() {
        Client client = new Client();
        client.setEmail("test@example.com");
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