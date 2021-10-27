package softuni.com.personal_health_dossier.model.entities;

import softuni.com.personal_health_dossier.model.entities.enums.UserRoleEnum;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users_roles")
public class UserRoleEntity extends BaseEntity {

    private UserRoleEnum role;
    private List<PhysicianEntity> doctors;
    private List<PatientEntity> patients;
    private List<PharmacistEntity> pharmacists;

    public UserRoleEntity() {
    }


    @Enumerated(EnumType.STRING)
    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum role) {
        this.role = role;
    }

    @ManyToMany(mappedBy = "roles")
    public List<PhysicianEntity> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<PhysicianEntity> doctors) {
        this.doctors = doctors;
    }

    @ManyToMany(mappedBy = "roles")
    public List<PatientEntity> getPatients() {
        return patients;
    }

    public void setPatients(List<PatientEntity> patients) {
        this.patients = patients;
    }

    @ManyToMany(mappedBy = "roles")
    public List<PharmacistEntity> getPharmacists() {
        return pharmacists;
    }

    public void setPharmacists(List<PharmacistEntity> pharmacists) {
        this.pharmacists = pharmacists;
    }
}
