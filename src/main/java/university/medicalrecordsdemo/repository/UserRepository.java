package university.medicalrecordsdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import university.medicalrecordsdemo.model.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // User findByUsername(String username);
    //
    // User findByEmail (String email);
    UserEntity findByUsername(String username);
}