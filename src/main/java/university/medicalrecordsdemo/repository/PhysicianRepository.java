package university.medicalrecordsdemo.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import university.medicalrecordsdemo.model.entity.PhysicianEntity;
import university.medicalrecordsdemo.model.entity.SpecialtyType;

public interface PhysicianRepository extends JpaRepository<PhysicianEntity, Long> {
    List<PhysicianEntity> findAllBySpecialtiesIn(Set<SpecialtyType> specialties);
    List<PhysicianEntity> findAllBy(Pageable pageable);

}
