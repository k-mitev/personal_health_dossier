package softuni.com.personal_health_dossier.model.bindings;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

public class MedicalCenterAddBindingModel {

    private String name;
    private String address;
    private LocalDateTime hospitalizationDateAndTime;
    private LocalDateTime dischargeDateAndTime;
    private String medicalTreatment;
    private String patientPin;

    public MedicalCenterAddBindingModel() {
    }

    @NotBlank
    @Size(min = 5)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotBlank
    @Size(min = 5)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @PastOrPresent
    public LocalDateTime getHospitalizationDateAndTime() {
        return hospitalizationDateAndTime;
    }

    public void setHospitalizationDateAndTime(LocalDateTime hospitalizationDateAndTime) {
        this.hospitalizationDateAndTime = hospitalizationDateAndTime;
    }

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @PastOrPresent
    public LocalDateTime getDischargeDateAndTime() {
        return dischargeDateAndTime;
    }

    public void setDischargeDateAndTime(LocalDateTime dischargeDateAndTime) {
        this.dischargeDateAndTime = dischargeDateAndTime;
    }

    @NotBlank
    @Size(min = 10)
    public String getMedicalTreatment() {
        return medicalTreatment;
    }

    public void setMedicalTreatment(String medicalTreatment) {
        this.medicalTreatment = medicalTreatment;
    }

    @NotBlank
    @Pattern(regexp = "^\\d{10}$", message = "Can contain only digits!")
    public String getPatientPin() {
        return patientPin;
    }

    public void setPatientPin(String patientPin) {
        this.patientPin = patientPin;
    }
}
