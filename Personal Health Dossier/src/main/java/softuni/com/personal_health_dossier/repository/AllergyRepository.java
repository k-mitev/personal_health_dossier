package softuni.com.personal_health_dossier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.com.personal_health_dossier.model.entities.AllergyEntity;

import java.util.List;

@Repository
public interface AllergyRepository extends JpaRepository<AllergyEntity, Long> {

    @Query("SELECT a FROM AllergyEntity a WHERE a.patient.id=:id")
    List<AllergyEntity> findAllByPatientId(@Param("id") Long patientId);
}
