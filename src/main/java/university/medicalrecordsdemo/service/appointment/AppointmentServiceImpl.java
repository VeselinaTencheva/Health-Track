package university.medicalrecordsdemo.service.appointment;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.dto.appointment.AppointmentDto;
import university.medicalrecordsdemo.dto.appointment.CreateAppointmentDto;
import university.medicalrecordsdemo.dto.appointment.UpdateAppointmentDto;
import university.medicalrecordsdemo.dto.patient.PatientDto;
import university.medicalrecordsdemo.model.entity.AppointmentEntity;
import university.medicalrecordsdemo.model.entity.PatientEntity;
import university.medicalrecordsdemo.repository.AppointmentRepository;
import university.medicalrecordsdemo.util.enums.AppointmentTableColumnsEnum;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final ModelMapper modelMapper;

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
        return convertToAppointmentDto(
                this.appointmentRepository.save(modelMapper.map(createAppointmentDto, AppointmentEntity.class)));
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
    public void delete(Long id) {
        this.appointmentRepository.deleteById(id);
    }

    private AppointmentDto convertToAppointmentDto(AppointmentEntity appointment) {
        return modelMapper.map(appointment, AppointmentDto.class);
    }
}