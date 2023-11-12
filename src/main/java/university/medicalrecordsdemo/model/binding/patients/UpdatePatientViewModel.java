package university.medicalrecordsdemo.model.binding.patients;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import university.medicalrecordsdemo.model.binding.users.UserViewModel;

// import org.springframework.format.annotation.DateTimeFormat;

// import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UpdatePatientViewModel extends UserViewModel {

    // @NotBlank(message = "Name is mandatory")
    // @Size(min = 5, max=30, message = "Name must be between 5 and 30 characters")

    // @NotBlank(message = "SSN is mandatory")
    // @Digits(integer = 10, fraction = 0, message = "SSN must contains only
    // digits")
    // @Size(min = 10, max = 10, message = "SSN must be exact 10 integers")
    private boolean hasInsurance;

    // @NotNull(message = "General Practitioner is mandatory")
    private String physicianId;
}
