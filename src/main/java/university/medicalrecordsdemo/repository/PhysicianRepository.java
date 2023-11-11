package university.medicalrecordsdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import university.medicalrecordsdemo.model.entity.PhysicianEntity;

public interface PhysicianRepository extends JpaRepository<PhysicianEntity, Long> {
}
