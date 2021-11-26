package softuni.com.personal_health_dossier.service;

import softuni.com.personal_health_dossier.model.entities.PhysicianEntity;

import java.io.IOException;
import java.util.Optional;

public interface PhysicianService {
    String readFileContent() throws IOException;

    void seedAllPhysicians() throws IOException;

    Optional<PhysicianEntity> findPhysicianByUsername(String username);
}
