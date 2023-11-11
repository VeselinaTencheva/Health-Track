package university.medicalrecordsdemo.service.user;

import java.util.Set;

import org.springframework.security.core.userdetails.UserDetailsService;

import university.medicalrecordsdemo.model.entity.UserEntity;

public interface UserService extends UserDetailsService // extends UserDetailsService //
{
    // UserEntity findUserByUserName(String username);

    // UserEntity findUserByEmail(String email);

    // UserServiceModel editUserProfile(UserServiceModel userServiceModel, String
    // oldPassword);

    Set<UserEntity> findAllUsers();

    UserEntity findUserById(Long id);

    void deleteUser(UserEntity user);
}