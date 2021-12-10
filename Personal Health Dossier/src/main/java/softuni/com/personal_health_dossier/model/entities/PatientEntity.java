package softuni.com.personal_health_dossier.model.entities;


import softuni.com.personal_health_dossier.model.entities.enums.BloodGroupEnum;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patients")
public class PatientEntity extends BaseEntityUsers {
    private String imgUrl;
    private Double kilos;
    private Integer height;
    private String age;
    private boolean consentForOrganDonationAfterDeath;
    private BloodGroupEnum bloodGroup;
    private List<PhysicianEntity> doctors;
    private List<PrescriptionEntity> prescriptions;
    private List<MedicalCenterEntity> medicalCenters;
    private List<ImmunizationEntity> immunizations;
    private List<AllergyEntity> allergies;
    private List<PharmacistEntity> pharmacists;

    public PatientEntity(String pin) {
        this.setPersonalIdentificationNumber(pin);
        this.age = this.setTheAge();
    }

    public PatientEntity() {
        this.consentForOrganDonationAfterDeath = false;
        this.allergies = new ArrayList<>();
    }

    @Column(name = "img_url")
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Column(name = "kilos")
    public Double getKilos() {
        return kilos;
    }

    public void setKilos(Double kilos) {
        this.kilos = kilos;
    }

    @Column(name = "height")
    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    @Column(name = "age", nullable = false)
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = this.setTheAge();

    }

    @Column(name = "consent_for_organ_donation", nullable = false)
    public boolean isConsentForOrganDonationAfterDeath() {
        return consentForOrganDonationAfterDeath;
    }

    public void setConsentForOrganDonationAfterDeath(boolean consentForOrganDonationAfterDeath) {
        this.consentForOrganDonationAfterDeath = consentForOrganDonationAfterDeath;
    }

    @Enumerated(EnumType.STRING)
    public BloodGroupEnum getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(BloodGroupEnum bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    @ManyToMany
    public List<PhysicianEntity> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<PhysicianEntity> doctors) {
        this.doctors = doctors;
    }

    @OneToMany(mappedBy = "patient")
    public List<PrescriptionEntity> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<PrescriptionEntity> prescriptions) {
        this.prescriptions = prescriptions;
    }

    @OneToMany(mappedBy = "patient")
    public List<MedicalCenterEntity> getMedicalCenters() {
        return medicalCenters;
    }

    public void setMedicalCenters(List<MedicalCenterEntity> medicalCenters) {
        this.medicalCenters = medicalCenters;
    }

    @OneToMany(mappedBy = "patient")
    public List<ImmunizationEntity> getImmunizations() {
        return immunizations;
    }

    public void setImmunizations(List<ImmunizationEntity> immunizations) {
        this.immunizations = immunizations;
    }

    @OneToMany(mappedBy = "patient")
    public List<AllergyEntity> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<AllergyEntity> allergies) {
        this.allergies = allergies;
    }

    @ManyToMany
    public List<PharmacistEntity> getPharmacists() {
        return pharmacists;
    }

    public void setPharmacists(List<PharmacistEntity> pharmacists) {
        this.pharmacists = pharmacists;
    }

    public void addRole(UserRoleEntity userRoleEntity) {
        this.getRoles().add(userRoleEntity);
    }

    public void addAllergy(AllergyEntity allergyEntity) {
        this.allergies.add(allergyEntity);
    }

    private int getYear(String personalIdentificationNumber) {
        int yearFactor = Integer.parseInt(personalIdentificationNumber.substring(2, 3));
        int year = Integer.parseInt(personalIdentificationNumber.substring(0, 1)) >= 1 ?
                Integer.parseInt(personalIdentificationNumber.substring(0, 2)) :
                Integer.parseInt(personalIdentificationNumber.substring(1, 2));
        if (yearFactor > 1) {
            year += 2000;

        } else {
            year += 1900;
        }

        return year;
    }

    private int getMonth(String personalIdentificationNumber) {
        int yearFactor = Integer.parseInt(personalIdentificationNumber.substring(2, 3));
        int month;
        if (yearFactor > 1) {
            month = Integer.parseInt(personalIdentificationNumber.substring(2, 4)) - 40;

        } else {
            month = Integer.parseInt(personalIdentificationNumber.substring(2, 3)) == 1 ?
                    Integer.parseInt(personalIdentificationNumber.substring(2, 4)) :
                    Integer.parseInt(personalIdentificationNumber.substring(3, 4));
        }
        return month;
    }

    public String setTheAge() {
        String age = "";
        String personalIdentificationNumber = this.getPersonalIdentificationNumber();
        int year = this.getYear(personalIdentificationNumber);
        int month = this.getMonth(personalIdentificationNumber);
        int day = Integer.parseInt(personalIdentificationNumber.substring(4, 5)) >= 1 ?
                Integer.parseInt(personalIdentificationNumber.substring(4, 6)) :
                Integer.parseInt(personalIdentificationNumber.substring(5, 6));
        int currentYear = LocalDate.now().getYear();
        int currentMonthValue = LocalDate.now().getMonthValue();
        int currentDayOfMonth = LocalDate.now().getDayOfMonth();

        Period period = LocalDate.of(year, month, day).until(LocalDate.now());

        if (currentYear - year == 0) {
            if (currentMonthValue - month == 0) {
                age = String.format("%d day(s)", currentDayOfMonth - day);
            } else {
                if (period.getMonths() > 0) {
                    age = String.format("%d month(s)", period.getMonths());
                } else {
                    age = String.format("%d day(s)", period.getDays());
                }

            }

        } else {
            if (period.isNegative()) {
                age = "Invalid";
            } else {
                if (period.getYears() > 0) {
                    age = String.format("%d year(s)", period.getYears());
                } else {
                    if (period.getMonths() > 0) {
                        age = String.format("%d month(s)", period.getMonths());
                    } else {
                        age = String.format("%d day(s)", period.getDays());
                    }
                }
            }


        }

        return age;
    }


}
