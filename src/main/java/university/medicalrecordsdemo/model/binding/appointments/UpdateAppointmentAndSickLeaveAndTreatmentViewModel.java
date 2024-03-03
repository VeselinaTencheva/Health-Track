package university.medicalrecordsdemo.model.binding.appointments;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import university.medicalrecordsdemo.validation.NotEmptyIfPresent;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

// import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UpdateAppointmentAndSickLeaveAndTreatmentViewModel {

    @NotNull(message = "Date is mandatory")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "Date must be in the future or present")
    private LocalDate date = LocalDate.now();

    @NotNull(message = "Patient is mandatory")
    private Long patientId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "Date must be in the future or present")
    private LocalDate sickLeaveStartDate = LocalDate.now();

    @Min(value = 0, message = "Sick Duration Days must be at least 1 day")
    @Max(value = 180, message = "Sick Duration Days must be maximum 180 days")
    private int sickLeaveDuration;

    @NotNull(message = "Diagnose is mandatory")
    private Long diagnosisId;

    @NotEmptyIfPresent(message = "Treatment should be between 5 and 20 symbols",field = "treatment", minParam = "5", maxParam = "20")
    private String treatment;
}
    
