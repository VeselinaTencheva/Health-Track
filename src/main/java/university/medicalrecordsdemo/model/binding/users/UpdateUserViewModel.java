package university.medicalrecordsdemo.model.binding.users;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public abstract class UpdateUserViewModel {
    @NotEmpty(message = "First name is required")
    private String firstName;

    @NotEmpty(message = "Last name is required")
    private String lastName;

    @NotEmpty(message = "Username is required")
    private String username;

    @NotNull(message = "Gender is required")
    private String gender;

    @NotNull(message = "Birth date is required")
    // Assuming date format is yyyy-MM-dd
    // @PastOrPresent(message = "Birth date cannot be in the future")
    private String birthDate;

    @NotEmpty(message = "SSN is required")
    // Assuming SSN format is ###-##-#### currently not validating for this format
    private String ssn;

}
