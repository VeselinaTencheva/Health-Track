package university.medicalrecordsdemo.service.patient;

import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.dto.patient.*;
import university.medicalrecordsdemo.model.entity.PatientEntity;
import university.medicalrecordsdemo.repository.PatientRepository;

@AllArgsConstructor
@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    // private final DiagnosisService diagnosisService;
    // private final AppointmentService appointmentService;

    @Override
    public Set<PatientDto> findAll() {
        return patientRepository.findAll().stream()
                .map(this::convertToPatientDto)
                .collect(Collectors.toSet());
    }

    // @Override
    // public Set<DiagnosisDto> findAllDiagnosesPerPatient(long id) {
    // List<AppointmentDto> appointmentsDTO = appointmentService
    // .findAppointmentsByPatient(modelMapper.map(findById(id),
    // PatientEntity.class));
    // List<AppointmentEntity> appointments = appointmentsDTO.stream()
    // .map((appointmentDTO) -> modelMapper.map(appointmentDTO, Appointment.class))
    // .collect(Collectors.toList());
    // List<DiagnosisDto> diagnoses =
    // diagnosisService.findAllByAppointments(appointments);
    // return diagnoses;
    // }

    @Override
    public PatientDto findById(Long id) {
        return convertToPatientDto(this.patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Patient ID: " + id)));
    }

    @Override
    public PatientDto create(PatientDto patient) {
        return convertToPatientDto(this.patientRepository.save(modelMapper.map(patient, PatientEntity.class)));
    }

    @Override
    public PatientDto update(Long id, UpdatePatientDto updatePatientDTO) {
        PatientEntity patient = modelMapper.map(updatePatientDTO, PatientEntity.class);
        patient.setId(id);
        return convertToPatientDto(this.patientRepository.save(patient));
    }

    @Override
    public void delete(Long id) {
        this.patientRepository.deleteById(id);
    }

    public PatientDto convertToPatientDto(PatientEntity patient) {
        return modelMapper.map(patient, PatientDto.class);
    }
}
