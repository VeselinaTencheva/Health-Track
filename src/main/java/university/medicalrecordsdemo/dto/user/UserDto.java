package university.medicalrecordsdemo.dto.user;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import university.medicalrecordsdemo.dto.BaseDto;

@Getter
@Setter
@NoArgsConstructor
public class UserDto extends BaseDto {
    private String ssn;
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate birthDate;
    private String username; // email
    private String password;
    private boolean isAccountNonExpired = true; // Set default value
    private boolean isAccountNonLocked = true; // Set default value
    private boolean isCredentialsNonExpired = true; // Set default value
    private boolean isEnabled = true; // Set default value
}
