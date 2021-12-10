package softuni.com.personal_health_dossier.model.bindings;

import javax.validation.constraints.*;

public class PatientModifyKilosBindingModel {
    private Double kilos;

    public PatientModifyKilosBindingModel() {
    }

    @NotNull
    public Double getKilos() {
        return kilos;
    }

    public void setKilos(Double kilos) {
        this.kilos = kilos;
    }
}
