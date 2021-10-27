package softuni.com.personal_health_dossier.model.entities;

import org.hibernate.annotations.Cache;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "allergies")
public class AllergyEntity extends BaseEntity {
    private LocalDate registeredOnDate;
    private List<AllergenEntity> allergens;
    private PhysicianEntity registeredBy;

    public AllergyEntity() {
    }

    @Column(name = "registered_on_date", nullable = false)
    public LocalDate getRegisteredOnDate() {
        return registeredOnDate;
    }

    public void setRegisteredOnDate(LocalDate registeredOnDate) {
        this.registeredOnDate = registeredOnDate;
    }

    @OneToMany
    public List<AllergenEntity> getAllergens() {
        return allergens;
    }

    public void setAllergens(List<AllergenEntity> allergens) {
        this.allergens = allergens;
    }

    @OneToOne
    public PhysicianEntity getRegisteredBy() {
        return registeredBy;
    }

    public void setRegisteredBy(PhysicianEntity registeredBy) {
        this.registeredBy = registeredBy;
    }
}
