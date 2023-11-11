package university.medicalrecordsdemo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {
    ROLE_ADMIN,
    ROLE_PATIENT,
    ROLE_PHYSICIAN,
    ROLE_GENERAL_PRACTITIONER
}