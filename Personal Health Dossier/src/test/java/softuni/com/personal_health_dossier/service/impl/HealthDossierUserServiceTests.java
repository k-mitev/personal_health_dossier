package softuni.com.personal_health_dossier.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import softuni.com.personal_health_dossier.model.entities.PatientEntity;
import softuni.com.personal_health_dossier.model.entities.PhysicianEntity;
import softuni.com.personal_health_dossier.model.entities.UserRoleEntity;
import softuni.com.personal_health_dossier.model.entities.enums.UserRoleEnum;
import softuni.com.personal_health_dossier.repository.PatientRepository;
import softuni.com.personal_health_dossier.repository.PhysicianRepository;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class HealthDossierUserServiceTests {

    private HealthDossierUserService serviceToTest;

    @Mock
    PatientRepository mockPatientRepository;

    @Mock
    PhysicianRepository mockPhysicianRepository;

    @BeforeEach
    public void setup() {
        serviceToTest = new HealthDossierUserService(mockPatientRepository,
                mockPhysicianRepository);
    }

    @Test
    public void testUserNotFound() {
        Assertions.assertThrows(
                UsernameNotFoundException.class, () ->
                        serviceToTest.loadUserByUsername("user"));
    }

    @Test
    void testExistingPatient() {
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setPersonalIdentificationNumber("7209240656");
        patientEntity.setFirstName("Radostina");
        patientEntity.setLastName("Medvedeva");
        patientEntity.setPassword("123");
        patientEntity.setAge("");
        patientEntity.setUsername("joy");

        UserRoleEntity rolePatient = new UserRoleEntity();
        rolePatient.setRole(UserRoleEnum.PATIENT);

        UserRoleEntity roleAdmin = new UserRoleEntity();
        roleAdmin.setRole(UserRoleEnum.ADMIN);

        patientEntity.setRoles(List.of(rolePatient, roleAdmin));

        Mockito.when(mockPatientRepository.findByUsername("joy"))
                .thenReturn(java.util.Optional.of(patientEntity));

        UserDetails patientPrincipal = serviceToTest.loadUserByUsername("joy");

        Assertions.assertEquals(patientEntity.getUsername(), patientPrincipal.getUsername());
        Assertions.assertEquals(2, patientPrincipal.getAuthorities().size());
    }

    @Test
    void testExistingPhysician() {
        PhysicianEntity physicianEntity = new PhysicianEntity();
        physicianEntity.setPersonalIdentificationNumber("7209240656");
        physicianEntity.setFirstName("Radostina");
        physicianEntity.setLastName("Medvedeva");
        physicianEntity.setPassword("123");
        physicianEntity.setUsername("joy");

        UserRoleEntity rolePhysician = new UserRoleEntity();
        rolePhysician.setRole(UserRoleEnum.PHYSICIAN);

        physicianEntity.setRoles(List.of(rolePhysician));

        Mockito.when(mockPhysicianRepository.findByUsername("joy"))
                .thenReturn(java.util.Optional.of(physicianEntity));

        UserDetails patientPrincipal = serviceToTest.loadUserByUsername("joy");

        Assertions.assertEquals(physicianEntity.getUsername(), patientPrincipal.getUsername());
        Assertions.assertEquals(1, patientPrincipal.getAuthorities().size());
    }
}
