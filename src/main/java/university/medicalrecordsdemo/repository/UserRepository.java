package university.medicalrecordsdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import university.medicalrecordsdemo.model.entity.UserEntity;
import java.util.List;
import university.medicalrecordsdemo.model.entity.RoleEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // User findByUsername(String username);
    //
    // User findByEmail (String email);
    UserEntity findByUsername(String username);

    @Query("SELECT DISTINCT u FROM UserEntity u JOIN FETCH u.roles r WHERE r = :role")
    List<UserEntity> findAllByRole(@Param("role") RoleEntity role);
}