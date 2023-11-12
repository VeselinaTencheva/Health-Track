package university.medicalrecordsdemo.model.binding.patients;

import lombok.Getter;
import lombok.Setter;
import university.medicalrecordsdemo.model.binding.users.UserViewModel;

@Getter
@Setter
public class PatientViewModel extends UserViewModel {
    // TODO fix all view models to have only the needed fieds from the properties
    // ex: Instead of private PhysiciansViewModel generalPractitioner; use private
    // long physicianId
    private boolean hasInsurance;
    private long physicianId;
    private String physicianFullName;
}
