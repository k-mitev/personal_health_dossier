package softuni.com.personal_health_dossier.service;

import softuni.com.personal_health_dossier.model.entities.ImmunizationEntity;
import softuni.com.personal_health_dossier.model.services.ImmunizationAddServiceModel;

import java.util.List;

public interface ImmunizationService {
    void saveImmunization(ImmunizationAddServiceModel immunizationAddServiceModel);

    List<ImmunizationEntity> findAllImmunizationsByPatientId(Long id);

}
