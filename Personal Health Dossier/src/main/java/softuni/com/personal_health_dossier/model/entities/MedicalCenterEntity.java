package softuni.com.personal_health_dossier.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;


@Entity
@Table(name = "medical_centers")
public class MedicalCenterEntity extends BaseEntity {
    private String name;
    private String address;
    private List<PhysicianEntity> doctors;
    private List<PatientEntity> patients;

    public MedicalCenterEntity() {
    }

    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @ManyToMany(mappedBy = "hospitals")
    public List<PhysicianEntity> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<PhysicianEntity> doctors) {
        this.doctors = doctors;
    }

    @ManyToMany(mappedBy = "medicalCenters")
    public List<PatientEntity> getPatients() {
        return patients;
    }

    public void setPatients(List<PatientEntity> patients) {
        this.patients = patients;
    }
}
