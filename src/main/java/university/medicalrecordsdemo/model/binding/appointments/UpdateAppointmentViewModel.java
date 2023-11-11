package university.medicalrecordsdemo.model.binding.appointments;

import university.medicalrecordsdemo.model.entity.DiagnosisEntity;
import university.medicalrecordsdemo.model.entity.PatientEntity;
import university.medicalrecordsdemo.model.entity.PhysicianEntity;
import university.medicalrecordsdemo.model.entity.SickLeaveEntity;
import university.medicalrecordsdemo.model.entity.TreatmentEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
// TODO generally take a look and fix the models 

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateAppointmentViewModel {
    // @NotBlank
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    // @PastOrPresent
    private LocalDate date;

    private PatientEntity patient;

    private PhysicianEntity physician;

    private SickLeaveEntity sickLeave;

    private DiagnosisEntity diagnosis;

    private TreatmentEntity treatment;
}
