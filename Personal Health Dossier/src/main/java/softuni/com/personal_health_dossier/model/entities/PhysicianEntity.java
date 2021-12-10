package softuni.com.personal_health_dossier.model.entities;

import softuni.com.personal_health_dossier.model.entities.enums.MedicalSpecialty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "doctors")
public class PhysicianEntity extends BaseEntityUsers {
    private String imgUrl;
    private String region;
    private MedicalSpecialty specialty;
    private List<PatientEntity> patients;
    private List<MedicalCenterEntity> medicalCenters;
    private List<PrescriptionEntity> prescriptions;

    public PhysicianEntity() {
    }

    @Column(name = "img_url")
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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


    @ManyToMany(mappedBy = "doctors")
    public List<PatientEntity> getPatients() {
        return patients;
    }

    public void setPatients(List<PatientEntity> patients) {
        this.patients = patients;
    }

    @ManyToMany(mappedBy = "doctors")
    public List<MedicalCenterEntity> getMedicalCenters() {
        return medicalCenters;
    }

    public void setMedicalCenters(List<MedicalCenterEntity> hospitals) {
        this.medicalCenters = hospitals;
    }

    @OneToMany(mappedBy = "issuedBy")
    public List<PrescriptionEntity> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<PrescriptionEntity> prescriptions) {
        this.prescriptions = prescriptions;
    }


}
