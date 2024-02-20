package university.medicalrecordsdemo.dto.user;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserDto {
    private String firstName;
    private String ssn;
    private String lastName;
    private String username;
    private String gender;
    private LocalDate birthDate;
}
