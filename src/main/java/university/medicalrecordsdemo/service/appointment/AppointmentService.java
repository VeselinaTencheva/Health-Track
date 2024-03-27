package university.medicalrecordsdemo.service.appointment;

import java.util.Set;

import org.springframework.data.domain.Page;

import university.medicalrecordsdemo.dto.appointment.*;
import university.medicalrecordsdemo.dto.patient.PatientDto;
import university.medicalrecordsdemo.model.entity.RoleType;
import university.medicalrecordsdemo.util.enums.AppointmentTableColumnsEnum;

public interface AppointmentService {
    Set<AppointmentDto> findAll();

    AppointmentDto findById(Long id);

    Page<AppointmentDto> findAllByPageAndSort(int page, int size, AppointmentTableColumnsEnum sortField, String sortDirection);

    Page<AppointmentDto> findAllByUserId(Long userId, RoleType roleType, int page, int size, AppointmentTableColumnsEnum sortField, String sortDirection);
    
    AppointmentDto create(CreateAppointmentDto appointment);

    AppointmentDto update(Long id, UpdateAppointmentDto updateAppointmentDto);

    Set<AppointmentDto> findAppointmentsByPatient(PatientDto patient);

    Set<AppointmentDto> findAppointmentsByPatientId(Long patientId);

    void delete(Long id);
}
