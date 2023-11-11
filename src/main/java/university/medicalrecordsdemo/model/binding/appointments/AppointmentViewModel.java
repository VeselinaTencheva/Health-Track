package university.medicalrecordsdemo.model.binding.appointments;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import university.medicalrecordsdemo.model.entity.DiagnosisEntity;
import university.medicalrecordsdemo.model.entity.PatientEntity;
import university.medicalrecordsdemo.model.entity.PhysicianEntity;
import university.medicalrecordsdemo.model.entity.SickLeaveEntity;
import university.medicalrecordsdemo.model.entity.TreatmentEntity;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class AppointmentViewModel {

    // Appointment Data
    private LocalDate date;

    private long id;

    private PatientEntity patient;

    private PhysicianEntity physician;

    private DiagnosisEntity diagnosis;

    private TreatmentEntity treatment;

    private SickLeaveEntity sickLeave;

}
