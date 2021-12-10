package softuni.com.personal_health_dossier.model.bindings;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class PatientModifyHeightBindingModel {
    private Integer height;

    public PatientModifyHeightBindingModel() {
    }

    @NotNull
    @Min(value = 10)
    @Max(value = 250)
    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
}
