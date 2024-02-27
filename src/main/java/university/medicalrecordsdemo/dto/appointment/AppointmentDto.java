package university.medicalrecordsdemo.dto.appointment;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import university.medicalrecordsdemo.dto.diagnosis.DiagnosisDto;
import university.medicalrecordsdemo.dto.patient.PatientDto;
import university.medicalrecordsdemo.dto.physician.PhysicianDto;
import university.medicalrecordsdemo.dto.sickLeave.SickLeaveDto;

@Getter
@Setter
@NoArgsConstructor
public class AppointmentDto {
    private Long id;
    private LocalDate date;
    private PatientDto patient;
    private PhysicianDto physician;
    private SickLeaveDto sickLeave;
    private DiagnosisDto diagnosis;
    private String treatment;
}
