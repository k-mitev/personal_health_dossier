package softuni.com.personal_health_dossier.model.bindings;

import softuni.com.personal_health_dossier.model.entities.enums.BloodGroupEnum;


import javax.validation.constraints.NotNull;

public class PatientModifyBloodGroupBindingModel {
    private BloodGroupEnum bloodGroup;


    public PatientModifyBloodGroupBindingModel() {
    }

    @NotNull
    public BloodGroupEnum getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(BloodGroupEnum bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
}
