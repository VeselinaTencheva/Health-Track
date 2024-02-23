package university.medicalrecordsdemo.model.binding.appointments;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import university.medicalrecordsdemo.model.entity.DiagnosisEntity;
import university.medicalrecordsdemo.model.entity.PatientEntity;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CreateAppointmentAndSickLeaveAndTreatmentViewModel {

    // @NotNull(message = "Date is mandatory")
    // @DateTimeFormat(pattern = "yyyy-MM-dd")
    // @FutureOrPresent(message = "Date must be in the future or present")
    private LocalDate date = LocalDate.now();

    // @NotNull(message = "Patient is mandatory")
    private PatientEntity patient;

    // @DateTimeFormat(pattern = "yyyy-MM-dd")
    // @FutureOrPresent(message = "Date must be in the future or present")
    private LocalDate sickLeaveStartDate = LocalDate.now();
    // @Min(value = 1, message = "Sick Duration Days must be at least 1 day")
    // @Max(value = 180, message = "Sick Duration Days must be maximum 180 days")
    private int sickLeaveDuration;

    private DiagnosisEntity diagnosis;

    // @Size(min = 5, max = 20, message="Min 5, Max 20")
    private String treatmentName;

    // @Size(min = 5, max = 50, message="Min 5, Max 50")
    private String treatmentDescription;
}
