package university.medicalrecordsdemo.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private String ssn;
    private String firstName;
    private String lastName;
    private String gender;
    private String birthDate;
    private String username; // email
    private String password;
    private boolean isAccountNonExpired = true; // Set default value
    private boolean isAccountNonLocked = true; // Set default value
    private boolean isCredentialsNonExpired = true; // Set default value
    private boolean isEnabled = true; // Set default value
}
