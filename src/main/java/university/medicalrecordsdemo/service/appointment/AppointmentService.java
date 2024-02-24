package university.medicalrecordsdemo.service.appointment;

import java.util.Set;

import org.springframework.data.domain.Page;

import university.medicalrecordsdemo.dto.appointment.*;
import university.medicalrecordsdemo.dto.patient.PatientDto;
import university.medicalrecordsdemo.util.enums.AppointmentTableColumnsEnum;

public interface AppointmentService {
    Set<AppointmentDto> findAll();

    AppointmentDto findById(Long id);

    Page<AppointmentDto> findAllByPageAndSort(int page, int size, AppointmentTableColumnsEnum sortField, String sortDirection);
    
    AppointmentDto create(CreateAppointmentDto appointment);

    AppointmentDto update(Long id, UpdateAppointmentDto updateAppointmentDto);

    Set<AppointmentDto> findAppointmentsByPatient(PatientDto patient);

    void delete(Long id);
}
