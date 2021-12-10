package softuni.com.personal_health_dossier.model.services;

import java.time.LocalDate;

public class ImmunizationAddServiceModel {
    private String patientPin;
    private String vaccine;//name
    private LocalDate immunizationDate;
    private String batchNumber;
    private String merchantCode;
    private String typeOfApplication;
    private Double dosage;  // In ml!!
    private String notes;
    private String physicianUsername;

    public ImmunizationAddServiceModel() {
    }

    public String getPatientPin() {
        return patientPin;
    }

    public void setPatientPin(String patientPin) {
        this.patientPin = patientPin;
    }

    public String getVaccine() {
        return vaccine;
    }

    public void setVaccine(String vaccine) {
        this.vaccine = vaccine;
    }

    public LocalDate getImmunizationDate() {
        return immunizationDate;
    }

    public void setImmunizationDate(LocalDate immunizationDate) {
        this.immunizationDate = immunizationDate;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getTypeOfApplication() {
        return typeOfApplication;
    }

    public void setTypeOfApplication(String typeOfApplication) {
        this.typeOfApplication = typeOfApplication;
    }

    public Double getDosage() {
        return dosage;
    }

    public void setDosage(Double dosage) {
        this.dosage = dosage;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPhysicianUsername() {
        return physicianUsername;
    }

    public void setPhysicianUsername(String physicianUsername) {
        this.physicianUsername = physicianUsername;
    }
}
