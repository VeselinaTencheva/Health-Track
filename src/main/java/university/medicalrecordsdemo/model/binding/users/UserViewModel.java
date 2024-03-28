package university.medicalrecordsdemo.model.binding.users;

import java.time.LocalDate;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import university.medicalrecordsdemo.model.binding.BaseViewModel;
import university.medicalrecordsdemo.model.entity.RoleEntity;

@Getter
@Setter
public class UserViewModel extends BaseViewModel {
    private String firstName;

    private String ssn;

    private String lastName;

    private String username;

    private String password;

    private LocalDate birthDate;

    private String gender;

    private String confirmPassword;

    private boolean isAccountNonExpired = true;

    private boolean isAccountNonLocked = true;

    private boolean isCredentialsNonExpired = true;

    private boolean isEnabled = true;

    private Set<RoleEntity> roles;
}
