package university.medicalrecordsdemo.model.binding.patients;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import university.medicalrecordsdemo.model.binding.users.CreateUserViewModel;
import university.medicalrecordsdemo.model.entity.PhysicianEntity;

// import javax.validation.constraints.*;

@Getter
@Setter
public class CreatePatientViewModel extends CreateUserViewModel {

    @NotNull(message = "You need to specify the patient's paid insurance status")
    private boolean hasInsurance;

    @NotNull(message = "General Practitioner is mandatory")
    private PhysicianEntity generalPractitioner;
}
