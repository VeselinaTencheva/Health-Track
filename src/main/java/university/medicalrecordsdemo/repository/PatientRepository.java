package university.medicalrecordsdemo.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import university.medicalrecordsdemo.model.entity.AppointmentEntity;
import university.medicalrecordsdemo.model.entity.PatientEntity;
import university.medicalrecordsdemo.model.entity.PhysicianEntity;

import java.util.List;

public interface PatientRepository extends JpaRepository<PatientEntity, Long> {

        List<PatientEntity> findAllBy(Pageable pageable);

        List<PatientEntity> findAllByAppointmentsIn(List<AppointmentEntity> appointments);

        List<PatientEntity> findByPhysician(PhysicianEntity physician);
}