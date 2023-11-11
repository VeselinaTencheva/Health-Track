package university.medicalrecordsdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import university.medicalrecordsdemo.model.entity.AppointmentEntity;
import university.medicalrecordsdemo.model.entity.DiagnosisEntity;

public interface DiagnosisRepository extends JpaRepository<DiagnosisEntity, Long> {
    DiagnosisEntity findByAppointments(AppointmentEntity appointment);
}