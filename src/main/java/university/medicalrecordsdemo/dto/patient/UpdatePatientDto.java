package university.medicalrecordsdemo.dto.patient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import university.medicalrecordsdemo.dto.user.UpdateUserDto;

@Getter
@Setter
@NoArgsConstructor
public class UpdatePatientDto extends UpdateUserDto {
    private boolean hasInsurance;
    private String physicianId;
}
