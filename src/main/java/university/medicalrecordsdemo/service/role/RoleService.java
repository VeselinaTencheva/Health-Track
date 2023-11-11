package university.medicalrecordsdemo.service.role;

import java.util.Set;

import university.medicalrecordsdemo.model.entity.RoleEntity;

public interface RoleService {
    Set<RoleEntity> findAll();

    RoleEntity findById(Long id);

    RoleEntity create(RoleEntity role);

    RoleEntity update(Long id, RoleEntity role);

    void delete(Long id);
}