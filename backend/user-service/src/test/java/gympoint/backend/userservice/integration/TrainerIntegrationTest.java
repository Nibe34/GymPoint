package gympoint.backend.userservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import gympoint.backend.common.enums.Role;
import gympoint.backend.userservice.dto.TrainerCreateDto;
import gympoint.backend.userservice.dto.TrainerDto;
import gympoint.backend.userservice.entity.Trainer;
import gympoint.backend.userservice.repository.TrainerRepository;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
class TrainerIntegrationTest {

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
    private TrainerRepository trainerRepository;

    private TrainerCreateDto trainerCreateDto;

    @BeforeEach
    void setUp() {
        trainerRepository.deleteAll();

        trainerCreateDto = new TrainerCreateDto();
        trainerCreateDto.setEmail("trainer@example.com");
        trainerCreateDto.setPassword("password123");
        trainerCreateDto.setFirstName("John");
        trainerCreateDto.setLastName("Doe");
        trainerCreateDto.setRole(Role.ROLE_TRAINER);
        trainerCreateDto.setSpecialization("Fitness");
        trainerCreateDto.setBio("Experienced fitness trainer");
        trainerCreateDto.setCertification("ACE Certified");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreateTrainer() throws Exception {
        // when
        mockMvc.perform(post("/api/users/trainers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(trainerCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(trainerCreateDto.getEmail()))
                .andExpect(jsonPath("$.firstName").value(trainerCreateDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(trainerCreateDto.getLastName()))
                .andExpect(jsonPath("$.specialization").value(trainerCreateDto.getSpecialization()))
                .andExpect(jsonPath("$.bio").value(trainerCreateDto.getBio()))
                .andExpect(jsonPath("$.certification").value(trainerCreateDto.getCertification()));

        // then
        List<Trainer> trainers = trainerRepository.findAll();
        assertThat(trainers).hasSize(1);
        assertThat(trainers.get(0).getEmail()).isEqualTo(trainerCreateDto.getEmail());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetTrainerById() throws Exception {
        // given
        Trainer savedTrainer = trainerRepository.save(createTrainer());

        // when/then
        mockMvc.perform(get("/api/users/trainers/{id}", savedTrainer.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedTrainer.getId()))
                .andExpect(jsonPath("$.email").value(savedTrainer.getEmail()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldUpdateTrainer() throws Exception {
        // given
        Trainer savedTrainer = trainerRepository.save(createTrainer());
        trainerCreateDto.setFirstName("Jane");
        trainerCreateDto.setSpecialization("Yoga");
        trainerCreateDto.setBio("Updated bio");
        trainerCreateDto.setCertification("Updated certification");

        // when/then
        mockMvc.perform(put("/api/users/trainers/{id}", savedTrainer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(trainerCreateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.specialization").value("Yoga"))
                .andExpect(jsonPath("$.bio").value("Updated bio"))
                .andExpect(jsonPath("$.certification").value("Updated certification"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteTrainer() throws Exception {
        // given
        Trainer savedTrainer = trainerRepository.save(createTrainer());

        // when/then
        mockMvc.perform(delete("/api/users/trainers/{id}", savedTrainer.getId()))
                .andExpect(status().isNoContent());

        assertThat(trainerRepository.findById(savedTrainer.getId())).isEmpty();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetAllTrainers() throws Exception {
        // given
        Trainer trainer1 = createTrainer();
        trainer1.setEmail("trainer1@example.com");
        trainerRepository.save(trainer1);

        Trainer trainer2 = createTrainer();
        trainer2.setEmail("trainer2@example.com");
        trainerRepository.save(trainer2);

        // when/then
        mockMvc.perform(get("/api/users/trainers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    private Trainer createTrainer() {
        Trainer trainer = new Trainer();
        trainer.setEmail("trainer@example.com");
        trainer.setPassword("password123");
        trainer.setFirstName("John");
        trainer.setLastName("Doe");
        trainer.setRole(Role.ROLE_TRAINER);
        trainer.setSpecialization("Fitness");
        trainer.setBio("Experienced fitness trainer");
        trainer.setCertification("ACE Certified");
        trainer.setRating(0.0);
        return trainer;
    }
} 