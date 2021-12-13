package softuni.com.personal_health_dossier.model.views;

import java.time.LocalDateTime;

public class MedicalCenterViewModel {
    private String name;
    private LocalDateTime hospitalizationDateAndTime;
    private LocalDateTime dischargeDateAndTime;
    private String medicalTreatment;

    public MedicalCenterViewModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
