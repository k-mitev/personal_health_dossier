package softuni.com.personal_health_dossier.model.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "medical_centers")
public class MedicalCenterEntity extends BaseEntity {
    private String name;
    private String address;
    private LocalDateTime hospitalizationDateAndTime;
    private LocalDateTime dischargeDateAndTime;
    private String medicalTreatment;
    private List<PhysicianEntity> doctors;
    private PatientEntity patient;

    public MedicalCenterEntity() {
        this.doctors = new ArrayList<>();

    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "address", nullable = false)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "hospitalization_date_time", nullable = false)
    public LocalDateTime getHospitalizationDateAndTime() {
        return hospitalizationDateAndTime;
    }

    public void setHospitalizationDateAndTime(LocalDateTime dateAndTimeOfHospitalization) {
        this.hospitalizationDateAndTime = dateAndTimeOfHospitalization;
    }

    @Column(name = "discharge_date_time", nullable = false)
    public LocalDateTime getDischargeDateAndTime() {
        return dischargeDateAndTime;
    }

    public void setDischargeDateAndTime(LocalDateTime dateAndTimeOfDischarge) {
        this.dischargeDateAndTime = dateAndTimeOfDischarge;
    }

    @Column(name = "medical_treatment", nullable = false, columnDefinition = "TEXT")
    public String getMedicalTreatment() {
        return medicalTreatment;
    }

    public void setMedicalTreatment(String medicalTreatment) {
        this.medicalTreatment = medicalTreatment;
    }

    @ManyToMany
    public List<PhysicianEntity> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<PhysicianEntity> doctors) {
        this.doctors = doctors;
    }

    @ManyToOne
    public PatientEntity getPatient() {
        return patient;
    }

    public void setPatient(PatientEntity patient) {
        this.patient = patient;
    }


}
