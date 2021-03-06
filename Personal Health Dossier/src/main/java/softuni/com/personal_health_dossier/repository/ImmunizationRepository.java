package softuni.com.personal_health_dossier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.com.personal_health_dossier.model.entities.ImmunizationEntity;

import java.util.List;

@Repository
public interface ImmunizationRepository extends JpaRepository<ImmunizationEntity, Long> {

    @Query("SELECT i FROM ImmunizationEntity i WHERE i.patient.id=:id")
    List<ImmunizationEntity> findAllByPatient_Id(@Param("id") Long id);
}
