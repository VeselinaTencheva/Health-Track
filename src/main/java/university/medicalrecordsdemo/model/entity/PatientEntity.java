package university.medicalrecordsdemo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "patient")
public class PatientEntity extends UserEntity {

    @Column(name = "has_insurance", nullable = false)
    private boolean hasInsurance;

    @ManyToOne
    @JoinColumn(name = "physician_id")
    private PhysicianEntity physician;

    @OneToMany(mappedBy = "patient")
    @JsonIgnoreProperties("patient")
    private Set<AppointmentEntity> appointments;

}