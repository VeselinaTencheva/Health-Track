package university.medicalrecordsdemo.service.treatment;

import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.dto.treatment.CreateTreatmentDto;
import university.medicalrecordsdemo.dto.treatment.TreatmentDto;
import university.medicalrecordsdemo.dto.treatment.UpdateTreatmentDto;
import university.medicalrecordsdemo.model.entity.TreatmentEntity;
import university.medicalrecordsdemo.repository.TreatmentRepository;
import university.medicalrecordsdemo.util.enums.TreatmentTableColumnsEnum;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public Page<TreatmentDto> findAllByPageAndSort(int page, int size, TreatmentTableColumnsEnum sortField, String sortDirection) {
        Sort.Direction direction = Sort.Direction.ASC;
        if ("desc".equalsIgnoreCase(sortDirection)) {
            direction = Sort.Direction.DESC;
        }
        final String sortFieldString = sortField.getColumnName().toString();
        Sort sort = Sort.by(direction, sortFieldString);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<TreatmentEntity> treatmentPage = treatmentRepository.findAll(pageRequest);
        return treatmentPage.map(this::convertToTreatmentDto);
    }

    @Override
    public TreatmentDto findById(Long id) {
        return modelMapper.map(this.treatmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Treatment ID: " + id)), TreatmentDto.class);
    }

    @Override
    public TreatmentDto create(CreateTreatmentDto treatment) {
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