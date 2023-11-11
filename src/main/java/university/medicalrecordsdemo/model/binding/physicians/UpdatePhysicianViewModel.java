package university.medicalrecordsdemo.model.binding.physicians;

import university.medicalrecordsdemo.model.entity.DepartmentType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePhysicianViewModel {

    // @NotBlank(message = "Name is mandatory")
    // @Size(min = 5, max=30, message = "Name must be between 5 and 30 characters")
    private String name;

    // @NotBlank(message = "Medical UUID is mandatory")
    // @Digits(integer = 10, fraction = 0, message = "Medical UUID must contains
    // only digits")
    // @Size(min = 6, max = 6, message = "Medical UUID must be exact 6 integers")
    private String medicalLicenseNumber;

    private DepartmentType departmentType;

    // @Size(min = 5, max = 5, message="Practise code must be exact 5 integers")
    // TODO add validation for size 5 but only when the field is not null
    private String practiceCode;

    // @Size(min = 5, message="Practise address must be at least 5 symbols")
    // TODO add validation for size 5 but only when the field is not null
    private String practiceAddress;

}
