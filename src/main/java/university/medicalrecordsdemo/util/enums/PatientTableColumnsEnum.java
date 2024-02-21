package university.medicalrecordsdemo.util.enums;

import lombok.Getter;

@Getter
public enum PatientTableColumnsEnum {
    FULL_NAME("fullname", "Full Name"),
    USERNAME("username", "Username"),
    SSN("ssn", "Ssn"),
    BIRTH_DATE("birthDate", "Birth Date"),
    GENDER("gender", "Gender"),
    VALID_INSURANCE("hasInsurance", "Valid Insurance"),
    GP("gp", "GP");

    private final String columnName;
    private final String columnDiplayValue;

    private PatientTableColumnsEnum(String columnName, String columnDisplayValue) {
        this.columnDiplayValue = columnDisplayValue;
        this.columnName = columnName;
    }
}
