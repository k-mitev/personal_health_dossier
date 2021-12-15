package softuni.com.personal_health_dossier.web;


import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import softuni.com.personal_health_dossier.model.entities.ImmunizationEntity;
import softuni.com.personal_health_dossier.model.entities.PatientEntity;
import softuni.com.personal_health_dossier.model.entities.PhysicianEntity;
import softuni.com.personal_health_dossier.model.entities.UserRoleEntity;
import softuni.com.personal_health_dossier.model.entities.enums.MedicalSpecialty;
import softuni.com.personal_health_dossier.model.entities.enums.UserRoleEnum;
import softuni.com.personal_health_dossier.repository.ImmunizationRepository;
import softuni.com.personal_health_dossier.repository.PatientRepository;
import softuni.com.personal_health_dossier.repository.PhysicianRepository;
import softuni.com.personal_health_dossier.repository.UserRoleRepository;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ImmunizationsControllerTest {

    private static final String IMMUNIZATIONS_CONTROLLER_PREFIX = "/immunizations";
    private Long testPatientId;


    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    PhysicianRepository physicianRepository;

    @Autowired
    ImmunizationRepository immunizationRepository;


    @BeforeEach
    public void setup() {
        this.init();
    }

    @AfterEach
    public void clean() {
        immunizationRepository.deleteAll();
        physicianRepository.deleteAll();
        patientRepository.deleteAll();
        userRoleRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "patient123", roles = {"PATIENT"})
    public void testShouldReturnValidStatusViewNameAndModel() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get(IMMUNIZATIONS_CONTROLLER_PREFIX + "/all/{id}", testPatientId))
                .andExpect(status().isOk())
                .andExpect(view().name("immunizations-all"))
                .andExpect(model().attributeExists("patientViewModel", "immunizationViewModels"));
    }

    @Test
    @WithMockUser(username = "physician", roles = {"PHYSICIAN"})
    public void testAddImmunizationPostRequest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post(IMMUNIZATIONS_CONTROLLER_PREFIX + "/add")
                        .param("patientPin", "7501086443")
                        .param("vaccine", "covid")
                        .param("immunizationDate", "2021-12-12")
                        .param("typeOfApplication", "muscular")
                        .param("dosage", "0.5")
                        .with(csrf())
        ).andExpect(status().is3xxRedirection());

        Assertions.assertEquals(2, immunizationRepository.count());
    }

    private void init() {
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setRole(UserRoleEnum.PATIENT);

        UserRoleEntity physicianRole = new UserRoleEntity();
        physicianRole.setRole(UserRoleEnum.PHYSICIAN);

        userRoleRepository.save(userRoleEntity);
        userRoleRepository.save(physicianRole);

        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setUsername("patient123");
        patientEntity.setFirstName("Petar");
        patientEntity.setLastName("Petrov");
        patientEntity.setPersonalIdentificationNumber("7501086443");
        patientEntity.setAge("");
        patientEntity.setPassword("123");
        patientEntity.setRoles(List.of(userRoleEntity));
        patientEntity = patientRepository.save(patientEntity);

        testPatientId = patientEntity.getId();

        ImmunizationEntity immunizationEntity = new ImmunizationEntity();
        immunizationEntity.setTypeOfApplication("muscular");
        immunizationEntity.setDosage(0.5);
        immunizationEntity.setImmunizationDate(LocalDate.now());
        immunizationEntity.setVaccine("бцж");
        immunizationEntity.setVaccinatedBy("doctor");
        immunizationEntity.setPatient(patientEntity);

        immunizationEntity = immunizationRepository.save(immunizationEntity);

        PhysicianEntity physicianEntity = new PhysicianEntity();
        physicianEntity.setUsername("physician");
        physicianEntity.setPassword("123");
        physicianEntity.setFirstName("Angel");
        physicianEntity.setLastName("Angelov");
        physicianEntity.setRegion("Sofia");
        physicianEntity.setSpecialty(MedicalSpecialty.CARDIOVASCULAR);
        physicianEntity.setPersonalIdentificationNumber("BG2111111111");
        physicianEntity.setRoles(List.of(physicianRole));
        physicianRepository.save(physicianEntity);
    }

}
