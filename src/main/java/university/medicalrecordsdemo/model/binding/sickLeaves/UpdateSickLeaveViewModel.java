package university.medicalrecordsdemo.model.binding.sickLeaves;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
// import org.springframework.format.annotation.DateTimeFormat;

// import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSickLeaveViewModel {
    // @NotNull(message = "Start Date is mandatory")
    // @DateTimeFormat(pattern = "yyyy-MM-dd")
    // @FutureOrPresent(message = "Start Date must be in the future or present")
    private LocalDate startDate;

    // TODO add validation for null or alphabetical symbols
    // @NotEmpty(message = "Duration days is mandatory")
    // @Min(value = 1, message = "Duration must be at least 1 day")
    // @Max(value = 180, message = "Duration must be maximum 180 days")
    private int duration;
}
