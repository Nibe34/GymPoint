package gympoint.backend.trainingservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import gympoint.backend.trainingservice.dto.TrainingSessionDto;
import gympoint.backend.trainingservice.entity.TrainingSession;
import gympoint.backend.trainingservice.repository.TrainingSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
class TrainingSessionIntegrationTest {

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
    private TrainingSessionRepository trainingSessionRepository;

    private TrainingSessionDto trainingSessionDto;

    @BeforeEach
    void setUp() {
        trainingSessionRepository.deleteAll();

        trainingSessionDto = new TrainingSessionDto();
        trainingSessionDto.setTrainerId(1L);
        trainingSessionDto.setStartTime(LocalDateTime.now());
        trainingSessionDto.setMaxParticipants(10);
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void shouldCreateTrainingSession() throws Exception {
        // when
        mockMvc.perform(post("/api/training-sessions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(trainingSessionDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trainerId").value(trainingSessionDto.getTrainerId()));

        // then
        List<TrainingSession> sessions = trainingSessionRepository.findAll();
        assertThat(sessions).hasSize(1);
        assertThat(sessions.get(0).getTrainerId()).isEqualTo(trainingSessionDto.getTrainerId());
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void shouldGetTrainingSessionById() throws Exception {
        // given
        TrainingSession savedSession = trainingSessionRepository.save(createTrainingSession());

        // when/then
        mockMvc.perform(get("/api/training-sessions/{id}", savedSession.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedSession.getId()))
                .andExpect(jsonPath("$.trainerId").value(savedSession.getTrainerId()));
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void shouldGetTrainingSessionsByTrainerId() throws Exception {
        // given
        TrainingSession savedSession = trainingSessionRepository.save(createTrainingSession());

        // when/then
        mockMvc.perform(get("/api/training-sessions/trainer/{trainerId}", savedSession.getTrainerId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].trainerId").value(savedSession.getTrainerId()));
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void shouldUpdateTrainingSession() throws Exception {
        // given
        TrainingSession savedSession = trainingSessionRepository.save(createTrainingSession());
        trainingSessionDto.setMaxParticipants(15);

        // when/then
        mockMvc.perform(put("/api/training-sessions/{id}", savedSession.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(trainingSessionDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maxParticipants").value(15));
    }

    @Test
    @WithMockUser(roles = "TRAINER")
    void shouldDeleteTrainingSession() throws Exception {
        // given
        TrainingSession savedSession = trainingSessionRepository.save(createTrainingSession());

        // when/then
        mockMvc.perform(delete("/api/training-sessions/{id}", savedSession.getId()))
                .andExpect(status().isOk());

        assertThat(trainingSessionRepository.findById(savedSession.getId())).isEmpty();
    }

    private TrainingSession createTrainingSession() {
        TrainingSession session = new TrainingSession();
        session.setTrainerId(1L);
        session.setStartTime(LocalDateTime.now());
        session.setMaxParticipants(10);
        return session;
    }
} 