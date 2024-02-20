package university.medicalrecordsdemo.model.binding.users;

import lombok.Getter;
import lombok.Setter;
import university.medicalrecordsdemo.model.entity.RoleEntity;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

@Getter
@Setter
public abstract class CreateUserViewModel {
    @NotEmpty(message = "First name is required")
    private String firstName;

    @NotEmpty(message = "Last name is required")
    private String lastName;

    @NotEmpty(message = "Username is required")
    private String username;

    @NotEmpty(message = "Password is required")
    @Size(min = 7, message = "Password must be at least 8 characters long")
    private String password;


    @NotNull(message = "Gender is required")
    private String gender;

    @NotEmpty(message = "Birth date is required")
    // Assuming date format is yyyy-MM-dd
    // @PastOrPresent(message = "Birth date cannot be in the future")
    private String birthDate;

    @NotEmpty(message = "SSN is required")
    // Assuming SSN format is ###-##-#### currently not validating for this format
    private String ssn;

    private boolean isAccountNonExpired = true;

    private boolean isAccountNonLocked = true;

    private boolean isCredentialsNonExpired = true;

    private boolean isEnabled = true;

    private Set<RoleEntity> roles; // TODO maybe change to RoleViewModel

}
