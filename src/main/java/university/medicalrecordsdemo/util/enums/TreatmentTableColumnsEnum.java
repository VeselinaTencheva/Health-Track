package university.medicalrecordsdemo.util.enums;

import lombok.Getter;

@Getter
public enum TreatmentTableColumnsEnum  {
    NAME("name", "Name"),
    DESCRIPTION("description", "Description"),
    APPOINTMENT("appointment", "Appointment");

    private final String columnName;
    private final String columnDiplayValue;

    private TreatmentTableColumnsEnum(String columnName, String columnDisplayValue) {
        this.columnDiplayValue = columnDisplayValue;
        this.columnName = columnName;
    }
}
