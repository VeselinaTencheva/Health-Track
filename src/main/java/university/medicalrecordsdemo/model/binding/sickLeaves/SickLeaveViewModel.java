package university.medicalrecordsdemo.model.binding.sickLeaves;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import university.medicalrecordsdemo.model.entity.AppointmentEntity;
import university.medicalrecordsdemo.model.entity.PatientEntity;
import university.medicalrecordsdemo.model.entity.PhysicianEntity;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SickLeaveViewModel {
    private long id;
    private LocalDate startDate;
    private int duration;
    private PatientEntity patient;
    private AppointmentEntity appointment;
    private PhysicianEntity physician; // TODO maybe change with PhysicianvieModel
}
