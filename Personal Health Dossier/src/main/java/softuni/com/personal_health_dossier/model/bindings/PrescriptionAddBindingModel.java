package softuni.com.personal_health_dossier.model.bindings;

import softuni.com.personal_health_dossier.model.entities.enums.PrescriptionType;

import javax.validation.constraints.*;

public class PrescriptionAddBindingModel {
    private String patientPin;
    private PrescriptionType type;
    private Integer usageTimes;
    private String description;

    public PrescriptionAddBindingModel() {
    }

    @NotBlank
    @Pattern(regexp = "^\\d{10}$", message = "Can contain only digits!")
    public String getPatientPin() {
        return patientPin;
    }

    public void setPatientPin(String patientPin) {
        this.patientPin = patientPin;
    }

    @NotNull
    public PrescriptionType getPrescriptionType() {
        return type;
    }

    public void setPrescriptionType(PrescriptionType prescriptionType) {
        this.type = prescriptionType;
    }

    @Min(value = 0)
    public Integer getUsageTimes() {
        return usageTimes;
    }

    public void setUsageTimes(Integer numberOfUsages) {
        this.usageTimes = numberOfUsages;
    }

    @NotBlank
    @Size(min = 3)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
