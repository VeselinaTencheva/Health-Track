package university.medicalrecordsdemo.dto.appointment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import university.medicalrecordsdemo.dto.diagnosis.DiagnosisDto;
import university.medicalrecordsdemo.dto.patient.PatientDto;
import university.medicalrecordsdemo.dto.physician.PhysicianDto;
import university.medicalrecordsdemo.dto.sickLeave.SickLeaveDto;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CreateAppointmentDto {
    private LocalDate date;

    private PatientDto patient;

    private PhysicianDto physician;

    private SickLeaveDto sickLeave;

    private DiagnosisDto diagnosis;

    private String treatment;
}
