package university.medicalrecordsdemo.service.diagnosis;

import java.util.Set;

import university.medicalrecordsdemo.dto.diagnosis.DiagnosisDto;
import university.medicalrecordsdemo.dto.diagnosis.UpdateDiagnosisDto;
import university.medicalrecordsdemo.model.entity.AppointmentEntity;
import university.medicalrecordsdemo.model.entity.DepartmentType;

public interface DiagnosisService {
    Set<DiagnosisDto> findAll();

    Set<DiagnosisDto> findAllByCategory(Set<DepartmentType> categories);

    Set<DiagnosisDto> findAllByAppointments(Set<AppointmentEntity> appointments);

    DiagnosisDto findById(Long id);

    DiagnosisDto create(DiagnosisDto diagnosis);

    DiagnosisDto update(Long id, UpdateDiagnosisDto diagnosis);

    void delete(Long id);
}
