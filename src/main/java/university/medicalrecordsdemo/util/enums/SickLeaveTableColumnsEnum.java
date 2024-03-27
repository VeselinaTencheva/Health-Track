package university.medicalrecordsdemo.util.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SickLeaveTableColumnsEnum  {
    PATIENT_FULL_NAME("patient", "Patient", "patient"),
    PHYSICIAN_FULL_NAME("physician", "Physician", "physician"),
    START_DATE("startDate", "Start Date", "start_date"),
    DURATION("duration", "Duration", "duration"),
    APPOINTMENT("appointment", "Appointment", "appointment"),;

    private final String columnName;
    private final String columnDiplayValue;
    private final String columnDbName;

}
