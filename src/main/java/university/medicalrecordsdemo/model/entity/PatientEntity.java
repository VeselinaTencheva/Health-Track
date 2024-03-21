package university.medicalrecordsdemo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "patient")
public class PatientEntity extends UserEntity {

    @Column(name = "has_insurance", nullable = false)
    private boolean hasInsurance;

    @ManyToOne
    @JoinColumn(name = "physician_id")
    private PhysicianEntity physician;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("patient")
    private Set<AppointmentEntity> appointments;

    public PatientEntity(String ssn, String firstName, String lastName, String gender,
        LocalDate birthDate, String username, String password, boolean hasInsurance, RoleEntity role) {
        super(ssn, firstName, lastName, gender, birthDate, username, password, role);
        this.hasInsurance = hasInsurance;
    }

}