package university.medicalrecordsdemo.util.enums;

import lombok.Getter;

@Getter
public enum DiagnosisTableColumnsEnum {
    CODE("code", "Code"),
    NAME("name", "Name"),
    DESCRIPTION("description", "Description"),
    CATEGORY("category", "Category");

    private final String columnName;
    private final String columnDiplayValue;

    private DiagnosisTableColumnsEnum(String columnName, String columnDisplayValue) {
        this.columnDiplayValue = columnDisplayValue;
        this.columnName = columnName;
    }
}
