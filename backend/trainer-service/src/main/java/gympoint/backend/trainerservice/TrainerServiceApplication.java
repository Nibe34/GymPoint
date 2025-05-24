package gympoint.backend.trainerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("gympoint.backend.trainerservice.entity")
@EnableJpaRepositories("gympoint.backend.trainerservice.repository")
public class TrainerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrainerServiceApplication.class, args);
    }

}
