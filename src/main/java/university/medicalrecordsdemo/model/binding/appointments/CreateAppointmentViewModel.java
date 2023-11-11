package university.medicalrecordsdemo.model.binding.appointments;

import lombok.*;
import university.medicalrecordsdemo.model.entity.DiagnosisEntity;
import university.medicalrecordsdemo.model.entity.PatientEntity;
import university.medicalrecordsdemo.model.entity.PhysicianEntity;
import university.medicalrecordsdemo.model.entity.SickLeaveEntity;
import university.medicalrecordsdemo.model.entity.TreatmentEntity;

// import org.jetbrains.annotations.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

// import javax.validation.constraints.NotBlank;
// import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateAppointmentViewModel {
    // @NotBlank
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    // @PastOrPresent
    private LocalDate date;

    // @NotBlank
    private PatientEntity patient;

    // @NotBlank
    private PhysicianEntity physician;

    private SickLeaveEntity sickLeave;

    private DiagnosisEntity diagnosis;

    private TreatmentEntity treatment;

    public CreateAppointmentViewModel(LocalDate date, PatientEntity patient, PhysicianEntity physician,
            SickLeaveEntity sickLeave,
            DiagnosisEntity diagnosis) {
        this.date = date;
        this.patient = patient;
        this.physician = physician;
        this.sickLeave = sickLeave;
        this.diagnosis = diagnosis;
    }

    public CreateAppointmentViewModel(LocalDate date, PatientEntity patient, PhysicianEntity physician,
            DiagnosisEntity diagnosis) {
        this.date = date;
        this.patient = patient;
        this.physician = physician;
        this.diagnosis = diagnosis;
    }
}
