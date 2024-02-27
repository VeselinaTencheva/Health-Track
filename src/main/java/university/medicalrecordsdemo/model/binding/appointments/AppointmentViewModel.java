package university.medicalrecordsdemo.model.binding.appointments;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import university.medicalrecordsdemo.model.binding.diagnoses.DiagnoseViewModel;
import university.medicalrecordsdemo.model.binding.patients.PatientViewModel;
import university.medicalrecordsdemo.model.binding.physicians.PhysiciansViewModel;
import university.medicalrecordsdemo.model.binding.sickLeaves.SickLeaveViewModel;

@Getter
@Setter
@NoArgsConstructor
public class AppointmentViewModel {

    private String date;

    private long id;

    private PatientViewModel patient;

    private PhysiciansViewModel physician;

    private DiagnoseViewModel diagnosis;

    private String treatment;

    private SickLeaveViewModel sickLeave;

    public String getSickLeaveInfo() {
        return this.sickLeave.getStartDate() == null ? "" : this.sickLeave.getStartDate() + " - " + this.sickLeave.getDuration() + " days";
    }

    public String getPatientInfo() {
        return this.patient.getFirstName() + " " + this.patient.getLastName();
    }

    public String getPhysicianInfo() {
        return this.physician.getFirstName() + " " + this.physician.getLastName();
    }

    public String getDiagnosisInfo() {
        return this.diagnosis.getName();
    }

}
