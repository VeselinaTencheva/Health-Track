package university.medicalrecordsdemo.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "appointment")
public class AppointmentEntity extends BaseEntity {

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    @ManyToOne
    @JoinColumn(name = "physician_id")
    private PhysicianEntity physician;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "sickLeave_id", referencedColumnName = "id")
    private SickLeaveEntity sickLeave;

    @ManyToOne()
    @JoinColumn(name = "diagnosis_id")
    private DiagnosisEntity diagnosis;

    @Column(name = "treatment")
    private String treatment;
}