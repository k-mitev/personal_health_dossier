package softuni.com.personal_health_dossier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.com.personal_health_dossier.model.entities.MedicalCenterEntity;

import java.util.List;

@Repository
public interface MedicalCenterRepository extends JpaRepository<MedicalCenterEntity, Long> {
    @Query("SELECT mc FROM MedicalCenterEntity mc WHERE mc.patient.id=:id")
    List<MedicalCenterEntity> findAllByPatientId(@Param("id") Long patientId);
}
