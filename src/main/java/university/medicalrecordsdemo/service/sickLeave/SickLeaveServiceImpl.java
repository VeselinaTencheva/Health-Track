package university.medicalrecordsdemo.service.sickLeave;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.dto.appointment.AppointmentDto;
import university.medicalrecordsdemo.dto.sickLeave.*;
import university.medicalrecordsdemo.model.entity.AppointmentEntity;
import university.medicalrecordsdemo.model.entity.SickLeaveEntity;
import university.medicalrecordsdemo.repository.AppointmentRepository;
import university.medicalrecordsdemo.repository.SickLeaveRepository;
import university.medicalrecordsdemo.util.enums.SickLeaveTableColumnsEnum;
import jakarta.persistence.Query;

@AllArgsConstructor
@Service
public class SickLeaveServiceImpl implements SickLeaveService {

    private final SickLeaveRepository sickLeaveRepository;
    private final AppointmentRepository appointmentRepository;
    private final ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager entityManager;

    public Page<SickLeaveDto> findSickLeavesDynamically(Long patientId, Long physicianId, Pageable pageable) {
        String sortProperty = "start_date"; // default sorting property
        String sortOrder = "ASC"; // default sorting order

        if (pageable.getSort().isSorted()) {
            Sort.Order order = pageable.getSort().iterator().next(); // considering only the first sort condition for simplicity
            sortProperty = order.getProperty();
            sortOrder = order.getDirection().isAscending() ? "DESC" : "ASC";
        }

        String whereClause = "";
        if (patientId != null) {
            whereClause = "WHERE a.patient_id = " + patientId + " ";
        } else if (physicianId != null) {
            whereClause = "WHERE a.physician_id = " + physicianId +" ";
        }
        
        String orderByString = "";
        if (SickLeaveTableColumnsEnum.PATIENT_FULL_NAME.getColumnDbName().equals(sortProperty)) {
            orderByString = "CONCAT(upa.first_name, ' ', upa.last_name) ";
        } else if (SickLeaveTableColumnsEnum.PHYSICIAN_FULL_NAME.getColumnDbName().equals(sortProperty)) {
            orderByString = "CONCAT(uph.first_name, ' ', uph.last_name) ";
        } else if (SickLeaveTableColumnsEnum.START_DATE.getColumnDbName().equals(sortProperty)) {
            orderByString = "sl.start_date ";
        } else if (SickLeaveTableColumnsEnum.DURATION.getColumnDbName().equals(sortProperty)) {
            orderByString = "sl.duration ";
        } else if (SickLeaveTableColumnsEnum.APPOINTMENT.getColumnDbName().equals(sortProperty)) {
            orderByString = "a.id ";
        } else {
            orderByString = "sl.start_date ";
        }

        // Adjust this query to match your actual database schema and relationships
        String sql = "SELECT sl.* FROM sick_leave sl " +
                     "LEFT JOIN appointment a ON a.sick_leave_id = sl.id " +
                     "LEFT JOIN patient pa ON pa.id = a.patient_id " +
                     "LEFT JOIN user upa ON upa.id = pa.id " +
                     "LEFT JOIN physician ph ON ph.id = a.physician_id " +
                     "LEFT JOIN user uph ON uph.id = ph.id " +
                     whereClause + 
                     "ORDER BY " + orderByString + " " + sortOrder;

        Query query = entityManager.createNativeQuery(sql, SickLeaveEntity.class);

        // Manual pagination
        int pageSize = pageable.getPageSize();
        int firstResult = (int) pageable.getOffset();
        query.setFirstResult(firstResult);
        query.setMaxResults(pageSize);

        @SuppressWarnings("unchecked")
        List<SickLeaveEntity> result = query.getResultList();

        // Convert entities to DTOs
        List<SickLeaveDto> dtos = result.stream()
                                        .map(this::convertToSickLeaveDto)
                                        .collect(Collectors.toList());

        
        long totalRecords = 0;
        if (patientId != null) {
            totalRecords = sickLeaveRepository.countByPatientId(patientId);
        } else if (physicianId != null) {
            totalRecords = sickLeaveRepository.countByPhysicianId(patientId);
        } else {
            totalRecords = sickLeaveRepository.count();
        }

        return new PageImpl<>(dtos, pageable, totalRecords);
    }

    @Override
    public Set<SickLeaveDto> findAll() {
        return sickLeaveRepository.findAll().stream()
                .map(this::convertToSickLeaveDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Page<SickLeaveDto> findAllByPageAndSort(int page, int size, SickLeaveTableColumnsEnum sortField, String sortDirection) {
        Sort.Direction direction = Sort.Direction.ASC;
        if ("desc".equalsIgnoreCase(sortDirection)) {
            direction = Sort.Direction.DESC;
        }
        final String sortFieldString = sortField.getColumnName().toString();
        Sort sort = Sort.by(direction, sortFieldString);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<SickLeaveEntity> sickLeavePage = sickLeaveRepository.findAll(pageRequest);
        return sickLeavePage.map(this::convertToSickLeaveDto);
    }

    @Override
    public Page<SickLeaveDto> findAllByPhysicianId(Long physicianId, int page, int size, SickLeaveTableColumnsEnum sortField, String sortDirection) {
        Sort.Direction direction = Sort.Direction.ASC;
        if ("desc".equalsIgnoreCase(sortDirection)) {
            direction = Sort.Direction.DESC;
        }
        String sortFieldString = sortField.getColumnDbName();
        Sort sort = Sort.by(direction, sortFieldString);

        PageRequest pageRequest = PageRequest.of(page, size, sort);

        return findSickLeavesDynamically(null, physicianId, pageRequest);
    }

    @Override
    public Page<SickLeaveDto> findAllByPatientId(Long patientId, int page, int size, SickLeaveTableColumnsEnum sortField, String sortDirection) {
                Sort.Direction direction = Sort.Direction.ASC;
                if ("desc".equalsIgnoreCase(sortDirection)) {
                    direction = Sort.Direction.DESC;
                }
                String sortFieldString = sortField.getColumnDbName();
                Sort sort = Sort.by(direction, sortFieldString);
        
                PageRequest pageRequest = PageRequest.of(page, size, sort);
        
                return findSickLeavesDynamically(patientId, null, pageRequest);
    }

    @Override
    public SickLeaveDto findById(Long id) {
        return modelMapper
                .map(
                        this.sickLeaveRepository
                                .findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("Invalid ick Leave ID: " + id)),
                        SickLeaveDto.class);
    }

    @Override
    public SickLeaveDto create(CreateSickLeaveDto sickLeave) {
        return convertToSickLeaveDto(
                this.sickLeaveRepository.save(this.modelMapper.map(sickLeave, SickLeaveEntity.class)));
    }

    @Override
    public SickLeaveDto update(Long id, UpdateSickLeaveDto updateSickLeaveDto) {
        SickLeaveEntity sickLeave = modelMapper.map(updateSickLeaveDto, SickLeaveEntity.class);
        sickLeave.setId(id);
        return convertToSickLeaveDto(this.sickLeaveRepository.save(sickLeave));
    }

    @Override
    public void delete(Long id) {
        SickLeaveEntity sickLeave = sickLeaveRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Sick leave not found with ID: " + id));

        AppointmentEntity appointment = appointmentRepository.findBySickLeaveId(sickLeave.getId());
        if (appointment != null) {
            appointment.setSickLeave(null);
            appointmentRepository.save(appointment);
        }

        sickLeaveRepository.delete(sickLeave);
    }

    private SickLeaveDto convertToSickLeaveDto(SickLeaveEntity sickLeave) {
        final SickLeaveDto sickLeaveDto = modelMapper.map(sickLeave, SickLeaveDto.class);
        if (sickLeave.getAppointment() != null) {
            final AppointmentDto appointmentDto = modelMapper.map(sickLeave.getAppointment(), AppointmentDto.class);
            sickLeaveDto.setAppointmentDto(appointmentDto);
        }
        return sickLeaveDto;
    }
}
