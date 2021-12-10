package softuni.com.personal_health_dossier.service;

import softuni.com.personal_health_dossier.model.entities.AllergenEntity;

import java.util.Optional;

public interface AllergenService {
    void seedAllergens(String[] allAllergens);

    Optional<AllergenEntity> findByAllergenName(String name);
}
