package university.medicalrecordsdemo.service.diagnosis;

import java.util.Set;

import university.medicalrecordsdemo.dto.diagnosis.DiagnosisDto;
import university.medicalrecordsdemo.dto.diagnosis.UpdateDiagnosisDto;
import university.medicalrecordsdemo.model.entity.AppointmentEntity;

public interface DiagnosisService {
    Set<DiagnosisDto> findAll();

    Set<DiagnosisDto> findAllByAppointments(Set<AppointmentEntity> appointments);

    DiagnosisDto findById(Long id);

    DiagnosisDto create(DiagnosisDto diagnosis);

    DiagnosisDto update(Long id, UpdateDiagnosisDto diagnosis);

    void delete(Long id);
}
