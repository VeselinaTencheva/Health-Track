package university.medicalrecordsdemo.service.patient;

import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.dto.patient.*;
import university.medicalrecordsdemo.dto.physician.PhysicianDto;
import university.medicalrecordsdemo.model.entity.PatientEntity;
import university.medicalrecordsdemo.model.entity.PhysicianEntity;
import university.medicalrecordsdemo.repository.PatientRepository;
import university.medicalrecordsdemo.service.physician.PhysicianService;

@AllArgsConstructor
@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PhysicianService physicianService;
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
        final PatientEntity patientEntity = modelMapper.map(patient, PatientEntity.class);
        PhysicianEntity physicianEntity = modelMapper.map(patient.getGeneralPractitioner(), PhysicianEntity.class);
        patientEntity.setPhysician(physicianEntity);
        return convertToPatientDto(this.patientRepository.save(patientEntity));
    }

    @Override
    public PatientDto update(Long id, UpdatePatientDto updatePatientDTO) {
        String generalPractitionerId = updatePatientDTO.getPhysicianId();
        PatientEntity patient = modelMapper.map(updatePatientDTO, PatientEntity.class);

        Long physicianId = null;
        if (generalPractitionerId != null && !generalPractitionerId.isEmpty()) {
            physicianId = Long.valueOf(generalPractitionerId);
        }

        if (physicianId != null) {
            PhysicianEntity physicianEntity = modelMapper.map(physicianService
                    .findById(physicianId), PhysicianEntity.class);
            patient.setPhysician(physicianEntity);
        }

        PatientEntity prevousEntity = modelMapper.map(this.findById(id), PatientEntity.class);
        patient.setPassword(prevousEntity.getPassword());
        patient.setId(id);
        return convertToPatientDto(this.patientRepository.save(patient));
    }

    @Override
    public void delete(Long id) {
        this.patientRepository.deleteById(id);
    }

    public PatientDto convertToPatientDto(PatientEntity patient) {
        final PatientDto patientDto = modelMapper.map(patient, PatientDto.class);
        if (patient.getPhysician() != null) {
            final PhysicianDto physicianDto = modelMapper.map(patient.getPhysician(), PhysicianDto.class);
            patientDto.setGeneralPractitioner(physicianDto);
        }
        return patientDto;
    }
}
