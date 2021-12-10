package softuni.com.personal_health_dossier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.com.personal_health_dossier.model.entities.AllergyEntity;

@Repository
public interface AllergyRepository extends JpaRepository<AllergyEntity,Long> {
}
