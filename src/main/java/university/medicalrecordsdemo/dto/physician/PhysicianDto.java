package university.medicalrecordsdemo.dto.physician;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import university.medicalrecordsdemo.dto.user.UserDto;
import university.medicalrecordsdemo.model.entity.SpecialtyType;

@Getter
@Setter
@NoArgsConstructor
public class PhysicianDto extends UserDto {
    private String medicalLicenseNumber;
    private Set<SpecialtyType> specialties;
}
