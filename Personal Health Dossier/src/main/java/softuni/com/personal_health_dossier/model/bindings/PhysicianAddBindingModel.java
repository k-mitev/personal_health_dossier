package softuni.com.personal_health_dossier.model.bindings;

import org.springframework.web.multipart.MultipartFile;
import softuni.com.personal_health_dossier.model.entities.enums.MedicalSpecialty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class PhysicianAddBindingModel {
    private String username;
    private String firstName;
    private String middleName;
    private String lastName;
    private String password;
    private String physicianPin;
    private String mobileNumber;
    private String region;
    private MedicalSpecialty specialty;
    private MultipartFile img;

    public PhysicianAddBindingModel() {
    }

    @NotBlank
    @Pattern(regexp = "(^physician\\w+$)|(^physician[а-яА-Я0-9_]+$)")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotBlank
    @Pattern(regexp = "^([A-Z]{1}[a-z]{1,10}[-\\s]?([A-Z]{1}[a-z]{1,10})?)$|^([А-Я]{1}[а-я]{1,10}[-\\s]?([А-Я]{1}[а-я]{1,10})?)$",
            message = "Should start with capital letter and latin or cyrillic letters can be used.")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Pattern(regexp = "^([A-Z]{1}([a-z]{1,10}[-\\s]?([A-Z]{1}[a-z]{1,10})?)?)$|^([А-Я]{1}([а-я]{1,10}[-\\s]?([А-Я]{1}[а-я]{1,10})?)?)$|",
            message = "Should start with capital letter and latin or cyrillic letters can be used.")
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @NotBlank
    @Pattern(regexp = "^([A-Z]{1}[a-z]{1,10}[-\\s]?([A-Z]{1}[a-z]{1,10})?)$|^([А-Я]{1}[а-я]{1,10}[-\\s]?([А-Я]{1}[а-я]{1,10})?)$",
            message = "Should start with capital letter and latin or cyrillic letters can be used.")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NotBlank
    @Pattern(regexp = "\\w+|([а-яА-Я0-9_]+)")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotBlank
    @Pattern(regexp = "\\d{10}")
    public String getPhysicianPin() {
        return physicianPin;
    }

    public void setPhysicianPin(String physicianPin) {
        this.physicianPin = physicianPin;
    }

    @Pattern(regexp = "^\\+\\d{10,20}|$", message = "Must start with + and can contain only digits.")
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @NotBlank
    @Pattern(regexp = "^([A-Z]{1}[a-z]{1,10}[-\\s]?([A-Z]{1}[a-z]{1,10})?)$|^([А-Я]{1}[а-я]{1,10}[-\\s]?([А-Я]{1}[а-я]{1,10})?)$",
            message = "Should start with capital letter and latin or cyrillic letters can be used.")
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @NotNull
    public MedicalSpecialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(MedicalSpecialty specialty) {
        this.specialty = specialty;
    }

    public MultipartFile getImg() {
        return img;
    }

    public void setImg(MultipartFile img) {
        this.img = img;
    }
}
