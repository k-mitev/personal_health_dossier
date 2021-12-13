package softuni.com.personal_health_dossier.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import softuni.com.personal_health_dossier.model.entities.ImmunizationEntity;
import softuni.com.personal_health_dossier.model.entities.PatientEntity;
import softuni.com.personal_health_dossier.model.entities.PhysicianEntity;
import softuni.com.personal_health_dossier.model.services.ImmunizationAddServiceModel;
import softuni.com.personal_health_dossier.repository.ImmunizationRepository;
import softuni.com.personal_health_dossier.service.ImmunizationService;
import softuni.com.personal_health_dossier.service.PatientService;
import softuni.com.personal_health_dossier.service.PhysicianService;

import java.util.List;

@Service
public class ImmunizationServiceImpl implements ImmunizationService {
    private final PatientService patientService;
    private final PhysicianService physicianService;
    private final ImmunizationRepository immunizationRepository;
    private final ModelMapper modelMapper;

    public ImmunizationServiceImpl(PatientService patientService, PhysicianService physicianService, ImmunizationRepository immunizationRepository, ModelMapper modelMapper) {
        this.patientService = patientService;
        this.physicianService = physicianService;
        this.immunizationRepository = immunizationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveImmunization(ImmunizationAddServiceModel immunizationAddServiceModel) {
        ImmunizationEntity immunizationEntity =
                this.modelMapper.map(immunizationAddServiceModel, ImmunizationEntity.class);
        PatientEntity patientEntity = this.patientService
                .findPatientByPIN(immunizationAddServiceModel.getPatientPin())
                .orElseThrow(() -> new UsernameNotFoundException("Patient with PIN:" +
                        immunizationAddServiceModel.getPatientPin() + " does not exist!"));
        PhysicianEntity physicianEntity = this.physicianService
                .findPhysicianByUsername(immunizationAddServiceModel.getPhysicianUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Physician with username:" +
                        immunizationAddServiceModel.getPhysicianUsername() + " does not exist!"));

        immunizationEntity.setPatient(patientEntity);
        immunizationEntity.setVaccinatedBy(physicianEntity.getFirstName() + " " + physicianEntity.getLastName());

        this.immunizationRepository.save(immunizationEntity);
    }

    @Override
    public List<ImmunizationEntity> findAllImmunizationsByPatientId(Long patientId) {
        return this.immunizationRepository.findAllByPatient_Id(patientId);
    }
}
