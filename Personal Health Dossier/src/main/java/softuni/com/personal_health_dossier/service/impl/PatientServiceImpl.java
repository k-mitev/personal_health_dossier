package softuni.com.personal_health_dossier.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import softuni.com.personal_health_dossier.model.entities.AllergyEntity;
import softuni.com.personal_health_dossier.model.entities.PatientEntity;
import softuni.com.personal_health_dossier.model.entities.UserRoleEntity;
import softuni.com.personal_health_dossier.model.entities.enums.BloodGroupEnum;
import softuni.com.personal_health_dossier.model.entities.enums.UserRoleEnum;
import softuni.com.personal_health_dossier.model.services.PatientEditProfileServiceModel;
import softuni.com.personal_health_dossier.model.services.PatientServiceModel;
import softuni.com.personal_health_dossier.model.services.UserRegisterServiceModel;
import softuni.com.personal_health_dossier.repository.PatientRepository;
import softuni.com.personal_health_dossier.service.PatientService;
import softuni.com.personal_health_dossier.service.UserRoleService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {
    private final UserRoleService userRoleService;
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final HealthDossierUserService healthDossierUserService;

    public PatientServiceImpl(UserRoleService userRoleService, PatientRepository patientRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, HealthDossierUserService healthDossierUserService) {
        this.userRoleService = userRoleService;
        this.patientRepository = patientRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.healthDossierUserService = healthDossierUserService;
    }

    @Override
    public void seedPatients() {
        if (patientRepository.count() == 0) {
            UserRoleEntity adminRole = this.userRoleService.findByUserRole(UserRoleEnum.ADMIN);
            UserRoleEntity patientRole = this.userRoleService.findByUserRole(UserRoleEnum.PATIENT);

            PatientEntity patient = new PatientEntity("7801086443");
            patient.setUsername("patient");
            patient.setPassword(passwordEncoder.encode("topsecret"));
            patient.setRoles(List.of(patientRole));
            patient.setFirstName("Kristian");
            patient.setLastName("Mitev");


            PatientEntity admin = new PatientEntity("0148220555");
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("topsecret"));
            admin.setRoles(List.of(patientRole, adminRole));
            admin.setFirstName("Admin");
            admin.setLastName("Adminov");


            this.patientRepository.saveAll(List.of(patient, admin));

        }
    }

    @Override
    public Optional<PatientEntity> findPatientByUsername(String username) {
        return this.patientRepository.findByUsername(username);
    }

    @Override
    public Optional<PatientEntity> findPatientByPIN(String personalIdentificationNumber) {
        return this.patientRepository.findByPersonalIdentificationNumber(personalIdentificationNumber);
    }

    @Override
    public void registerAndLoginPatient(UserRegisterServiceModel userRegisterServiceModel) {
        PatientEntity patientEntity = modelMapper.map(userRegisterServiceModel, PatientEntity.class);
        patientEntity.setPassword(passwordEncoder.encode(userRegisterServiceModel.getPassword()));

        UserRoleEntity patientRole = this.userRoleService.findByUserRole(UserRoleEnum.PATIENT);

        patientEntity.addRole(patientRole);
        patientEntity.setAge("");
        this.patientRepository.save(patientEntity);

        UserDetails principal = healthDossierUserService.loadUserByUsername(patientEntity.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                patientEntity.getPassword(),
                principal.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    @Scheduled(cron = "@daily")
    public void updateAgeOfAllPatientsWhichAgeEndingWithDays() {
        List<PatientEntity> patients = this.patientRepository.findAllByAgeEndingWith("day(s)");
        for (PatientEntity patient : patients) {
            String age = patient.setTheAge();
            Long id = patient.getId();
            this.patientRepository
                    .updatePatientsAge(age, id);
        }
    }

    @Override
    @Scheduled(cron = "@monthly")
    public void updateAgeOfAllPatientsWhichAgeEndingWithMonthsOrYears() {
        List<PatientEntity> patientEntities = this.patientRepository.findAllByAgeIsNotContaining("day(s)");
        for (PatientEntity patientEntity : patientEntities) {
            Long id = patientEntity.getId();
            String age = patientEntity.setTheAge();
            this.patientRepository.updatePatientsAge(age, id);
        }
    }

    @Override
    public List<PatientEntity> findAll() {
        return this.patientRepository.findAll();
    }

    @Override
    public PatientServiceModel findPatientById(Long id) {

        return this.patientRepository
                .findById(id)
                .map(p -> modelMapper.map(p, PatientServiceModel.class))
                .orElseThrow(() -> new UsernameNotFoundException("Patient not found!"));
    }

    @Override
    public void updatePatientKilos(Double kilos, Long id) {
        this.patientRepository.updatePatientKilos(kilos, id);
    }

    @Override
    public void updatePatientHeight(Integer height, Long id) {
        this.patientRepository.updatePatientHeight(height, id);
    }

    @Override
    public void updatePatientBloodGroup(BloodGroupEnum bloodGroup, Long id) {
        this.patientRepository.updatePatientBloodGroup(bloodGroup, id);

    }

    @Override
    public void updatePatientConsentForDonation(boolean consentForOrganDonationAfterDeath, Long id) {
        this.patientRepository.updateConsentForDonation(consentForOrganDonationAfterDeath, id);
    }

    @Override
    public void updatePatient(PatientEditProfileServiceModel serviceModel) {
        this.patientRepository.updatePatientEntity(serviceModel.getUsername(), serviceModel.getFirstName(),
                serviceModel.getMiddleName(), serviceModel.getLastName(),
                serviceModel.getPassword(), serviceModel.getImgUrl(),
                serviceModel.getMobileNumber(), serviceModel.getId());
    }


}
