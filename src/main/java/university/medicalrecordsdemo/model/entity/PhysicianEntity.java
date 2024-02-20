package university.medicalrecordsdemo.model.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "physician")
public class PhysicianEntity extends UserEntity {

    // Medical license number
    private String medicalLicenseNumber;

    // List of specialties for the physician
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "physician_specialties", joinColumns = @JoinColumn(name = "physician_id"))
    @Column(name = "specialty")
    private Set<SpecialtyType> specialties;


    // List of patients assigned to this physician
    @OneToMany(mappedBy = "physician")
    private Set<PatientEntity> patients;

    // List of appointments associated with this physician
    @OneToMany(mappedBy = "physician")
    private Set<AppointmentEntity> appointments;

    public PhysicianEntity(String ssn, String firstName, String lastName, String gender,
        String birthDate, String username, String password, String medicalLicenseNumber, Set<SpecialtyType> specialties, RoleEntity role) {
            super(ssn, firstName, lastName, gender, birthDate, username, password, role);
            this.medicalLicenseNumber = medicalLicenseNumber;
            this.specialties = specialties;
    }

}
