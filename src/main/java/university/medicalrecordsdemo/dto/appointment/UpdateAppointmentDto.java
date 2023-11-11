package university.medicalrecordsdemo.dto.appointment;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateAppointmentDto {
    private LocalDate date;
    private Long patientId; // ID of the patient
    private Long physicianId; // ID of the physician
    private Long sickLeaveId; // ID of the sick leave
    private Long diagnosisId; // ID of the diagnosis
    private Long treatmentId; // ID of the treatment

}
