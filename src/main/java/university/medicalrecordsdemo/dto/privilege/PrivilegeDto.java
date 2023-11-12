package university.medicalrecordsdemo.dto.privilege;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import university.medicalrecordsdemo.dto.BaseDto;
import university.medicalrecordsdemo.dto.role.RoleDto;

@Getter
@Setter
@NoArgsConstructor
public class PrivilegeDto extends BaseDto {
    private String name;
    private Set<RoleDto> roles;
}
