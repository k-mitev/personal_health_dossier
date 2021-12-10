package softuni.com.personal_health_dossier.model.entities.enums;

public enum BloodGroupEnum {
    A_POZ("A+"),
    A_NEG("A-"),
    B_POZ("B+"),
    B_NEG("B-"),
    AB_POZ("AB+"),
    AB_NEG("AB-"),
    O_POZ("O+"),
    O_NEG("O-");

    private String value;

    BloodGroupEnum(String value) {
        this.value = value;
    }


    public String toString() {
        return this.value;
    }

}
