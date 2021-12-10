package softuni.com.personal_health_dossier.service;

import org.springframework.security.core.userdetails.UserDetails;
import softuni.com.personal_health_dossier.model.entities.PatientEntity;

import java.time.LocalDate;

public interface AllergyService {
    void saveAllergy(String[] allAllergens, PatientEntity patientEntity, UserDetails principal, LocalDate registrationDate);
}
