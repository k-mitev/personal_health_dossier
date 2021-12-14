package softuni.com.personal_health_dossier.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import softuni.com.personal_health_dossier.model.entities.MedicalCenterEntity;
import softuni.com.personal_health_dossier.model.entities.PatientEntity;
import softuni.com.personal_health_dossier.model.entities.PhysicianEntity;

import softuni.com.personal_health_dossier.model.services.MedicalCenterAddServiceModel;
import softuni.com.personal_health_dossier.repository.MedicalCenterRepository;
import softuni.com.personal_health_dossier.service.PatientService;
import softuni.com.personal_health_dossier.service.PhysicianService;


import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MedicalCenterServiceTests {
    private MedicalCenterServiceImpl serviceToTest;

    @Mock
    PatientService mockPatientService;

    @Mock
    PhysicianService mockPhysicianService;

    @Mock
    MedicalCenterRepository mockMedicalCenterRepository;

    @Mock
    ModelMapper mockModelMapper;

    @BeforeEach
    public void setup() {
        serviceToTest = new MedicalCenterServiceImpl(mockMedicalCenterRepository, mockPhysicianService,
                mockPatientService, mockModelMapper);
    }

    @Test
    public void testSaveMedicalCenter() {
        PatientEntity patient = new PatientEntity();
        patient.setId(1L);
        patient.setUsername("user");
        patient.setPersonalIdentificationNumber("7801086443");

        PhysicianEntity physician = new PhysicianEntity();
        physician.setUsername("physicianUsername1");

        MedicalCenterEntity entity = new MedicalCenterEntity();
        entity.setDoctors(List.of(physician));
        entity.setPatient(patient);

        MedicalCenterAddServiceModel model = new MedicalCenterAddServiceModel();
        model.setPhysicianUsername("physicianUsername1");
        model.setPatientPin("7801086443");
        model.setName("sin krast");

        Mockito.when(mockModelMapper.map(Mockito.any(), Mockito.any()))
                .thenReturn(entity);

        Mockito.when(mockPatientService.findPatientByPIN("7801086443"))
                .thenReturn(java.util.Optional.of(patient));

        Mockito.when(mockPhysicianService.findPhysicianByUsername(model.getPhysicianUsername()))
                .thenReturn(java.util.Optional.of(physician));

        serviceToTest.saveMedicalCenter(model);
        Mockito.verify(mockMedicalCenterRepository, Mockito.times(1)).save(entity);

    }
    @Test
    public void testFindAllMedicalCentersByPatientIdShouldWork() {
        PatientEntity patient = new PatientEntity();
        patient.setId(1L);
        patient.setUsername("user");
        patient.setPersonalIdentificationNumber("7801086443");

        PhysicianEntity physician = new PhysicianEntity();
        physician.setUsername("physicianUsername1");

        MedicalCenterEntity entity = new MedicalCenterEntity();
        entity.setDoctors(List.of(physician));
        entity.setPatient(patient);

        Mockito.when(mockMedicalCenterRepository.findAllByPatientId(1L))
                .thenReturn(List.of(entity));
        List<MedicalCenterEntity> allForAPatient = serviceToTest.findAllForAPatient(1L);
        Assertions.assertEquals(allForAPatient.get(0).getPatient().getUsername(), patient.getUsername());
    }
}
