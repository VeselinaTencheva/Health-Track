package university.medicalrecordsdemo.service.patient;

import java.util.Set;

import org.springframework.data.domain.Page;

import university.medicalrecordsdemo.dto.patient.*;
// import university.medicalrecordsdemo.dto.diagnosis.*;
import university.medicalrecordsdemo.util.enums.PatientTableColumnsEnum;

public interface PatientService {
    Set<PatientDto> findAll();

    Page<PatientDto> findAllByPageAndSort(int page, int size, PatientTableColumnsEnum sortField, String sortDirection);

    Page<PatientDto> findAllByPage(int page, int size);

    PatientDto findById(Long id);

    PatientDto create(PatientDto createPatientDTO);

    PatientDto update(Long id, UpdatePatientDto updatePatientDTO);

    void delete(Long id);
}