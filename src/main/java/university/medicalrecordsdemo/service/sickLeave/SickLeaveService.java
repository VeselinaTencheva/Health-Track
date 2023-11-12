package university.medicalrecordsdemo.service.sickLeave;

import java.util.Set;

import university.medicalrecordsdemo.dto.sickLeave.*;

public interface SickLeaveService {
    Set<SickLeaveDto> findAll();

    SickLeaveDto findById(Long id);

    SickLeaveDto create(CreateSickLeaveDto sickLeave);

    SickLeaveDto update(Long id, UpdateSickLeaveDto sickLeave);

    void delete(Long id);
}