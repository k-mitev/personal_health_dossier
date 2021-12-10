package softuni.com.personal_health_dossier.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.com.personal_health_dossier.model.entities.AllergyEntity;
import softuni.com.personal_health_dossier.model.entities.PatientEntity;
import softuni.com.personal_health_dossier.model.entities.enums.BloodGroupEnum;

import java.util.List;
import java.util.Optional;

@Repository

public interface PatientRepository extends JpaRepository<PatientEntity, Long> {

    Optional<PatientEntity> findByUsername(String username);

    Optional<PatientEntity> findByPersonalIdentificationNumber(String pin);

    List<PatientEntity> findAllByAgeEndingWith(String age);

    List<PatientEntity> findAllByAgeIsNotContaining(String age);

    @Modifying
    @Query("UPDATE PatientEntity pe SET pe.age=:age WHERE pe.id=:id")
    void updatePatientsAge(@Param("age") String age, @Param("id") Long id);

    @Modifying
    @Query("UPDATE PatientEntity pe SET pe.kilos=:kilos WHERE pe.id=:id")
    void updatePatientKilos(@Param("kilos") Double kilos, @Param("id") Long id);

    @Modifying
    @Query("UPDATE PatientEntity pe SET pe.height=:height WHERE pe.id=:id")
    void updatePatientHeight(@Param("height") Integer height, @Param("id") Long id);

    @Modifying
    @Query("UPDATE PatientEntity pe SET pe.bloodGroup=:bloodGroup WHERE pe.id=:id")
    void updatePatientBloodGroup(@Param("bloodGroup") BloodGroupEnum bloodGroup, @Param("id") Long id);

    @Modifying
    @Query("UPDATE PatientEntity pe SET pe.consentForOrganDonationAfterDeath=:consent WHERE pe.id=:id")
    void updateConsentForDonation(@Param("consent") boolean consentForOrganDonationAfterDeath, Long id);

    @Modifying
    @Query("UPDATE PatientEntity pe SET pe.username=:username, pe.firstName=:firstName, " +
            "pe.middleName=:middleName, pe.lastName=:lastName, pe.mobileNumber=:mobileNumber, " +
            "pe.password=:password,  pe.imgUrl=:imgUrl WHERE pe.id=:id")
    void updatePatientEntity(@Param("username") String username, @Param("firstName") String firstName,
                             @Param("middleName") String middleName, @Param("lastName") String lastName,
                             @Param("password") String password, @Param("imgUrl") String imgUrl,
                             @Param("mobileNumber") String mobileNumber, @Param("id") Long id);
}
