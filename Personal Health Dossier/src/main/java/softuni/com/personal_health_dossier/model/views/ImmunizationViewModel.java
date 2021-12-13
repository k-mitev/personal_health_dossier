package softuni.com.personal_health_dossier.model.views;

import java.time.LocalDate;

public class ImmunizationViewModel {
    private String vaccine;
    private LocalDate immunizationDate;
    private String batchNumber;
    private String merchantCode;
    private String typeOfApplication;
    private Double dosage;
    private String notes;
    private String vaccinatedBy;

    public ImmunizationViewModel() {
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

    public String getVaccinatedBy() {
        return vaccinatedBy;
    }

    public void setVaccinatedBy(String vaccinatedBy) {
        this.vaccinatedBy = vaccinatedBy;
    }
}
