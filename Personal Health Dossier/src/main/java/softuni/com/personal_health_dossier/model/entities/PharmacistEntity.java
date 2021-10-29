package softuni.com.personal_health_dossier.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pharmacists")
public class PharmacistEntity extends BaseEntityUsers {

    private List<PrescriptionEntity> prescriptions;
    private LocalDate filledPrescriptionDate;
    private String region;

    public PharmacistEntity() {
    }


    @ManyToMany(mappedBy = "filledBy")
    public List<PrescriptionEntity> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<PrescriptionEntity> prescriptions) {
        this.prescriptions = prescriptions;
    }

    @Column(name = "filled_prescription_date")
    public LocalDate getFilledPrescriptionDate() {
        return filledPrescriptionDate;
    }

    public void setFilledPrescriptionDate(LocalDate filledPrescriptionDate) {
        this.filledPrescriptionDate = filledPrescriptionDate;
    }

    @Column(name = "region", nullable = false)
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
