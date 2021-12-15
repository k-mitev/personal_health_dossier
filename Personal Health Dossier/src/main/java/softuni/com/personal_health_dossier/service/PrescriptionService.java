package softuni.com.personal_health_dossier.service;

import softuni.com.personal_health_dossier.model.entities.PrescriptionEntity;
import softuni.com.personal_health_dossier.model.services.PrescriptionAddServiceModel;

import java.time.LocalDate;
import java.util.List;

public interface PrescriptionService {
    void savePrescription(PrescriptionAddServiceModel addServiceModel);

    List<PrescriptionEntity> findAllForAPatient(Long id);

    int findTotalPrescriptionsIssuedForTheCurrentDay(Long physicianId, LocalDate localDate);
}
