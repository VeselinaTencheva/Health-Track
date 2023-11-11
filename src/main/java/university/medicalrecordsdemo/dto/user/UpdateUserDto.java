package university.medicalrecordsdemo.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserDto {
    private String firstName;
    private String lastName;
    private String gender;

}
