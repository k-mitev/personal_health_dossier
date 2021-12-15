package softuni.com.personal_health_dossier.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import softuni.com.personal_health_dossier.model.entities.PatientEntity;
import softuni.com.personal_health_dossier.model.entities.PhysicianEntity;
import softuni.com.personal_health_dossier.model.entities.PrescriptionEntity;
import softuni.com.personal_health_dossier.model.services.PrescriptionAddServiceModel;
import softuni.com.personal_health_dossier.repository.PrescriptionRepository;
import softuni.com.personal_health_dossier.service.PatientService;
import softuni.com.personal_health_dossier.service.PhysicianService;
import softuni.com.personal_health_dossier.service.PrescriptionService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;
    private final PhysicianService physicianService;
    private final PatientService patientService;
    private final ModelMapper modelMapper;

    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository, PhysicianService physicianService, PatientService patientService, ModelMapper modelMapper) {
        this.prescriptionRepository = prescriptionRepository;
        this.physicianService = physicianService;
        this.patientService = patientService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void savePrescription(PrescriptionAddServiceModel prescriptionAddServiceModel) {
        PrescriptionEntity prescriptionEntity = modelMapper.map(prescriptionAddServiceModel, PrescriptionEntity.class);

        PhysicianEntity physicianEntity = this.physicianService
                .findPhysicianByUsername(prescriptionAddServiceModel.getPhysicianUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Physician not found!"));
        PatientEntity patientEntity = this.patientService
                .findPatientByPIN(prescriptionAddServiceModel.getPatientPin())
                .orElseThrow(() -> new UsernameNotFoundException("Patient with PIN:" + prescriptionAddServiceModel.getPatientPin() + " was not found!"));

        prescriptionEntity.setIssuedBy(physicianEntity);
        prescriptionEntity.setPatient(patientEntity);
        prescriptionEntity.setTheExpiryDate();

        this.prescriptionRepository.save(prescriptionEntity);
    }

    @Override
    public List<PrescriptionEntity> findAllForAPatient(Long patientId) {

        return this.prescriptionRepository.findAllByPatientId(patientId);
    }

    @Override
    public int findTotalPrescriptionsIssuedForTheCurrentDay(Long physicianId, LocalDate localDate) {

        return this.prescriptionRepository.findAllIssuedOnTheCurrentDay(physicianId,localDate);
    }
}
