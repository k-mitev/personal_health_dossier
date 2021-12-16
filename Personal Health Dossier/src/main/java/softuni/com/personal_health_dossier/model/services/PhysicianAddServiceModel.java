package softuni.com.personal_health_dossier.model.services;

import softuni.com.personal_health_dossier.model.entities.enums.MedicalSpecialty;

public class PhysicianAddServiceModel {
    private String username;
    private String firstName;
    private String middleName;
    private String lastName;
    private String password;
    private String mobileNumber;
    private String region;
    private MedicalSpecialty specialty;
    private String imgUrl;
    private String physicianPin;

    public PhysicianAddServiceModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPhysicianPin() {
        return physicianPin;
    }

    public void setPhysicianPin(String physicianPin) {
        this.physicianPin = physicianPin;
    }
}
