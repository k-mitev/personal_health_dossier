package softuni.com.personal_health_dossier.model.bindings;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class AllergyAddBindingModel {
    private String patientPin;
    private LocalDate registrationDate;
    private String allergens;

    public AllergyAddBindingModel() {
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
    @PastOrPresent(message = "Date cannot be in the future!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    @NotBlank
    @Size(min = 3, message = "Should be at least 3 letters long!")
    @Pattern(regexp = "^([a-zA-Z]+[\\s\\-]?([a-zA-Z]+[\\s\\-]?)?,?([\\s]+)?)+$")
    public String getAllergens() {
        return allergens;
    }

    public void setAllergens(String allergens) {
        this.allergens = allergens;
    }
}
