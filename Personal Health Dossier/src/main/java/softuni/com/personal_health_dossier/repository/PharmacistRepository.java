package softuni.com.personal_health_dossier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.com.personal_health_dossier.model.entities.PharmacistEntity;

import java.util.Optional;

@Repository
public interface PharmacistRepository extends JpaRepository<PharmacistEntity, Long> {
    Optional<PharmacistEntity> findByUsername(String username);

}
