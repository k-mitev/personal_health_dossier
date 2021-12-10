package softuni.com.personal_health_dossier.model.bindings;

import org.springframework.web.multipart.MultipartFile;
import softuni.com.personal_health_dossier.model.entities.enums.MedicalSpecialty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PhysicianEditProfileBindingModel {
    private String username;
    private String firstName;
    private String middleName;
    private String lastName;
    private String oldPassword;
    private String newPassword;
    private String mobileNumber;
    private String region;
    private MedicalSpecialty specialty;
    private MultipartFile img;

    public PhysicianEditProfileBindingModel() {
    }

    @NotNull
    @Size(max = 20,message = "Can't be longer than 20 symbols")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull
    @Size(max = 23)
    @Pattern(regexp = "^([A-Z]{1}[a-z]{1,10}[-\\s]?([A-Z]{1}[a-z]{1,10})?)$|^([А-Я]{1}[а-я]{1,10}[-\\s]?([А-Я]{1}[а-я]{1,10})?)$|",
            message = "Should start with capital letter and latin or cyrillic letters can be used.")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotNull
    @Size(max = 23)
    @Pattern(regexp = "^([A-Z]{1}([a-z]{1,10}[-\\s]?([A-Z]{1}[a-z]{1,10})?)?)$|^([А-Я]{1}([а-я]{1,10}[-\\s]?([А-Я]{1}[а-я]{1,10})?)?)$|",
            message = "Should start with capital letter and latin or cyrillic letters can be used.")
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @NotNull
    @Size(max = 23)
    @Pattern(regexp = "^([A-Z]{1}[a-z]{1,10}[-\\s]?([A-Z]{1}[a-z]{1,10})?)$|^([А-Я]{1}[а-я]{1,10}[-\\s]?([А-Я]{1}[а-я]{1,10})?)$|",
            message = "Should start with capital letter and latin or cyrillic letters can be used.")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NotNull
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    @NotNull
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @NotNull
    @Pattern(regexp = "^\\+\\d{10,20}|$", message = "Must start with + and can contain only digits.")
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @NotNull
    @Size(max = 23)
    @Pattern(regexp = "^([A-Z]{1}[a-z]{1,10}[-\\s]?([A-Z]{1}[a-z]{1,10})?)$|^([А-Я]{1}[а-я]{1,10}[-\\s]?([А-Я]{1}[а-я]{1,10})?)$|",
            message = "Should start with capital letter and latin or cyrillic letters can be used.")
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

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
