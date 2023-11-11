package university.medicalrecordsdemo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SpecialtyType {
    // Immunology
    ALLERGY_AND_IMMUNOLOGY("Allergy and Immunology", DepartmentType.IMMUNOLOGY),
    CLINICAL_IMMUNOLOGY("Clinical Immunology", DepartmentType.IMMUNOLOGY),
    RHEUMATOLOGY("Rheumatology", DepartmentType.IMMUNOLOGY),

    // Anesthesiology
    ANESTHESIOLOGIST("Anesthesiologist", DepartmentType.ANESTHESIOLOGY),
    PEDIATRIC_ANESTHESIOLOGY("Pediatric Anesthesiology", DepartmentType.ANESTHESIOLOGY),
    PAIN_MEDICINE("Pain Medicine", DepartmentType.ANESTHESIOLOGY),

    // Cardiology
    INTERVENTIONAL_CARDIOLOGY("Interventional Cardiology", DepartmentType.CARDIOLOGY),
    ELECTROPHYSIOLOGY("Electrophysiology", DepartmentType.CARDIOLOGY),
    HEART_FAILURE_CARDIAC_TRANSPLANT("Heart Failure/Cardiac Transplant", DepartmentType.CARDIOLOGY),

    // Dermatology
    DERMATOPATHOLOGY("Dermatopathology", DepartmentType.DERMATOLOGY),
    PEDIATRIC_DERMATOLOGY("Pediatric Dermatology", DepartmentType.DERMATOLOGY),
    COSMETIC_DERMATOLOGY("Cosmetic Dermatology", DepartmentType.DERMATOLOGY),

    // Endocrinology
    PEDIATRIC_ENDOCRINOLOGY("Pediatric Endocrinology", DepartmentType.ENDOCRINOLOGY),
    REPRODUCTIVE_ENDOCRINOLOGY("Reproductive Endocrinology", DepartmentType.ENDOCRINOLOGY),
    DIABETES_AND_METABOLISM("Diabetes and Metabolism", DepartmentType.ENDOCRINOLOGY),

    // Gastroenterology
    HEPATOLOGY("Hepatology", DepartmentType.GASTROENTEROLOGY),
    PEDIATRIC_GASTROENTEROLOGY("Pediatric Gastroenterology", DepartmentType.GASTROENTEROLOGY),
    COLORECTAL_SURGERY("Colorectal Surgery", DepartmentType.GASTROENTEROLOGY),

    // Hematology
    HEMATOPATHOLOGY("Hematopathology", DepartmentType.HEMATOLOGY),
    PEDIATRIC_HEMATOLOGY("Pediatric Hematology", DepartmentType.HEMATOLOGY),
    COAGULATION_DISORDERS("Coagulation Disorders", DepartmentType.HEMATOLOGY),

    // Neurology
    PEDIATRIC_NEUROLOGY("Pediatric Neurology", DepartmentType.NEUROLOGY),
    NEUROCRITICAL_CARE("Neurocritical Care", DepartmentType.NEUROLOGY),
    NEUROMUSCULAR_MEDICINE("Neuromuscular Medicine", DepartmentType.NEUROLOGY),

    // Oncology
    RADIATION_ONCOLOGY("Radiation Oncology", DepartmentType.ONCOLOGY),
    SURGICAL_ONCOLOGY("Surgical Oncology", DepartmentType.ONCOLOGY),
    PEDIATRIC_ONCOLOGY("Pediatric Oncology", DepartmentType.ONCOLOGY),

    // Pathology
    ANATOMIC_PATHOLOGY("Anatomic Pathology", DepartmentType.PATHOLOGY),
    CLINICAL_PATHOLOGY("Clinical Pathology", DepartmentType.PATHOLOGY),
    FORENSIC_PATHOLOGY("Forensic Pathology", DepartmentType.PATHOLOGY),

    // Pulmonology
    SLEEP_MEDICINE("Sleep Medicine", DepartmentType.PULMONOLOGY),
    PEDIATRIC_PULMONOLOGY("Pediatric Pulmonology", DepartmentType.PULMONOLOGY),
    THORACIC_SURGERY("Thoracic Surgery", DepartmentType.PULMONOLOGY),

    // Other specialties
    GENERAL_PRACTICE("General Practice", DepartmentType.NONE); // Use a special department or "NONE" for GPs

    private final String displayName;
    private final DepartmentType department;
}
