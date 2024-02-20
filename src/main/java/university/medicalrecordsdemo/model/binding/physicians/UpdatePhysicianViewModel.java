package university.medicalrecordsdemo.model.binding.physicians;

import university.medicalrecordsdemo.model.binding.users.UpdateUserViewModel;
import university.medicalrecordsdemo.model.entity.SpecialtyType;

import java.util.Set;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePhysicianViewModel extends UpdateUserViewModel {
   
    @Size(min = 8, max = 8, message = "Medical UUID must be exact 8 symbols")
    @NotNull(message = "Medical License Number is mandatory")
    private String medicalLicenseNumber;

    @NotNull(message = "Specialties are mandatory")
    private Set<SpecialtyType> specialties;

}
