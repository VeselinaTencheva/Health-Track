package university.medicalrecordsdemo.model.binding.sickLeaves;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SickLeaveViewModel {
    private long id;
    private LocalDate startDate;
    private int duration;

    private long patientId;
    private String patientFirstName;
    private String patientLastName;

    private long physicianId;
    private String physicianFirstName;
    private String physicianLastName;

    private long appointmentId;

    public String getPatientFullName() {
        return this.patientFirstName + " " + this.patientLastName;
    }

    public String getPhysicianFullName() {
        return this.physicianFirstName + " " + this.physicianLastName;
    }
}
