package university.medicalrecordsdemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import university.medicalrecordsdemo.model.entity.AppointmentEntity;
import university.medicalrecordsdemo.model.entity.DepartmentType;
import university.medicalrecordsdemo.model.entity.DiagnosisEntity;

public interface DiagnosisRepository extends JpaRepository<DiagnosisEntity, Long> {
    DiagnosisEntity findByAppointments(AppointmentEntity appointment);
    List<DiagnosisEntity> findAllByCategory(DepartmentType category);
}