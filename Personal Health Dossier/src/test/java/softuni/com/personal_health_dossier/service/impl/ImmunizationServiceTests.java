package softuni.com.personal_health_dossier.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import softuni.com.personal_health_dossier.model.entities.ImmunizationEntity;
import softuni.com.personal_health_dossier.model.entities.PatientEntity;
import softuni.com.personal_health_dossier.model.entities.PhysicianEntity;
import softuni.com.personal_health_dossier.model.services.ImmunizationAddServiceModel;
import softuni.com.personal_health_dossier.repository.ImmunizationRepository;
import softuni.com.personal_health_dossier.service.PatientService;
import softuni.com.personal_health_dossier.service.PhysicianService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ImmunizationServiceTests {

    private ImmunizationServiceImpl serviceToTest;

    @Mock
    PatientService mockPatientService;

    @Mock
    PhysicianService mockPhysicianService;

    @Mock
    ImmunizationRepository mockImmunizationRepository;

    @Mock
    ModelMapper mockModelMapper;

    @BeforeEach
    public void setup() {
        serviceToTest = new ImmunizationServiceImpl(mockPatientService,
                mockPhysicianService, mockImmunizationRepository, mockModelMapper);
    }

    @Test
    public void findAllImmunizationsByPatientIdShouldWork() {
        ImmunizationAddServiceModel model = new ImmunizationAddServiceModel();
        model.setVaccine("covid");
        model.setTypeOfApplication("muscular");
        model.setPhysicianUsername("physicianUsername1");
        model.setDosage(0.5);
        model.setImmunizationDate(LocalDate.now());
        model.setPatientPin("7801086443");

        PatientEntity patient = new PatientEntity();
        patient.setId(1L);
        patient.setUsername("user");
        ImmunizationEntity entity = new ImmunizationEntity();
        entity.setVaccinatedBy("doctor");
        entity.setDosage(0.5);
        entity.setTypeOfApplication("muscular");
        entity.setPatient(patient);
        Mockito.when(mockImmunizationRepository.findAllByPatient_Id(1L))
                .thenReturn(List.of(entity));
        List<ImmunizationEntity> allImmunizationsByPatientId = serviceToTest.findAllImmunizationsByPatientId(1L);
        Assertions.assertEquals(allImmunizationsByPatientId.get(0).getVaccinatedBy(), entity.getVaccinatedBy());
    }

    @Test
    public void saveImmunizationShouldWork() {
        PatientEntity patient = new PatientEntity();
        patient.setId(1L);
        patient.setUsername("user");
        patient.setPersonalIdentificationNumber("7801086443");

        PhysicianEntity physician = new PhysicianEntity();
        physician.setUsername("physicianUsername1");

        ImmunizationEntity entity = new ImmunizationEntity();
        entity.setVaccinatedBy("doctor");
        entity.setDosage(0.5);
        entity.setTypeOfApplication("muscular");
        entity.setPatient(patient);

        ImmunizationAddServiceModel model = new ImmunizationAddServiceModel();
        model.setVaccine("covid");
        model.setTypeOfApplication("muscular");
        model.setPhysicianUsername("physicianUsername1");
        model.setDosage(0.5);
        model.setImmunizationDate(LocalDate.now());
        model.setPatientPin("7801086443");
        Mockito.when(mockModelMapper.map(Mockito.any(), Mockito.any())).thenReturn(entity);

        Mockito.when(mockPatientService.findPatientByPIN(model.getPatientPin()))
                .thenReturn(java.util.Optional.of(patient));

        Mockito.when(mockPhysicianService.findPhysicianByUsername(model.getPhysicianUsername()))
                .thenReturn(java.util.Optional.of(physician));

        serviceToTest.saveImmunization(model);
        Mockito.verify(mockImmunizationRepository, Mockito.times(1)).save(entity);

    }
}
