package softuni.com.personal_health_dossier.service;

import org.springframework.security.core.userdetails.UserDetails;
import softuni.com.personal_health_dossier.model.entities.AllergyEntity;
import softuni.com.personal_health_dossier.model.entities.PatientEntity;

import java.time.LocalDate;
import java.util.List;

public interface AllergyService {
    void saveAllergy(String[] allAllergens, PatientEntity patientEntity, UserDetails principal, LocalDate registrationDate);

    List<AllergyEntity> findAllForAPatient(Long id);
}
