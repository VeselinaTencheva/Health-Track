package university.medicalrecordsdemo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DepartmentType {
    IMMUNOLOGY("Immunology"),
    ANESTHESIOLOGY("Anesthesiology"),
    CARDIOLOGY("Cardiology"),
    DERMATOLOGY("Dermatology"),
    ENDOCRINOLOGY("Endocrinology"),
    GASTROENTEROLOGY("Gastroenterology"),
    HEMATOLOGY("Hematology"),
    NEUROLOGY("Neurology"),
    ONCOLOGY("Oncology"),
    PATHOLOGY("Pathology"),
    PULMONOLOGY("Pulmonology"),
    NONE("");

    String value;

    // GENERAL_PRACTITIONER
}
