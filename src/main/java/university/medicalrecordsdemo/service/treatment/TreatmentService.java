package university.medicalrecordsdemo.service.treatment;

import java.util.Set;

import org.springframework.data.domain.Page;

import university.medicalrecordsdemo.dto.treatment.*;
import university.medicalrecordsdemo.util.enums.TreatmentTableColumnsEnum;

public interface TreatmentService {
    Set<TreatmentDto> findAll();

    Page<TreatmentDto> findAllByPageAndSort(int page, int size, TreatmentTableColumnsEnum sortField, String sortDirection);

    TreatmentDto findById(Long id);

    TreatmentDto create(CreateTreatmentDto treatment);

    TreatmentDto update(Long id, UpdateTreatmentDto treatment);

    void delete(Long id);
}
