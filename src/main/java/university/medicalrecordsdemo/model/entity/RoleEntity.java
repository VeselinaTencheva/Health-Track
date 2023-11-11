package university.medicalrecordsdemo.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "role")
public class RoleEntity extends BaseEntity implements GrantedAuthority {

    @Enumerated(EnumType.STRING)
    @Column(name = "authority", nullable = false, unique = true)
    private RoleType authority;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<UserEntity> users;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<PrivilegeEntity> privileges;

    @Override
    public String getAuthority() {
        return authority.name(); // Convert RoleType to String to resolve the compatibility issue with the
                                 // GrantedAuthority interface
    }
}