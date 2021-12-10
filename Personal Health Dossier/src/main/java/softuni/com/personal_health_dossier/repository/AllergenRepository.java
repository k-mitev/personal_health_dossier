package softuni.com.personal_health_dossier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.com.personal_health_dossier.model.entities.AllergenEntity;

import java.util.Optional;

@Repository
public interface AllergenRepository extends JpaRepository<AllergenEntity,Long> {
    Optional<AllergenEntity> findByName(String name);
}
