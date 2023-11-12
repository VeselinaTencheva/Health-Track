package university.medicalrecordsdemo.service.user;

import java.util.Set;

import org.springframework.security.core.userdetails.UserDetailsService;

import university.medicalrecordsdemo.dto.role.RoleDto;
import university.medicalrecordsdemo.dto.user.UserDto;
import university.medicalrecordsdemo.model.entity.RoleType;
import university.medicalrecordsdemo.model.entity.UserEntity;

public interface UserService extends UserDetailsService // extends UserDetailsService //
{
    // UserEntity findUserByUserName(String username);

    // UserEntity findUserByEmail(String email);

    // UserServiceModel editUserProfile(UserServiceModel userServiceModel, String
    // oldPassword);

    Set<UserDto> findAllByRole(RoleDto role);

    Set<UserDto> findAllByRoleType(RoleType role);

    Set<UserEntity> findAllUsers();

    UserEntity findUserById(Long id);

    void deleteUser(UserEntity user);
}