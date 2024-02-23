package university.medicalrecordsdemo.service.diagnosis;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashSet;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.repository.DiagnosisRepository;
import university.medicalrecordsdemo.util.enums.DiagnosisTableColumnsEnum;
import university.medicalrecordsdemo.dto.diagnosis.DiagnosisDto;
import university.medicalrecordsdemo.dto.diagnosis.UpdateDiagnosisDto;
import university.medicalrecordsdemo.model.entity.AppointmentEntity;
import university.medicalrecordsdemo.model.entity.DepartmentType;
import university.medicalrecordsdemo.model.entity.DiagnosisEntity;

@Service
@AllArgsConstructor
public class DiagnosisServiceImpl implements DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;

    // private final AppointmentRepository appointmentRepository;
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
        return modelMapper.map(diagnosis, DiagnosisDto.class);
    }
}
