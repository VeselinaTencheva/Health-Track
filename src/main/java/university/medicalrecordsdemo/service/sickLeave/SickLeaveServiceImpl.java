package university.medicalrecordsdemo.service.sickLeave;

import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.dto.sickLeave.*;
import university.medicalrecordsdemo.model.entity.SickLeaveEntity;
import university.medicalrecordsdemo.repository.SickLeaveRepository;

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
    public SickLeaveDto findById(Long id) {
        return modelMapper
                .map(
                        this.sickLeaveRepository
                                .findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("Invalid ick Leave ID: " + id)),
                        SickLeaveDto.class);
    }

    @Override
    public SickLeaveDto create(SickLeaveDto sickLeave) {
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
        return modelMapper.map(sickLeave, SickLeaveDto.class);
    }
}
