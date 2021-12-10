package softuni.com.personal_health_dossier.model.services;

import softuni.com.personal_health_dossier.model.entities.enums.PrescriptionType;

public class PrescriptionAddServiceModel {
    private String physicianUsername;
    private String patientPin;
    private PrescriptionType type;
    private Integer usageTimes;
    private String description;

    public PrescriptionAddServiceModel() {
    }

    public String getPhysicianUsername() {
        return physicianUsername;
    }

    public void setPhysicianUsername(String physicianUsername) {
        this.physicianUsername = physicianUsername;
    }

    public String getPatientPin() {
        return patientPin;
    }

    public void setPatientPin(String patientPin) {
        this.patientPin = patientPin;
    }

    public PrescriptionType getPrescriptionType() {
        return type;
    }

    public void setPrescriptionType(PrescriptionType prescriptionType) {
        this.type = prescriptionType;
    }

    public Integer getUsageTimes() {
        return usageTimes;
    }

    public void setUsageTimes(Integer numberOfUsages) {
        this.usageTimes = numberOfUsages;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
