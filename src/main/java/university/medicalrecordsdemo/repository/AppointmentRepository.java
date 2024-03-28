package university.medicalrecordsdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import university.medicalrecordsdemo.model.entity.AppointmentEntity;
import university.medicalrecordsdemo.model.entity.DiagnosisEntity;
import university.medicalrecordsdemo.model.entity.PatientEntity;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
    List<AppointmentEntity> findAllByPatient(PatientEntity patient);

    List<AppointmentEntity> findByPatientId(Long patientId);

    AppointmentEntity findBySickLeaveId(Long sickLeaveId);

    List<AppointmentEntity> findAllByDiagnosis(DiagnosisEntity diagnosis);

    List<AppointmentEntity> findByDiagnosisId(Long diagnosisId);

    @Query("SELECT COUNT(a) FROM AppointmentEntity a WHERE a.patient.id = :patientId")
    Long countAppointmentsByPatientId(@Param("patientId") Long patientId);

    @Query("SELECT COUNT(a) FROM AppointmentEntity a WHERE a.physician.id = :physicianId")
    Long countAppointmentsByPhysicianId(@Param("physicianId") Long physicianId);
}