package university.medicalrecordsdemo.model.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "physician")
public class PhysicianEntity extends UserEntity {

    // Medical license number
    private String medicalLicenseNumber;

    // List of specialties for the physician
    @ElementCollection
    @CollectionTable(name = "physician_specialties", joinColumns = @JoinColumn(name = "physician_id"))
    @Column(name = "specialty")
    private Set<SpecialtyType> specialties;

    // List of patients assigned to this physician
    @OneToMany(mappedBy = "physician")
    private Set<PatientEntity> patients;

    // List of appointments associated with this physician
    @OneToMany(mappedBy = "physician")
    private Set<AppointmentEntity> appointments;

}
