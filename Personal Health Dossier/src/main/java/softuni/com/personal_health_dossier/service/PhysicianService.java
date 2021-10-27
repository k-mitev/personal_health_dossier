package softuni.com.personal_health_dossier.service;

import java.io.IOException;

public interface PhysicianService {
    String readFileContent() throws IOException;

    void seedAllPhysicians() throws IOException;
}
