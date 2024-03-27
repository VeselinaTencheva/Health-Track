package university.medicalrecordsdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import university.medicalrecordsdemo.model.entity.SickLeaveEntity;

public interface SickLeaveRepository extends JpaRepository<SickLeaveEntity, Long> {
    @Query("SELECT COUNT(sl) FROM SickLeaveEntity sl " +
    "JOIN sl.appointment a " +
    "WHERE a.patient.id = :patientId")
    long countByPatientId(@Param("patientId") Long patientId);

    @Query("SELECT COUNT(sl) FROM SickLeaveEntity sl " +
        "JOIN sl.appointment a " +
        "WHERE a.physician.id = :physicianId")
    long countByPhysicianId(@Param("physicianId") Long physicianId);
}
