package university.medicalrecordsdemo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PrivilegeType {
    // Patient
    READ_ALL_PATIENTS,
    READ_PATIENT,
    WRITE_PATIENT,

    // Physician
    READ_PHYSICIAN,
    READ_GENERAL_PRACTITIONER,
    WRITE_PHYSICIAN,
    WRITE_GENERAL_PRACTITIONER,
    READ_ALL_PHYSICIANS,

    // Visitation
    READ_VISITATION,
    WRITE_VISITATION,
    READ_ALL_VISITATIONS,

    // Diagnosis
    READ_DIAGNOSIS,
    WRITE_DIAGNOSIS,
    READ_ALL_DIAGNOSES,

    // Sick Leave
    READ_SICK_LEAVE,
    WRITE_SICK_LEAVE,
    READ_ALL_SICK_LEAVES
}
