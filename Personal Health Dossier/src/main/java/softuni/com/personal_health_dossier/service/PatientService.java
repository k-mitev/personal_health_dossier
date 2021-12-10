package softuni.com.personal_health_dossier.service;

import softuni.com.personal_health_dossier.model.entities.AllergyEntity;
import softuni.com.personal_health_dossier.model.entities.PatientEntity;
import softuni.com.personal_health_dossier.model.entities.enums.BloodGroupEnum;
import softuni.com.personal_health_dossier.model.services.PatientEditProfileServiceModel;
import softuni.com.personal_health_dossier.model.services.PatientServiceModel;
import softuni.com.personal_health_dossier.model.services.UserRegisterServiceModel;

import java.util.List;
import java.util.Optional;

public interface PatientService {

    void seedPatients();

    Optional<PatientEntity> findPatientByUsername(String username);

    Optional<PatientEntity> findPatientByPIN(String personalIdentificationNumber);

    void registerAndLoginPatient(UserRegisterServiceModel userRegisterServiceModel);


    void updateAgeOfAllPatientsWhichAgeEndingWithDays();

    void updateAgeOfAllPatientsWhichAgeEndingWithMonthsOrYears();

    List<PatientEntity> findAll();

    PatientServiceModel findPatientById(Long id);

    void updatePatientKilos(Double kilos, Long id);

    void updatePatientHeight(Integer height, Long id);

    void updatePatientBloodGroup(BloodGroupEnum bloodGroup, Long id);

    void updatePatientConsentForDonation(boolean consentForOrganDonationAfterDeath, Long id);

    void updatePatient(PatientEditProfileServiceModel editProfileServiceModel);

}
