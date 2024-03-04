package university.medicalrecordsdemo.util.enums;

import lombok.Getter;

@Getter
public enum DiagnosisTableColumnsEnum {
    CODE("code", "Code"),
    NAME("name", "Name"),
    DESCRIPTION("description", "Description"),
    CATEGORY("category", "Category");

    private final String columnName;
    private final String columnDisplayValue;

    private DiagnosisTableColumnsEnum(String columnName, String columnDisplayValue) {
        this.columnDisplayValue = columnDisplayValue;
        this.columnName = columnName;
    }
}
