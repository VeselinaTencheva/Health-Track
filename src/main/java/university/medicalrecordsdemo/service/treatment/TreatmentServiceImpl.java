package university.medicalrecordsdemo.service.treatment;

import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.dto.treatment.TreatmentDto;
import university.medicalrecordsdemo.dto.treatment.UpdateTreatmentDto;
import university.medicalrecordsdemo.model.entity.TreatmentEntity;
import university.medicalrecordsdemo.repository.TreatmentRepository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TreatmentServiceImpl implements TreatmentService {

    private final TreatmentRepository treatmentRepository;
    private final ModelMapper modelMapper;

    @Override
    public Set<TreatmentDto> findAll() {
        return this.treatmentRepository.findAll().stream()
                .map(this::convertToTreatmentDto)
                .collect(Collectors.toSet());
    }

    @Override
    public TreatmentDto findById(Long id) {
        return modelMapper.map(this.treatmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Treatment ID: " + id)), TreatmentDto.class);
    }

    @Override
    public TreatmentDto create(TreatmentDto treatment) {
        return convertToTreatmentDto(
                this.treatmentRepository.saveAndFlush(this.modelMapper.map(treatment, TreatmentEntity.class)));
    }

    @Override
    public TreatmentDto update(Long id, UpdateTreatmentDto updateTreatmentDto) {
        TreatmentEntity treatment = modelMapper.map(updateTreatmentDto, TreatmentEntity.class);
        treatment.setId(id);
        return convertToTreatmentDto(this.treatmentRepository.save(treatment));
    }

    @Override
    public void delete(Long id) {
        this.treatmentRepository.deleteById(id);
    }

    private TreatmentDto convertToTreatmentDto(TreatmentEntity treatment) {
        return modelMapper.map(treatment, TreatmentDto.class);
    }
}