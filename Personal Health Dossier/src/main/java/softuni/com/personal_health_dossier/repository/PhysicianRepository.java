package softuni.com.personal_health_dossier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.com.personal_health_dossier.model.entities.PhysicianEntity;

import java.util.Optional;

@Repository
public interface PhysicianRepository extends JpaRepository<PhysicianEntity, Long> {
    Optional<PhysicianEntity> findByUsername(String username);
}
