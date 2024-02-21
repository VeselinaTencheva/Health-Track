package university.medicalrecordsdemo.util.enums;

import lombok.Getter;

@Getter
public enum PhysicianTableColumnsEnum {
    FULL_NAME("fullname", "Full Name"),
    USERNAME("username", "Username"),
    MEDICAL_LICENSE_NUMBER("medicalLicenseNumber", "Medical License Number"),
    SSN("ssn", "Ssn"),
    BIRTH_DATE("birthDate", "Birth Date"),
    GENDER("gender", "Gender"),
    IS_GP("isGp", "Is GP"),
    PATIENTS_COUNT("patientsCount", "Patients Count");

    private final String columnName;
    private final String columnDiplayValue;

    private PhysicianTableColumnsEnum(String columnName, String columnDisplayValue) {
        this.columnDiplayValue = columnDisplayValue;
        this.columnName = columnName;
    }
}
