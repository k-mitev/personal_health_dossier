package softuni.com.personal_health_dossier.model.entities;

import softuni.com.personal_health_dossier.model.entities.enums.PrescriptionType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "prescriptions")
public class PrescriptionEntity extends BaseEntity {
    private PrescriptionType type;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String description;
    private Integer usageTimes;
    private PhysicianEntity issuedBy;
    private PatientEntity user;
    private List<PharmacistEntity> filledBy;

    public PrescriptionEntity() {
    }

    @NotNull
    @Enumerated(EnumType.STRING)
    public PrescriptionType getType() {
        return type;
    }

    public void setType(PrescriptionType type) {
        this.type = type;
    }

    @Column(name = "issue_date", nullable = false)
    public LocalDate getIssueDate() {
        return issueDate;
    }

    private void setIssueDate(LocalDate issueDate) {
        this.issueDate = LocalDate.now();
    }

    @Column(name = "expiry_date", nullable = false)
    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    private void setExpiryDate(LocalDate expiryDate) {
        if (this.getType().name().equals("WHITE")) {
            this.expiryDate = this.getIssueDate().plusMonths(6);
        } else {
            this.expiryDate = this.getIssueDate().plusDays(6);
        }

    }

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "usage_times")
    public Integer getUsageTimes() {
        return usageTimes;
    }

    public void setUsageTimes(Integer usageTimes) {
        this.usageTimes = usageTimes;
    }

    @ManyToOne
    public PhysicianEntity getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(PhysicianEntity issuedBy) {
        this.issuedBy = issuedBy;
    }

    @ManyToOne
    public PatientEntity getUser() {
        return user;
    }

    public void setUser(PatientEntity user) {
        this.user = user;
    }

    @ManyToMany
    public List<PharmacistEntity> getFilledBy() {
        return filledBy;
    }

    public void setFilledBy(List<PharmacistEntity> filledBy) {
        this.filledBy = filledBy;
    }
}
