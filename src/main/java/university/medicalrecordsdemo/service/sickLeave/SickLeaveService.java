package university.medicalrecordsdemo.service.sickLeave;

import java.util.Set;

import org.springframework.data.domain.Page;

import university.medicalrecordsdemo.dto.sickLeave.*;
import university.medicalrecordsdemo.util.enums.SickLeaveTableColumnsEnum;

public interface SickLeaveService {
    Set<SickLeaveDto> findAll();

    SickLeaveDto findById(Long id);

    Page<SickLeaveDto> findAllByPageAndSort(int page, int size, SickLeaveTableColumnsEnum sortField, String sortDirection);

    SickLeaveDto create(CreateSickLeaveDto sickLeave);

    SickLeaveDto update(Long id, UpdateSickLeaveDto sickLeave);

    void delete(Long id);
}