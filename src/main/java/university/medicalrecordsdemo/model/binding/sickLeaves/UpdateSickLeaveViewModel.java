package university.medicalrecordsdemo.model.binding.sickLeaves;

import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
// import org.springframework.format.annotation.DateTimeFormat;

// import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSickLeaveViewModel {
    @NotNull(message = "Start Date is mandatory")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String startDate;

    @NotNull(message = "Duration days is mandatory")
    @Min(value = 1, message = "Duration must be at least 1 day")
    @Max(value = 180, message = "Duration must be maximum 180 days")
    private int duration;
}
