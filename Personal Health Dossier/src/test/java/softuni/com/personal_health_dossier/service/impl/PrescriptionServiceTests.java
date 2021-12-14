package softuni.com.personal_health_dossier.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import softuni.com.personal_health_dossier.model.entities.PatientEntity;
import softuni.com.personal_health_dossier.model.entities.PhysicianEntity;
import softuni.com.personal_health_dossier.model.entities.PrescriptionEntity;
import softuni.com.personal_health_dossier.model.entities.enums.PrescriptionType;
import softuni.com.personal_health_dossier.model.services.PrescriptionAddServiceModel;
import softuni.com.personal_health_dossier.repository.PrescriptionRepository;
import softuni.com.personal_health_dossier.service.PatientService;
import softuni.com.personal_health_dossier.service.PhysicianService;


import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PrescriptionServiceTests {

    private PrescriptionServiceImpl serviceToTest;

    @Mock
    PrescriptionRepository mockPrescriptionRepository;

    @Mock
    PatientService mockPatientService;

    @Mock
    PhysicianService mockPhysicianService;

    @Mock
    ModelMapper mockModelMapper;

    @BeforeEach
    public void setup() {
        serviceToTest = new PrescriptionServiceImpl(mockPrescriptionRepository, mockPhysicianService,
                mockPatientService, mockModelMapper);
    }

    @Test
    public void testSavePrescription() {
        PatientEntity patient = new PatientEntity();
        patient.setUsername("user");
        patient.setPersonalIdentificationNumber("0000000000");

        PhysicianEntity physician = new PhysicianEntity();
        physician.setUsername("doc");

        PrescriptionEntity entity = new PrescriptionEntity();
        entity.setPatient(patient);
        entity.setIssuedBy(physician);
        entity.setDescription("analgin 2x10mg");
        entity.setType(PrescriptionType.WHITE);

        PrescriptionAddServiceModel model = new PrescriptionAddServiceModel();
        model.setPatientPin("1234567890");

        Mockito.when(mockModelMapper.map(Mockito.any(), Mockito.any()))
                .thenReturn(entity);

        Mockito.when(mockPatientService.findPatientByPIN("1234567890"))
                .thenReturn(java.util.Optional.of(patient));

        Mockito.when(mockPhysicianService.findPhysicianByUsername(model.getPhysicianUsername()))
                .thenReturn(java.util.Optional.of(physician));

        serviceToTest.savePrescription(model);

        Mockito.verify(mockPrescriptionRepository, Mockito.times(1)).save(entity);

    }

    @Test
    public void testFindAllPrescriptionsByPatientId() {
        PrescriptionEntity entity1 = new PrescriptionEntity();
        entity1.setType(PrescriptionType.WHITE);
        entity1.setUsageTimes(1);

        PrescriptionEntity entity2 = new PrescriptionEntity();
        entity2.setType(PrescriptionType.GREEN);
        entity2.setUsageTimes(2);

        Mockito.when(mockPrescriptionRepository.findAllByPatientId(1L))
                .thenReturn(List.of(entity1, entity2));

        List<PrescriptionEntity> allForAPatient = serviceToTest.findAllForAPatient(1L);

        Assertions.assertEquals(allForAPatient.get(1).getUsageTimes(), entity2.getUsageTimes());
        Assertions.assertEquals(allForAPatient.get(0).getUsageTimes(), entity1.getUsageTimes());

    }
}
