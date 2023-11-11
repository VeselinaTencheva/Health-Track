package university.medicalrecordsdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import university.medicalrecordsdemo.model.entity.TreatmentEntity;

public interface TreatmentRepository extends JpaRepository<TreatmentEntity, Long> {

}