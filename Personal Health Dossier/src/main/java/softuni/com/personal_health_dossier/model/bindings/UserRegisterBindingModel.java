package softuni.com.personal_health_dossier.model.bindings;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRegisterBindingModel {
    private String username;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String middleName;
    private String lastName;
    private String personalIdentificationNumber;

    public UserRegisterBindingModel() {
    }

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 2, message = "Username must be at least 2 characters")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 3, message = "Password must be at least 3 characters")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotBlank(message = "Confirm password cannot be blank")
    @Size(min = 3, message = "Confirm password must be at least 3 characters")
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, message = "First name must be at least 2 characters")
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

    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 2, message = "Last name must be at least 2 characters")
    @Pattern(regexp = "^([A-Z]{1}[a-z]{1,10}[-\\s]?([A-Z]{1}[a-z]{1,10})?)$|^([А-Я]{1}[а-я]{1,10}[-\\s]?([А-Я]{1}[а-я]{1,10})?)$",
            message = "Should start with capital letter and latin or cyrillic letters can be used.")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NotBlank(message = "Personal identification number cannot be blank")
    @Pattern(regexp = "^\\d{2}[012345]\\d[0-3]\\d\\d{4}$", message = "Can contain only digits!")
    public String getPersonalIdentificationNumber() {
        return personalIdentificationNumber;
    }

    public void setPersonalIdentificationNumber(String personalIdentificationNumber) {
        this.personalIdentificationNumber = personalIdentificationNumber;
    }
}
