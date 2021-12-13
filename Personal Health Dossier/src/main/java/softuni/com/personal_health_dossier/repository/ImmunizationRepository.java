package softuni.com.personal_health_dossier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.com.personal_health_dossier.model.entities.ImmunizationEntity;

import java.util.List;

@Repository
public interface ImmunizationRepository extends JpaRepository<ImmunizationEntity, Long> {

    List<ImmunizationEntity> findAllByPatient_Id(Long id);
}
