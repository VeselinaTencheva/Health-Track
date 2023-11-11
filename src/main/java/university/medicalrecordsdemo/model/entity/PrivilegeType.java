package university.medicalrecordsdemo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PrivilegeType {
    READ_PATIENT,
    READ_PHYSICIAN,
    READ_GENERAL_PRACTITIONER,
    WRITE_PATIENT,
    WRITE_PHYSICIAN,
    WRITE_GENERAL_PRACTITIONER,
    READ_DIAGNOSIS,
    WRITE_DIAGNOSIS,
    READ_SICK_LEAVE,
    WRITE_SICK_LEAVE,
    READ_VISITATION,
    WRITE_VISITATION
}
