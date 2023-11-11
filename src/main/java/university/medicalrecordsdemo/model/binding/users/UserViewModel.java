package university.medicalrecordsdemo.model.binding.users;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import university.medicalrecordsdemo.model.entity.RoleEntity;

@Getter
@Setter
public class UserViewModel {
    private String firstName;

    private String lastName;

    private String username;

    private String password;

    private String confirmPassword;

    private boolean isAccountNonExpired = true;

    private boolean isAccountNonLocked = true;

    private boolean isCredentialsNonExpired = true;

    private boolean isEnabled = true;

    private Set<RoleEntity> roles; // TODO maybe change to RoleViewModel
}
