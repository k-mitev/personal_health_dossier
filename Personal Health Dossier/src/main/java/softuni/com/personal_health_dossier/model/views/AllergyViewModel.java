package softuni.com.personal_health_dossier.model.views;

import java.time.LocalDate;


public class AllergyViewModel {
    private LocalDate registeredOnDate;
    private String doctor;
    private String allergens;

    public AllergyViewModel() {
    }

    public LocalDate getRegisteredOnDate() {
        return registeredOnDate;
    }

    public void setRegisteredOnDate(LocalDate registeredOnDate) {
        this.registeredOnDate = registeredOnDate;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getAllergens() {
        return allergens;
    }

    public void setAllergens(String allergens) {
        this.allergens = allergens;
    }
}
