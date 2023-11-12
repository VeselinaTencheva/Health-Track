package university.medicalrecordsdemo.dto.role;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import university.medicalrecordsdemo.dto.BaseDto;
import university.medicalrecordsdemo.dto.privilege.PrivilegeDto;
import university.medicalrecordsdemo.dto.user.UserDto;

@Getter
@Setter
@NoArgsConstructor
public class RoleDto extends BaseDto {
    private String authority;
    private Set<PrivilegeDto> privileges;
    private Set<UserDto> users;
}
