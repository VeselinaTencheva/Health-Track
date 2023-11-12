package university.medicalrecordsdemo.dto.sickLeave;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CreateSickLeaveDto {
    private LocalDate startDate;
    private int duration;
    // private Appointment appointment;
}
