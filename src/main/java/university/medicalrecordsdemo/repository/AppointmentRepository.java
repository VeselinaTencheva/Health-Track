package university.medicalrecordsdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import university.medicalrecordsdemo.model.entity.AppointmentEntity;
import university.medicalrecordsdemo.model.entity.DiagnosisEntity;
import university.medicalrecordsdemo.model.entity.PatientEntity;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
    List<AppointmentEntity> findAllByPatient(PatientEntity patient);

    List<AppointmentEntity> findAllByDiagnosis(DiagnosisEntity diagnosis);

    List<AppointmentEntity> findByDiagnosisId(Long diagnosisId);
}