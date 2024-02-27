package university.medicalrecordsdemo.model.binding.appointments;

import lombok.*;
import university.medicalrecordsdemo.dto.diagnosis.DiagnosisDto;
import university.medicalrecordsdemo.dto.patient.PatientDto;
import university.medicalrecordsdemo.dto.physician.PhysicianDto;
import university.medicalrecordsdemo.dto.sickLeave.SickLeaveDto;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateAppointmentViewModel {
    private LocalDate date;

    private PatientDto patient;

    private PhysicianDto physician;

    private SickLeaveDto sickLeave;

    private DiagnosisDto diagnosis;

    private String treatment;

}
