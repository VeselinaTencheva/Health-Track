package university.medicalrecordsdemo.service.appointment;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.dto.appointment.AppointmentDto;
import university.medicalrecordsdemo.dto.appointment.CreateAppointmentDto;
import university.medicalrecordsdemo.dto.appointment.UpdateAppointmentDto;
import university.medicalrecordsdemo.dto.diagnosis.DiagnosisDto;
import university.medicalrecordsdemo.dto.patient.PatientDto;
import university.medicalrecordsdemo.dto.physician.PhysicianDto;
import university.medicalrecordsdemo.dto.sickLeave.SickLeaveDto;
import university.medicalrecordsdemo.model.entity.AppointmentEntity;
import university.medicalrecordsdemo.model.entity.PatientEntity;
import university.medicalrecordsdemo.model.entity.RoleType;
import university.medicalrecordsdemo.repository.AppointmentRepository;
import university.medicalrecordsdemo.service.patient.PatientServiceImpl;
import university.medicalrecordsdemo.service.physician.PhysicianServiceImpl;
import university.medicalrecordsdemo.util.enums.AppointmentTableColumnsEnum;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PatientServiceImpl patientServiceImpl;
    private final PhysicianServiceImpl physicianServiceImpl;
    private final ModelMapper modelMapper;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<AppointmentDto> findAll() {
        return appointmentRepository.findAll().stream()
                .map(this::convertToAppointmentDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Page<AppointmentDto> findAllByPageAndSort(int page, int size, AppointmentTableColumnsEnum sortField, String sortDirection) {
        Sort.Direction direction = Sort.Direction.ASC;
        if ("desc".equalsIgnoreCase(sortDirection)) {
            direction = Sort.Direction.DESC;
        }
        final String sortFieldString = sortField.getColumnName().toString();
        Sort sort = Sort.by(direction, sortFieldString);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<AppointmentEntity> patientPage = appointmentRepository.findAll(pageRequest);
        return patientPage.map(this::convertToAppointmentDto);
    }

    public Page<AppointmentDto> findAppointmentsDynamically(Long userId, RoleType roleType, Pageable pageable) {
        String sortProperty = "date";
        String sortOrder = "ASC";

        if (pageable.getSort().isSorted()) {
            Sort.Order order = pageable.getSort().iterator().next();
            sortProperty = order.getProperty();
            sortOrder = order.getDirection().isAscending() ? "DESC" : "ASC";
        }

        String whereClause = "";
        Long totalRecords = 0L;
        if (RoleType.ROLE_PATIENT.equals(roleType)) {
            whereClause = "WHERE a.patient_id = " + userId + " ";
            totalRecords = appointmentRepository.countAppointmentsByPatientId(userId);
        } else if (RoleType.ROLE_PHYSICIAN.equals(roleType) || RoleType.ROLE_GENERAL_PRACTITIONER.equals(roleType)) {
            whereClause = "WHERE a.physician_id = " + userId +" ";
            totalRecords = appointmentRepository.countAppointmentsByPhysicianId(userId);
        } else {
            totalRecords = appointmentRepository.count();
        }
        
        String orderByString = "";
        if (AppointmentTableColumnsEnum.PATIENT.getColumnName().equals(sortProperty)) {
            orderByString = "CONCAT(upa.first_name, ' ', upa.last_name) " + sortOrder;
        } else if (AppointmentTableColumnsEnum.PHYSICIAN.getColumnName().equals(sortProperty)) {
            orderByString = "CONCAT(uph.first_name, ' ', uph.last_name) " + sortOrder;
        } else if (AppointmentTableColumnsEnum.DATE.getColumnName().equals(sortProperty)) {
            orderByString = "a.date " + sortOrder;
        } else if (AppointmentTableColumnsEnum.DIAGNOSIS.getColumnName().equals(sortProperty)) {
            orderByString = "d.name " + sortOrder;
        } else if (AppointmentTableColumnsEnum.SICK_LEAVE.getColumnName().equals(sortProperty)) {
            orderByString = "sk.start_date " + sortOrder + ", sk.duration " + sortOrder;
        } else {
            orderByString = "a.date " + sortOrder;
        }

        // Adjust this query to match your actual database schema and relationships
        String sql = "SELECT a.* FROM appointment a " +
                     "LEFT JOIN patient pa ON pa.id = a.patient_id " +
                     "LEFT JOIN user upa ON upa.id = pa.id " +
                     "LEFT JOIN physician ph ON ph.id = a.physician_id " +
                     "LEFT JOIN user uph ON uph.id = ph.id " +
                     "LEFT JOIN sick_leave sk ON sk.id = a.sick_leave_id " +
                     "LEFT JOIN diagnosis d ON d.id = a.diagnosis_id " +
                     whereClause + 
                     "ORDER BY " + orderByString;

        Query query = entityManager.createNativeQuery(sql, AppointmentEntity.class);

        // Manual pagination
        int pageSize = pageable.getPageSize();
        int firstResult = (int) pageable.getOffset();
        query.setFirstResult(firstResult);
        query.setMaxResults(pageSize);

        @SuppressWarnings("unchecked")
        List<AppointmentEntity> result = query.getResultList();

        List<AppointmentDto> dtos = result.stream()
                                        .map(this::convertToAppointmentDto)
                                        .collect(Collectors.toList());

        
        return new PageImpl<>(dtos, pageable, totalRecords);
    }

    @Override
    public Page<AppointmentDto> findAllByUserId(Long userId, RoleType roleType, int page, int size, AppointmentTableColumnsEnum sortField, String sortDirection) {
        Sort.Direction direction = Sort.Direction.ASC;
        if ("desc".equalsIgnoreCase(sortDirection)) {
            direction = Sort.Direction.DESC;
        }
        String sortFieldString = sortField.getColumnName();
        Sort sort = Sort.by(direction, sortFieldString);

        PageRequest pageRequest = PageRequest.of(page, size, sort);

        return findAppointmentsDynamically(userId, roleType, pageRequest);
    }

    @Override
    public AppointmentDto findById(Long id) {
        return modelMapper
                .map(
                        this.appointmentRepository
                                .findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("Invalid appointment ID: " + id)),
                        AppointmentDto.class);
    }

    @Override
    public AppointmentDto create(CreateAppointmentDto createAppointmentDto) {
        AppointmentEntity appointment = modelMapper.map(createAppointmentDto, AppointmentEntity.class);
        AppointmentEntity appointmentEntity = this.appointmentRepository.save(appointment);
        AppointmentDto appointmentDto = convertToAppointmentDto(appointmentEntity);
        return appointmentDto;
        // Appointment appointment = modelMapper.map(createAppointmentDto,
        // Appointment.class);
        // if (appointment.getSickLeave().getDuration() <= 0 &&
        // appointment.getSickLeave().getStartDate() == null) {
        // appointment.setSickLeave(null);
        // }
        // if (appointment.getTreatment().getName() == null) {
        // appointment.setTreatment(null);
        // }
    }

    @Override
    public AppointmentDto update(Long id, UpdateAppointmentDto AppointmentDto) {

        AppointmentEntity appointment = modelMapper.map(AppointmentDto, AppointmentEntity.class);
        appointment.setId(id);
        return convertToAppointmentDto(this.appointmentRepository.saveAndFlush(appointment));
    }

    @Override
    public Set<AppointmentDto> findAppointmentsByPatient(PatientDto patientDto) {
        PatientEntity patient = modelMapper.map(patientDto, PatientEntity.class);
        return appointmentRepository.findAllByPatient(patient).stream()
                .map(this::convertToAppointmentDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<AppointmentDto> findAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findByPatientId(patientId).stream()
            .map(this::convertToAppointmentDto)
            .collect(Collectors.toSet());
    }
    

    @Override
    public void delete(Long id) {
        this.appointmentRepository.deleteById(id);
    }

    private AppointmentDto convertToAppointmentDto(AppointmentEntity appointment) {
        // Map AppointmentEntity to AppointmentDTO
        AppointmentDto appointmentDTO = modelMapper.map(appointment, AppointmentDto.class);

        // You can map related objects if needed
        // For example, map patientEntity to PatientDTO
        PatientDto patientDTO = patientServiceImpl.convertToPatientDto(appointment.getPatient());
        appointmentDTO.setPatient(patientDTO);

        // Similarly, map physicianEntity to PhysicianDTO
        PhysicianDto physicianDTO = physicianServiceImpl.convertToPhysicianDTO(appointment.getPhysician());
        appointmentDTO.setPhysician(physicianDTO);

        // Map diagnosisEntity to DiagnosisDTO
        if (appointment.getDiagnosis() != null) {
            // return appointmentDTO;
            DiagnosisDto diagnosisDTO = modelMapper.map(appointment.getDiagnosis(), DiagnosisDto.class);
            appointmentDTO.setDiagnosis(diagnosisDTO);
        }

        if (appointment.getSickLeave() != null) {
            SickLeaveDto sickLeaveDTO = modelMapper.map(appointment.getSickLeave(), SickLeaveDto.class);
            appointmentDTO.setSickLeave(sickLeaveDTO);
        }
        return appointmentDTO;
    }
}