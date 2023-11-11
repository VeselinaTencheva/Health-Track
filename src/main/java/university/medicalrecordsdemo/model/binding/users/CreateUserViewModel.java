package university.medicalrecordsdemo.model.binding.users;

import lombok.Getter;
import lombok.Setter;
import university.medicalrecordsdemo.model.entity.RoleEntity;

import java.util.Set;

@Getter
@Setter
public abstract class CreateUserViewModel { // TODO add validation
    private String firstName;

    private String lastName;

    private String username;

    private String password;

    private String confirmPassword;

    private String gender;

    private String birthDate;

    private String ssn;

    private boolean isAccountNonExpired = true;

    private boolean isAccountNonLocked = true;

    private boolean isCredentialsNonExpired = true;

    private boolean isEnabled = true;

    private Set<RoleEntity> roles; // TODO maybe change to RoleViewModel

}
