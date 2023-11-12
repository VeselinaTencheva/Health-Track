package university.medicalrecordsdemo.service.appointment;

import java.util.Set;

import university.medicalrecordsdemo.dto.appointment.*;
import university.medicalrecordsdemo.dto.patient.PatientDto;

public interface AppointmentService {
    Set<AppointmentDto> findAll();

    AppointmentDto findById(Long id);

    AppointmentDto create(CreateAppointmentDto appointment);

    AppointmentDto update(Long id, UpdateAppointmentDto updateAppointmentDto);

    Set<AppointmentDto> findAppointmentsByPatient(PatientDto patient);

    void delete(Long id);
}
