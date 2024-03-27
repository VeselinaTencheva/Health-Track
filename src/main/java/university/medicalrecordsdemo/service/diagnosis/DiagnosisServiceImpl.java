package university.medicalrecordsdemo.service.diagnosis;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.repository.AppointmentRepository;
import university.medicalrecordsdemo.repository.DiagnosisRepository;
import university.medicalrecordsdemo.util.enums.DiagnosisTableColumnsEnum;
import university.medicalrecordsdemo.dto.diagnosis.DiagnosisDto;
import university.medicalrecordsdemo.dto.diagnosis.UpdateDiagnosisDto;
import university.medicalrecordsdemo.model.entity.AppointmentEntity;
import university.medicalrecordsdemo.model.entity.DepartmentType;
import university.medicalrecordsdemo.model.entity.DiagnosisEntity;
import university.medicalrecordsdemo.model.entity.PatientEntity;

@Service
@AllArgsConstructor
public class DiagnosisServiceImpl implements DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;

    private final AppointmentRepository appointmentRepository;
    // private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Override
    public Set<DiagnosisDto> findAll() {
        return diagnosisRepository.findAll().stream()
                .map(this::convertToDiagnosisDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<DiagnosisDto> findAllByCategory(Set<DepartmentType> categories) {
        Set<DiagnosisDto> diagnosisDtos = new HashSet<>();
    
        for (DepartmentType category : categories) {
            diagnosisDtos.addAll(diagnosisRepository.findAllByCategory(category).stream()
                .map(this::convertToDiagnosisDto)
                .collect(Collectors.toSet()));
        }
    
        return diagnosisDtos;
    }

    // TODO remove after confirming that the new method works
    @Override
    public Page<DiagnosisDto> findAllByPageAndSort(int page, int size, DiagnosisTableColumnsEnum sortField, String sortDirection) {
        Sort.Direction direction = Sort.Direction.ASC;
        if ("desc".equalsIgnoreCase(sortDirection)) {
            direction = Sort.Direction.DESC;
        }
        final String sortFieldString = sortField.getColumnName().toString();
        Sort sort = Sort.by(direction, sortFieldString);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<DiagnosisEntity> diagnosisPage = diagnosisRepository.findAll(pageRequest);
        return diagnosisPage.map(this::convertToDiagnosisDto);
    }

    @Override
    public Page<DiagnosisDto> findAllByPageAndSort(Pageable pageable) {
        Page<DiagnosisEntity> diagnosisPage = diagnosisRepository.findAll(pageable);
        return diagnosisPage.map(this::convertToDiagnosisDto);
    }

    @Override
    public Set<DiagnosisDto> findAllByAppointments(Set<AppointmentEntity> appointments) {
        Set<DiagnosisDto> diagnoses = new HashSet<>();

        appointments.forEach(appointment -> {
            final DiagnosisDto diagnosis = convertToDiagnosisDto(diagnosisRepository.findByAppointments(appointment));
            if (diagnosis != null) {
                diagnoses.add(diagnosis);
            }
        });

        return diagnoses;
    }

    @Override
    public Set<DiagnosisDto> findAllByPatient(Long patientId) {
        List<AppointmentEntity> appointments = appointmentRepository.findByPatientId(patientId);
        Set<DiagnosisDto> diagnosis = new HashSet<>();
        appointments.forEach(appointment -> {

            final DiagnosisEntity diagnose = diagnosisRepository.findByAppointments(appointment);
            if (diagnose != null){
                final DiagnosisDto diagnosisDto = convertToDiagnosisDto(diagnose);
                diagnosis.add(diagnosisDto);
            }
        });
        
        return diagnosis;
    }
    

    @Override
    public DiagnosisDto findById(Long id) {
        return convertToDiagnosisDto(this.diagnosisRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid diagnosis ID: " + id)));
    }

    @Override
    public DiagnosisDto create(DiagnosisDto diagnosis) {
        return convertToDiagnosisDto(this.diagnosisRepository.save(modelMapper.map(diagnosis, DiagnosisEntity.class)));
    }

    @Override
    public DiagnosisDto update(Long id, UpdateDiagnosisDto updateDiagnosisDto) {
        DiagnosisEntity diagnosis = modelMapper.map(updateDiagnosisDto, DiagnosisEntity.class);
        diagnosis.setId(id);
        return convertToDiagnosisDto(this.diagnosisRepository.save(diagnosis));
    }

    @Override
    public void delete(Long id) {
        this.diagnosisRepository.deleteById(id);
    }

    private DiagnosisDto convertToDiagnosisDto(DiagnosisEntity diagnosis) {
        final Set<AppointmentEntity> appointments = diagnosis.getAppointments() != null ? diagnosis.getAppointments() : new HashSet<>();

        final Set<PatientEntity> patients = new HashSet<>();
        final DiagnosisDto diagnosisDto = modelMapper.map(diagnosis, DiagnosisDto.class);
        if (appointments.size() > 0) {
            appointments.forEach(appointment -> patients.add(appointment.getPatient()));
            diagnosisDto.setPatientsCount(patients.size());
        } else {
            diagnosisDto.setPatientsCount(0);
        }
        
        return diagnosisDto;
    }
}
