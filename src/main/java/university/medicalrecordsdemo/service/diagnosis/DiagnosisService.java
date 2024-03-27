package university.medicalrecordsdemo.service.diagnosis;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import university.medicalrecordsdemo.dto.diagnosis.DiagnosisDto;
import university.medicalrecordsdemo.dto.diagnosis.UpdateDiagnosisDto;
import university.medicalrecordsdemo.model.entity.AppointmentEntity;
import university.medicalrecordsdemo.model.entity.DepartmentType;
import university.medicalrecordsdemo.util.enums.DiagnosisTableColumnsEnum;

public interface DiagnosisService {
    Set<DiagnosisDto> findAll();

    Set<DiagnosisDto> findAllByCategory(Set<DepartmentType> categories);

    Page<DiagnosisDto> findAllByPageAndSort(int page, int size, DiagnosisTableColumnsEnum sortField, String sortDirection);

    Page<DiagnosisDto> findAllByPageAndSort(Pageable pageable);

    Set<DiagnosisDto> findAllByAppointments(Set<AppointmentEntity> appointments);

    DiagnosisDto findById(Long id);

    Set<DiagnosisDto> findAllByPatient(Long patientId);

    DiagnosisDto create(DiagnosisDto diagnosis);

    DiagnosisDto update(Long id, UpdateDiagnosisDto diagnosis);

    void delete(Long id);
}
