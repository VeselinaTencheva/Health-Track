package university.medicalrecordsdemo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@AllArgsConstructor
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

    @OneToOne
    @JoinColumn(name = "sick_leave_id")
    private SickLeaveEntity sickLeave;

    @ManyToOne()
    @JoinColumn(name = "diagnosis_id")
    private DiagnosisEntity diagnosis;

    @Column(name = "treatment")
    private String treatment;

    public AppointmentEntity(LocalDate date, PatientEntity patient, PhysicianEntity physician, String treatment) {
        this.date = date;
        this.patient = patient;
        this.physician = physician;
        this.treatment = treatment;
    }
}