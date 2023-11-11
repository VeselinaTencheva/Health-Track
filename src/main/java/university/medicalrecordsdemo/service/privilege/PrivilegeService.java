package university.medicalrecordsdemo.service.privilege;

import java.util.Set;

import university.medicalrecordsdemo.model.entity.PrivilegeEntity;

public interface PrivilegeService {
    Set<PrivilegeEntity> findAll();

    PrivilegeEntity findById(Long id);

    // Optional<PrivilegeEntity> findByName(String name);

    PrivilegeEntity create(PrivilegeEntity privilege);

    PrivilegeEntity update(Long id, PrivilegeEntity privilege);

    void delete(Long id);
}