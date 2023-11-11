package university.medicalrecordsdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import university.medicalrecordsdemo.model.entity.SickLeaveEntity;

public interface SickLeaveRepository extends JpaRepository<SickLeaveEntity, Long> {
}
