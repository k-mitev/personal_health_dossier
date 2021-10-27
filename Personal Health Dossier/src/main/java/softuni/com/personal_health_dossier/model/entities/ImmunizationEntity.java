package softuni.com.personal_health_dossier.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "immunizations")
public class ImmunizationEntity extends BaseEntity {
    private String vaccine;
    private LocalDate immunizationDate;
    private String batchNumber;
    private String merchantCode;
    private String typeOfApplication;
    private Double dosage;  // In ml!!
    private String notes;
    private String vaccinatedBy;
    private PatientEntity patient;

    public ImmunizationEntity() {
    }

    @Column(name = "vaccine", nullable = false)
    public String getVaccine() {
        return vaccine;
    }

    public void setVaccine(String vaccine) {
        this.vaccine = vaccine;
    }

    @Column(name = "immunization_date", nullable = false)
    public LocalDate getImmunizationDate() {
        return immunizationDate;
    }

    public void setImmunizationDate(LocalDate immunizationDate) {
        this.immunizationDate = immunizationDate;
    }

    @Column(name = "batch_number")
    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    @Column(name = "merchant_code")
    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    @Column(name = "type_of_application", nullable = false)
    public String getTypeOfApplication() {
        return typeOfApplication;
    }

    public void setTypeOfApplication(String typeOfApplication) {
        this.typeOfApplication = typeOfApplication;
    }

    @Column(name = "dosage", nullable = false)
    public Double getDosage() {
        return dosage;
    }

    public void setDosage(Double dosage) {
        this.dosage = dosage;
    }

    @Column(name = "notes")
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Column(name = "vaccinated_by", nullable = false)
    public String getVaccinatedBy() {
        return vaccinatedBy;
    }

    public void setVaccinatedBy(String vaccinatedBy) {
        this.vaccinatedBy = vaccinatedBy;
    }

    @ManyToOne
    public PatientEntity getPatient() {
        return patient;
    }

    public void setPatient(PatientEntity patient) {
        this.patient = patient;
    }
}
