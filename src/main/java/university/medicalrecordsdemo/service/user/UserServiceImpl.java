package university.medicalrecordsdemo.service.user;

import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.dto.role.RoleDto;
import university.medicalrecordsdemo.dto.user.UserDto;
import university.medicalrecordsdemo.model.entity.RoleEntity;
import university.medicalrecordsdemo.model.entity.RoleType;
import university.medicalrecordsdemo.model.entity.UserEntity;
import university.medicalrecordsdemo.repository.RoleRepository;
import university.medicalrecordsdemo.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    // private RoleRepository roleRepo;

    // @Override
    // public UserDetails loadUserByUsername(String username) throws
    // UsernameNotFoundException {
    // User user = userRepo.findByUsername(username);
    // if (user == null) {
    // throw new UsernameNotFoundException(username);
    // }
    // return user;
    //// return this.userRepo.findByEmail(email)
    //// .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    ////
    //// User user = userRepo.findByUsername(username);
    //// if (user == null) {
    //// return new org.springframework.security.core.userdetails.User(
    //// " ", " ", true, true, true, true,
    //// getAuthorities(Arrays.asList(
    //// roleRepo.findByAuthority("ROLE_USER"))));
    //// }
    ////
    //// return new org.springframework.security.core.userdetails.User(
    //// user.getEmail(), user.getPassword(), user.isEnabled(), true, true,
    //// true, getAuthorities(user.getAuthorities()));
    // }

    // private Collection<? extends GrantedAuthority> getAuthorities(
    // Collection<Role> roles) {
    //
    // return getGrantedAuthorities(getPrivileges(roles));
    // }

    // private List<String> getPrivileges(Collection<Role> roles) {
    //
    // List<String> privileges = new ArrayList<>();
    // List<Privilege> collection = new ArrayList<>();
    // for (Role role : roles) {
    // privileges.add(role.getAuthority());
    // collection.addAll(role.getPrivileges());
    // }
    // for (Privilege item : collection) {
    // privileges.add(item.getName());
    // }
    // return privileges;
    // }
    //
    // private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges)
    // {
    // List<GrantedAuthority> authorities = new ArrayList<>();
    // for (String privilege : privileges) {
    // authorities.add(new SimpleGrantedAuthority(privilege));
    // }
    // return authorities;
    // }

    // @Override
    // public User findUserByUserName(String username) {
    // return this.userRepo.findByUsername(username);
    // }
    //
    // @Override
    // public User findUserByEmail(String email) {
    // return this.userRepo.findByEmail(email);
    // }

    @Override
    public Set<UserEntity> findAllUsers() {
        return this.userRepository.findAll().stream()
                .collect(Collectors.toSet());
    }

    @Override
    public Set<UserDto> findAllByRole(RoleDto role) {
        final RoleEntity roleEntity = modelMapper.map(role, RoleEntity.class);
        return this.userRepository.findAllByRole(roleEntity).stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<UserDto> findAllByRoleType(RoleType roleType) {
        final RoleEntity roleEntity = roleRepository.findByAuthority(roleType);
        return this.userRepository.findAllByRole(roleEntity).stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public UserEntity findUserById(Long id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("ID not found"));
    }

    @Override
    public void deleteUser(UserEntity user) {
        this.userRepository.delete(user);
    }   

    @Override
    public UserEntity findUserByUserName(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

    private UserDto convertToUserDTO(UserEntity user) {
        return modelMapper.map(user, UserDto.class);
    }
}
