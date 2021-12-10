package softuni.com.personal_health_dossier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.com.personal_health_dossier.model.entities.PhysicianEntity;
import softuni.com.personal_health_dossier.model.entities.enums.MedicalSpecialty;

import java.util.Optional;

@Repository
public interface PhysicianRepository extends JpaRepository<PhysicianEntity, Long> {
    Optional<PhysicianEntity> findByUsername(String username);

    @Modifying
    @Query("UPDATE PhysicianEntity doc SET doc.username=:username, doc.firstName=:firstName, " +
            "doc.middleName=:middleName, doc.lastName=:lastName, doc.mobileNumber=:mobileNumber, " +
            "doc.password=:password, doc.region=:region, doc.specialty=:specialty, doc.imgUrl=:imgUrl WHERE doc.id=:id")
    void updatePhysicianEntity(@Param("username") String username, @Param("firstName") String firstName,
                               @Param("middleName") String middleName, @Param("lastName") String lastName, @Param("mobileNumber") String mobileNumber,
                               @Param("password") String password, @Param("region") String region, @Param("specialty") MedicalSpecialty specialty,
                               @Param("imgUrl") String imgUrl, @Param("id") Long id);
}
