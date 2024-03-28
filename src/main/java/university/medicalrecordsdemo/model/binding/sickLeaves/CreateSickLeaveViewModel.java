package university.medicalrecordsdemo.model.binding.sickLeaves;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateSickLeaveViewModel {
    @NotNull(message = "Start Date is mandatory")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "Start Date must be in the future or present")
    private LocalDate startDate = LocalDate.now();;

    @Min(value = 1, message = "Duration must be at least 1 day")
    @Max(value = 180, message = "Duration must be maximum 180 days")
    private int duration;
}
