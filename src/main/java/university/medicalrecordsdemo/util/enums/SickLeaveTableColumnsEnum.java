package university.medicalrecordsdemo.util.enums;

import lombok.Getter;

@Getter
public enum SickLeaveTableColumnsEnum  {
    PATIENT_FULL_NAME("patient", "Patient"),
    PHYSICIAN_FULL_NAME("physician", "Physician"),
    START_DATE("startDate", "Start Date"),
    DURATION("duration", "Duration"),
    APPOINTMENT("appointment", "Appointment");

    private final String columnName;
    private final String columnDiplayValue;

    private SickLeaveTableColumnsEnum(String columnName, String columnDisplayValue) {
        this.columnDiplayValue = columnDisplayValue;
        this.columnName = columnName;
    }
}
