package university.medicalrecordsdemo.service.sickLeave;

import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.dto.appointment.AppointmentDto;
import university.medicalrecordsdemo.dto.sickLeave.*;
import university.medicalrecordsdemo.model.entity.SickLeaveEntity;
import university.medicalrecordsdemo.repository.SickLeaveRepository;
import university.medicalrecordsdemo.util.enums.SickLeaveTableColumnsEnum;

@AllArgsConstructor
@Service
public class SickLeaveServiceImpl implements SickLeaveService {

    private final SickLeaveRepository sickLeaveRepository;
    private final ModelMapper modelMapper;

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
        this.sickLeaveRepository.deleteById(id);
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
