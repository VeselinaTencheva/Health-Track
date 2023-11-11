package university.medicalrecordsdemo.dto.patient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import university.medicalrecordsdemo.dto.user.UserDto;

@Getter
@Setter
@NoArgsConstructor
public class PatientDto extends UserDto {
    private String ssn;
    private boolean hasInsurance;

}
