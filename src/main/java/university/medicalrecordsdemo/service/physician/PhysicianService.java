package university.medicalrecordsdemo.service.physician;

import java.util.Set;

import university.medicalrecordsdemo.dto.physician.*;

public interface PhysicianService {
    Set<PhysicianDto> findAll();

    PhysicianDto findById(Long id);

    PhysicianDto create(PhysicianDto physician);

    PhysicianDto update(Long id, PhysicianDto physician);

    void delete(Long id);
}
