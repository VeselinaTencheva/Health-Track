package university.medicalrecordsdemo.service.physician;

import java.util.Set;

import org.springframework.data.domain.Page;
import university.medicalrecordsdemo.dto.physician.*;
import university.medicalrecordsdemo.model.entity.SpecialtyType;

public interface PhysicianService {
    Set<PhysicianDto> findAll();

    Set<PhysicianDto> findAllBySpecialty(SpecialtyType specialty);

    Page<PhysicianDto> findAllByPage(int page, int size);

    PhysicianDto findById(Long id);

    PhysicianDto create(PhysicianDto physician);

    PhysicianDto update(Long id, PhysicianDto physician);

    void delete(Long id);
}
