package university.medicalrecordsdemo.model.binding.appointments;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import university.medicalrecordsdemo.model.entity.DiagnosisEntity;
import university.medicalrecordsdemo.model.entity.PatientEntity;
import university.medicalrecordsdemo.model.entity.PhysicianEntity;

import org.springframework.format.annotation.DateTimeFormat;

// import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UpdateAppointmentAndSickLeaveAndTreatmentViewModel {
    private long id;

    // @NotNull(message = "Date is mandatory")
    // @DateTimeFormat(pattern = "yyyy-MM-dd")
    // @FutureOrPresent(message = "Date must be in the future or present")
    private LocalDate date;

    // @NotNull(message = "Patient is mandatory")
    private PatientEntity patient;

    // @NotNull(message = "Physician is mandatory")
    private PhysicianEntity physician;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    // @FutureOrPresent(message = "Date must be in the future or present")
    private LocalDate sickLeaveStartDate;

    // @Min(0)
    // @Max(180)
    private int sickLeaveDuration;

    private DiagnosisEntity diagnosis;

    // @NotBlank
    // @Size(min = 5, max = 20, message="Min 5, Max 20")
    private String treatmentName;

    // @NotBlank
    // @Size(min = 5, max = 50, message="Min 5, Max 50")
    private String treatmentDescription;

}
