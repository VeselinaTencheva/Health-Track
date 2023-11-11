package university.medicalrecordsdemo.service.patient;

import java.util.Set;

import university.medicalrecordsdemo.dto.patient.*;
// import university.medicalrecordsdemo.dto.diagnosis.*;

public interface PatientService {
    Set<PatientDto> findAll();

    // Set<DiagnosisDto> findAllDiagnosesPerPatient(long id);

    PatientDto findById(Long id);

    PatientDto create(PatientDto createPatientDTO);

    PatientDto update(Long id, UpdatePatientDto updatePatientDTO);

    void delete(Long id);
}