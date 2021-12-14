package softuni.com.personal_health_dossier.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import softuni.com.personal_health_dossier.model.entities.PatientEntity;
import softuni.com.personal_health_dossier.model.entities.UserRoleEntity;
import softuni.com.personal_health_dossier.model.entities.enums.UserRoleEnum;
import softuni.com.personal_health_dossier.model.services.PatientServiceModel;
import softuni.com.personal_health_dossier.model.services.UserRegisterServiceModel;
import softuni.com.personal_health_dossier.repository.PatientRepository;
import softuni.com.personal_health_dossier.service.UserRoleService;

import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTests {

    private PatientServiceImpl serviceToTest;

    @Mock
    UserRoleService mockUserRoleService;

    @Mock
    PatientRepository mockPatientRepository;

    @Mock
    PasswordEncoder mockPasswordEncoder;

    @Mock
    ModelMapper mockModelMapper;

    @Mock
    HealthDossierUserService mockHealthDossierService;

    @BeforeEach
    public void setup() {
        this.serviceToTest = new PatientServiceImpl(mockUserRoleService, mockPatientRepository,
                mockPasswordEncoder, mockModelMapper, mockHealthDossierService);
    }

    @Test
    public void testSeedPatients() {
        UserRoleEntity patientRole = new UserRoleEntity();
        patientRole.setRole(UserRoleEnum.PATIENT);

        UserRoleEntity adminRole = new UserRoleEntity();
        adminRole.setRole(UserRoleEnum.ADMIN);


        PatientEntity patient = new PatientEntity("7801086443");
        patient.setUsername("patient");
        patient.setRoles(List.of(patientRole));
        patient.setFirstName("Kristian");
        patient.setLastName("Mitev");

        PatientEntity admin = new PatientEntity("0148220555");
        admin.setUsername("admin");
        admin.setRoles(List.of(patientRole, adminRole));
        admin.setFirstName("Admin");
        admin.setLastName("Adminov");

        Mockito.when(mockUserRoleService.findByUserRole(UserRoleEnum.PATIENT))
                .thenReturn(patientRole);

        Mockito.when(mockUserRoleService.findByUserRole(UserRoleEnum.ADMIN))
                .thenReturn(adminRole);

        serviceToTest.seedPatients();

        Mockito.verify(mockPatientRepository, Mockito.times(1)).count();
    }

    @Test
    public void testFindPatientByUsername() {
        PatientEntity patient = new PatientEntity("7801086443");
        patient.setUsername("patient");
        patient.setFirstName("Kristian");
        patient.setLastName("Mitev");

        Mockito.when(mockPatientRepository.findByUsername("patient"))
                .thenReturn(java.util.Optional.of(patient));

        PatientEntity patientEntity = serviceToTest.findPatientByUsername("patient").orElse(null);

        Assertions.assertEquals(patient.getUsername(), patientEntity.getUsername());
    }

    @Test
    public void testFindPatientByPIN() {
        PatientEntity patient = new PatientEntity("7801086443");
        patient.setUsername("patient");
        patient.setFirstName("Kristian");
        patient.setLastName("Mitev");

        Mockito.when(mockPatientRepository.findByPersonalIdentificationNumber("7801086443"))
                .thenReturn(java.util.Optional.of(patient));

        PatientEntity patientEntity = serviceToTest.findPatientByPIN("7801086443").orElse(null);

        Assertions.assertEquals(patient.getUsername(), patientEntity.getUsername());
    }

    @Test
    public void testRegisterAndLoginPatient() {
        UserRegisterServiceModel model = new UserRegisterServiceModel();
        model.setUsername("patient");

        UserRoleEntity patientRole = new UserRoleEntity();
        patientRole.setRole(UserRoleEnum.PATIENT);

        PatientEntity patient = new PatientEntity("7801086443");
        patient.setUsername("patient");
        patient.setFirstName("Kristian");
        patient.setLastName("Mitev");
        patient.addRole(patientRole);
        patient.setPassword("123");

        Mockito.when(mockUserRoleService.findByUserRole(UserRoleEnum.PATIENT))
                .thenReturn(patientRole);

        Mockito.when(mockModelMapper.map(Mockito.any(), Mockito.any()))
                .thenReturn(patient);
        List<SimpleGrantedAuthority> authorities =
                patient
                        .getRoles()
                        .stream()
                        .map(ur -> new SimpleGrantedAuthority(ur.getRole().name())).collect(Collectors.toList());
        UserDetails principal = new User(patient.getUsername(), patient.getPassword(), authorities);
        Mockito.when(mockHealthDossierService.loadUserByUsername("patient"))
                .thenReturn(principal);
        serviceToTest.registerAndLoginPatient(model);

        Mockito.verify(mockPatientRepository, Mockito.times(1)).save(patient);

    }

    @Test
    public void testUpdateAgeOfPatientsWhosAgeEndingWithDays() {
        PatientEntity patient = new PatientEntity("2152126443");
        patient.setUsername("patient");
        patient.setFirstName("Kristian");
        patient.setLastName("Mitev");
        patient.setId(1L);

        PatientEntity admin = new PatientEntity("2152130555");
        admin.setUsername("admin");
        admin.setFirstName("Admin");
        admin.setLastName("Adminov");
        admin.setId(2L);

        Mockito.when(mockPatientRepository.findAllByAgeEndingWith("day(s)"))
                .thenReturn(List.of(patient, admin));

        serviceToTest.updateAgeOfAllPatientsWhichAgeEndingWithDays();

        Mockito.verify(mockPatientRepository, Mockito.times(2)).updatePatientsAge(Mockito.anyString(), Mockito.anyLong());
    }

    @Test
    public void testUpdateAgeOfPatientsWhosAgeEndingWithMonthsOrYears() {
        PatientEntity patient = new PatientEntity("7801086443");
        patient.setUsername("patient");
        patient.setFirstName("Kristian");
        patient.setLastName("Mitev");
        patient.setId(1L);

        PatientEntity admin = new PatientEntity("0148220555");
        admin.setUsername("admin");
        admin.setFirstName("Admin");
        admin.setLastName("Adminov");
        admin.setId(2L);

        Mockito.when(mockPatientRepository.findAllByAgeIsNotContaining("day(s)"))
                .thenReturn(List.of(patient, admin));

        serviceToTest.updateAgeOfAllPatientsWhichAgeEndingWithMonthsOrYears();

        Mockito.verify(mockPatientRepository, Mockito.times(2)).updatePatientsAge(Mockito.anyString(), Mockito.anyLong());
    }

    @Test
    public void testFindPatientById() {
        PatientEntity patient = new PatientEntity("7801086443");
        patient.setUsername("patient");
        patient.setFirstName("Kristian");
        patient.setLastName("Mitev");
        patient.setId(1L);

        Mockito.when(mockPatientRepository.findById(1L))
                .thenReturn(java.util.Optional.of(patient));

        PatientServiceModel model = new PatientServiceModel();
        model.setUsername("patient");
        model.setId(1L);

        Mockito.when(mockModelMapper.map(Mockito.any(), Mockito.any()))
                .thenReturn(model);

        serviceToTest.findPatientById(1L);
        Assertions.assertEquals(model.getUsername(), patient.getUsername());

    }
}
