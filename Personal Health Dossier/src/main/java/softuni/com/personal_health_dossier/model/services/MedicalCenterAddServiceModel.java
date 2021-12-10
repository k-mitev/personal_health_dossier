package softuni.com.personal_health_dossier.model.services;

import java.time.LocalDateTime;

public class MedicalCenterAddServiceModel {
    private String name;
    private String address;
    private LocalDateTime hospitalizationDateAndTime;
    private LocalDateTime dischargeDateAndTime;
    private String medicalTreatment;
    private String patientPin;
    private String physicianUsername;

    public MedicalCenterAddServiceModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getHospitalizationDateAndTime() {
        return hospitalizationDateAndTime;
    }

    public void setHospitalizationDateAndTime(LocalDateTime hospitalizationDateAndTime) {
        this.hospitalizationDateAndTime = hospitalizationDateAndTime;
    }

    public LocalDateTime getDischargeDateAndTime() {
        return dischargeDateAndTime;
    }

    public void setDischargeDateAndTime(LocalDateTime dischargeDateAndTime) {
        this.dischargeDateAndTime = dischargeDateAndTime;
    }

    public String getMedicalTreatment() {
        return medicalTreatment;
    }

    public void setMedicalTreatment(String medicalTreatment) {
        this.medicalTreatment = medicalTreatment;
    }

    public String getPatientPin() {
        return patientPin;
    }

    public void setPatientPin(String patientPin) {
        this.patientPin = patientPin;
    }

    public String getPhysicianUsername() {
        return physicianUsername;
    }

    public void setPhysicianUsername(String physicianUsername) {
        this.physicianUsername = physicianUsername;
    }
}
