package university.medicalrecordsdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import university.medicalrecordsdemo.model.entity.RoleEntity;
import university.medicalrecordsdemo.model.entity.RoleType;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByAuthority(RoleType authority);

}
