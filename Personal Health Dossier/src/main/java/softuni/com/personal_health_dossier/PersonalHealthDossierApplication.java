package softuni.com.personal_health_dossier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import softuni.com.personal_health_dossier.model.entities.enums.BloodGroupEnum;

@SpringBootApplication
@EnableScheduling
public class PersonalHealthDossierApplication {

    public static void main(String[] args) {
//        SpringApplication.run(PersonalHealthDossierApplication.class, args);


        //Replacing the above commented code because of "java.awt.HeadlessException: null" when added javax.swing.JOptionPane
        //TODO check if anything should be added to the test folder
        SpringApplicationBuilder builder = new SpringApplicationBuilder(PersonalHealthDossierApplication.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);
    }

}
