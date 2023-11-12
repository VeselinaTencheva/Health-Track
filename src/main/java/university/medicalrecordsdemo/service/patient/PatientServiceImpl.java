package university.medicalrecordsdemo.service.patient;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.dto.patient.*;
import university.medicalrecordsdemo.dto.physician.PhysicianDto;
import university.medicalrecordsdemo.model.entity.PatientEntity;
import university.medicalrecordsdemo.model.entity.PhysicianEntity;
import university.medicalrecordsdemo.model.entity.RoleEntity;
import university.medicalrecordsdemo.model.entity.RoleType;
import university.medicalrecordsdemo.repository.PatientRepository;
import university.medicalrecordsdemo.repository.RoleRepository;
import university.medicalrecordsdemo.service.physician.PhysicianService;

@AllArgsConstructor
@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PhysicianService physicianService;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder encoder;

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
    public PatientDto create(PatientDto patientDto) {
        // Map PatientDto to PatientEntity
        PatientEntity patientEntity = modelMapper.map(patientDto, PatientEntity.class);

        // Map General Practitioner from DTO and set it to the PatientEntity
        PhysicianEntity physicianEntity = modelMapper.map(patientDto.getGeneralPractitioner(), PhysicianEntity.class);
        patientEntity.setPhysician(physicianEntity);

        // Set the patient role
        RoleEntity patientRole = roleRepository.findByAuthority(RoleType.ROLE_PATIENT);
        patientEntity.setRoles(Collections.singleton(patientRole));
        physicianEntity.setPassword(encoder.encode(patientDto.getPassword()));

        // Save the patient entity and map it back to DTO
        PatientEntity savedPatientEntity = patientRepository.save(patientEntity);
        return modelMapper.map(savedPatientEntity, PatientDto.class);
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
