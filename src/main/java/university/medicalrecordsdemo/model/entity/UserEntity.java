package university.medicalrecordsdemo.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "\"user\"")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserEntity extends BaseEntity implements UserDetails {

    @Column(name = "ssn", nullable = false, unique = true)
    private String ssn;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "username", nullable = false, unique = true)
    private String username; // email

    @Column(name = "password", nullable = false)
    private String password;

    @Column
    private boolean isAccountNonExpired;

    @Column
    private boolean isAccountNonLocked;

    @Column
    private boolean isCredentialsNonExpired;

    @Column
    private boolean isEnabled;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<RoleEntity> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }


    public UserEntity(String ssn, String firstName, String lastName, String gender, LocalDate birthDate, String username, String password, RoleEntity role) {
        this.ssn = ssn;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.username = username;
        this.password = password;
        this.isAccountNonExpired = false;
        this.isAccountNonLocked = false;
        this.isCredentialsNonExpired = false;
        this.isEnabled = false;
        this.roles = Set.of(role);
    }
}