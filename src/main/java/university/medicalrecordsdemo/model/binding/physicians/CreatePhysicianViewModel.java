package university.medicalrecordsdemo.model.binding.physicians;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import university.medicalrecordsdemo.model.binding.users.CreateUserViewModel;
import university.medicalrecordsdemo.model.entity.SpecialtyType;

// import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePhysicianViewModel extends CreateUserViewModel {

    // @NotBlank(message = "Medical UUID is mandatory")
    // @Digits(integer = 10, fraction = 0, message = "Medical UUID must contains
    // only digits")
    // @Size(min = 6, max = 6, message = "Medical UUID must be exact 6 integers")
    private String medicalLicenseNumber;

    private Set<SpecialtyType> specialties;
}
