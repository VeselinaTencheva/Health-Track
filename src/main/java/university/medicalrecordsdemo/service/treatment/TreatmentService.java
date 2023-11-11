package university.medicalrecordsdemo.service.treatment;

import java.util.Set;

import university.medicalrecordsdemo.dto.treatment.*;

public interface TreatmentService {
    Set<TreatmentDto> findAll();

    TreatmentDto findById(Long id);

    TreatmentDto create(TreatmentDto treatment);

    TreatmentDto update(Long id, UpdateTreatmentDto treatment);

    void delete(Long id);
}
