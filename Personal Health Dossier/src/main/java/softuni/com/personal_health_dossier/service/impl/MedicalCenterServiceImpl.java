package softuni.com.personal_health_dossier.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import softuni.com.personal_health_dossier.model.entities.MedicalCenterEntity;
import softuni.com.personal_health_dossier.model.entities.PatientEntity;
import softuni.com.personal_health_dossier.model.entities.PhysicianEntity;
import softuni.com.personal_health_dossier.model.services.MedicalCenterAddServiceModel;
import softuni.com.personal_health_dossier.repository.MedicalCenterRepository;
import softuni.com.personal_health_dossier.service.MedicalCenterService;
import softuni.com.personal_health_dossier.service.PatientService;
import softuni.com.personal_health_dossier.service.PhysicianService;

import java.util.List;


@Service
public class MedicalCenterServiceImpl implements MedicalCenterService {
    private final MedicalCenterRepository medicalCenterRepository;
    private final PhysicianService physicianService;
    private final PatientService patientService;
    private final ModelMapper modelMapper;

    public MedicalCenterServiceImpl(MedicalCenterRepository medicalCenterRepository, PhysicianService physicianService,
                                    PatientService patientService, ModelMapper modelMapper) {
        this.medicalCenterRepository = medicalCenterRepository;
        this.physicianService = physicianService;
        this.patientService = patientService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveMedicalCenter(MedicalCenterAddServiceModel medicalCenterAddServiceModel) {
        MedicalCenterEntity medicalCenterEntity =
                modelMapper.map(medicalCenterAddServiceModel, MedicalCenterEntity.class);
        PatientEntity patientEntity = this.patientService
                .findPatientByPIN(medicalCenterAddServiceModel.getPatientPin())
                .orElseThrow(() -> new UsernameNotFoundException("Patient with PIN:" +
                        medicalCenterAddServiceModel.getPatientPin() + " does not exist!"));
        PhysicianEntity physicianEntity = this.physicianService
                .findPhysicianByUsername(medicalCenterAddServiceModel.getPhysicianUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Physician with username:" +
                        medicalCenterAddServiceModel.getPhysicianUsername() + " does not exist!"));
        medicalCenterEntity.setDoctors(List.of(physicianEntity));
        medicalCenterEntity.setPatient(patientEntity);

        this.medicalCenterRepository.save(medicalCenterEntity);
    }

    @Override
    public List<MedicalCenterEntity> findAllForAPatient(Long patientId) {
        return this.medicalCenterRepository.findAllByPatientId(patientId);

    }
}
