package softuni.com.personal_health_dossier.model.entities;

import softuni.com.personal_health_dossier.model.entities.enums.MedicalSpecialty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "doctors")
public class PhysicianEntity extends BaseEntityUsers {
    private String region;
    private MedicalSpecialty specialty;
    private List<UserRoleEntity> roles;
    private List<PatientEntity> patients;
    private List<MedicalCenterEntity> hospitals;
    private List<PrescriptionEntity> prescriptions;

    public PhysicianEntity() {
    }

    @Column(name = "region", nullable = false)
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @NotNull
    @Enumerated(EnumType.STRING)
    public MedicalSpecialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(MedicalSpecialty specialty) {
        this.specialty = specialty;
    }

    @ManyToMany
    public List<UserRoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRoleEntity> roles) {
        this.roles = roles;
    }

    @ManyToMany(mappedBy = "doctors")
    public List<PatientEntity> getPatients() {
        return patients;
    }

    public void setPatients(List<PatientEntity> patients) {
        this.patients = patients;
    }

    @ManyToMany
    public List<MedicalCenterEntity> getHospitals() {
        return hospitals;
    }

    public void setHospitals(List<MedicalCenterEntity> hospitals) {
        this.hospitals = hospitals;
    }

    @OneToMany(mappedBy = "issuedBy")
    public List<PrescriptionEntity> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<PrescriptionEntity> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public void addRole(UserRoleEntity roleEntity) {
        this.roles.add(roleEntity);
    }
}
