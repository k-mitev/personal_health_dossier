package softuni.com.personal_health_dossier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import softuni.com.personal_health_dossier.model.entities.enums.BloodGroupEnum;

@SpringBootApplication
@EnableScheduling
public class PersonalHealthDossierApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalHealthDossierApplication.class, args);
    }

}
