package university.medicalrecordsdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import university.medicalrecordsdemo.model.entity.PrivilegeEntity;
import university.medicalrecordsdemo.model.entity.PrivilegeType;

public interface PrivilegeRepository extends JpaRepository<PrivilegeEntity, Long> {

    PrivilegeEntity findByName(PrivilegeType name);

}
