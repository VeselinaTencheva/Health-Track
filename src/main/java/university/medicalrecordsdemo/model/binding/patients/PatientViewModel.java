package university.medicalrecordsdemo.model.binding.patients;

import lombok.Getter;
import lombok.Setter;
import university.medicalrecordsdemo.model.binding.users.UserViewModel;

@Getter
@Setter
public class PatientViewModel extends UserViewModel {

    private boolean hasInsurance;
    private long physicianId;
    private String physicianFullName;

    public String getFullname() {
        return this.getFirstName() + " " + this.getLastName();
    }

}
