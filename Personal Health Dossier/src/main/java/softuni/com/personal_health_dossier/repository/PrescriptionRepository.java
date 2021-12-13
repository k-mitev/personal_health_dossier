package softuni.com.personal_health_dossier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.com.personal_health_dossier.model.entities.PrescriptionEntity;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<PrescriptionEntity, Long> {
    @Query("SELECT p FROM PrescriptionEntity p WHERE p.patient.id=:id")
    List<PrescriptionEntity> findAllByPatientId(@Param("id") Long patientId);
}
