package softuni.com.personal_health_dossier.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import softuni.com.personal_health_dossier.model.entities.AllergenEntity;
import softuni.com.personal_health_dossier.model.entities.AllergyEntity;
import softuni.com.personal_health_dossier.model.entities.PatientEntity;
import softuni.com.personal_health_dossier.model.entities.PhysicianEntity;
import softuni.com.personal_health_dossier.repository.AllergyRepository;
import softuni.com.personal_health_dossier.service.AllergenService;
import softuni.com.personal_health_dossier.service.AllergyService;
import softuni.com.personal_health_dossier.service.PatientService;
import softuni.com.personal_health_dossier.service.PhysicianService;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AllergyServiceImpl implements AllergyService {
    private final AllergyRepository allergyRepository;
    private final PhysicianService physicianService;
    private final AllergenService allergenService;


    public AllergyServiceImpl(AllergyRepository allergyRepository, PhysicianService physicianService, AllergenService allergenService) {
        this.allergyRepository = allergyRepository;
        this.physicianService = physicianService;
        this.allergenService = allergenService;

    }

    @Override
    public void saveAllergy(String[] allAllergens, PatientEntity patientEntity, UserDetails principal, LocalDate registrationDate) {
        List<AllergenEntity> allergenEntities = new ArrayList<>();
        for (String allergen : allAllergens) {
            AllergenEntity allergenEntity = this.allergenService.findByAllergenName(allergen).orElse(null);
            if (allergenEntity != null) {
                allergenEntities.add(allergenEntity);
            }
        }
        PhysicianEntity physicianByUsername = this.physicianService.findPhysicianByUsername(principal.getUsername()).orElse(null);

        AllergyEntity allergyEntity = new AllergyEntity();
        allergyEntity.setPatient(patientEntity);
        allergyEntity.getAllergens().addAll(allergenEntities);
        allergyEntity.setRegisteredBy(physicianByUsername);
        allergyEntity.setRegisteredOnDate(registrationDate);

        this.allergyRepository.save(allergyEntity);

    }
}
