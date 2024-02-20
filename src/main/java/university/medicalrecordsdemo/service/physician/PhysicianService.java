package university.medicalrecordsdemo.service.physician;

import java.util.Set;

import university.medicalrecordsdemo.dto.physician.*;
import university.medicalrecordsdemo.model.entity.SpecialtyType;

public interface PhysicianService {
    Set<PhysicianDto> findAll();

    Set<PhysicianDto> findAllBySpecialty(SpecialtyType specialty);

    PhysicianDto findById(Long id);

    PhysicianDto create(PhysicianDto physician);

    PhysicianDto update(Long id, PhysicianDto physician);

    void delete(Long id);
}
