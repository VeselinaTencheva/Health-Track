package university.medicalrecordsdemo.model.binding.patients;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import university.medicalrecordsdemo.model.binding.users.UpdateUserViewModel;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class UpdatePatientViewModel extends UpdateUserViewModel {

    @NotNull(message = "You need to specify the patient's paid insurance status")
    private boolean hasInsurance;

    @NotNull(message = "General Practitioner is mandatory")
    private String physicianId;
}
