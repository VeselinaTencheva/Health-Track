package university.medicalrecordsdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import university.medicalrecordsdemo.model.entity.PatientEntity;
import university.medicalrecordsdemo.model.entity.PhysicianEntity;

import java.util.List;

public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
        // List<PatientEntity> findDistinctByAppointments(List<AppointmentEntity>
        // appointments);

        List<PatientEntity> findByPhysician(PhysicianEntity physician);

}