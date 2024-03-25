package university.medicalrecordsdemo.util.enums;

import lombok.Getter;

@Getter
public enum AppointmentTableColumnsEnum {
    DATE("Date", "date"),
    PATIENT("Patient", "patient"),
    PHYSICIAN("Physician", "physician"),
    DIAGNOSIS("Diagnosis", "diagnosis"),
    SICK_LEAVE("Sick Leave Info", "sickLeave");


    private final String columnName;
    private final String columnDiplayValue;

    private AppointmentTableColumnsEnum(String columnDisplayValue, String columnName) {
        this.columnDiplayValue = columnDisplayValue;
        this.columnName = columnName;
    }
}
