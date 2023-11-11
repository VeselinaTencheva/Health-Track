package university.medicalrecordsdemo.dto.physician;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import university.medicalrecordsdemo.dto.user.UpdateUserDto;
import university.medicalrecordsdemo.model.entity.SpecialtyType;

@Getter
@Setter
@NoArgsConstructor
public class UpdatePhysicianDto extends UpdateUserDto {
    private Set<SpecialtyType> specialties;
}
