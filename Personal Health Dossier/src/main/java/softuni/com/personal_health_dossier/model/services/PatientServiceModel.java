package softuni.com.personal_health_dossier.model.services;

import softuni.com.personal_health_dossier.model.entities.enums.BloodGroupEnum;

public class PatientServiceModel {
    private Long id;
    private String username;
    private String firstName;
    private String middleName;
    private String lastName;
    private Double kilos;
    private Integer height;
    private String age;
    private boolean consentForOrganDonationAfterDeath;
    private BloodGroupEnum bloodGroup;
    private String mobileNumber;
    private String imgUrl;
    private String password;

    public PatientServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Double getKilos() {
        return kilos;
    }

    public void setKilos(Double kilos) {
        this.kilos = kilos;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public boolean isConsentForOrganDonationAfterDeath() {
        return consentForOrganDonationAfterDeath;
    }

    public void setConsentForOrganDonationAfterDeath(boolean consentForOrganDonationAfterDeath) {
        this.consentForOrganDonationAfterDeath = consentForOrganDonationAfterDeath;
    }

    public BloodGroupEnum getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(BloodGroupEnum bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
