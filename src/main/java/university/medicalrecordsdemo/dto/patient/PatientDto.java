package university.medicalrecordsdemo.dto.patient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import university.medicalrecordsdemo.dto.physician.PhysicianDto;
import university.medicalrecordsdemo.dto.user.UserDto;

@Getter
@Setter
@NoArgsConstructor
public class PatientDto extends UserDto {
    private boolean hasInsurance;
    private PhysicianDto generalPractitioner;
    private int diagnosisCount;
}
