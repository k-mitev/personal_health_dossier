package softuni.com.personal_health_dossier.service;

import softuni.com.personal_health_dossier.model.entities.MedicalCenterEntity;
import softuni.com.personal_health_dossier.model.services.MedicalCenterAddServiceModel;

import java.util.List;

public interface MedicalCenterService {
    void saveMedicalCenter(MedicalCenterAddServiceModel medicalCenterAddServiceModel);

    List<MedicalCenterEntity> findAllForAPatient(Long patientId);
}
