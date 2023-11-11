package university.medicalrecordsdemo.dto.sickLeave;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UpdateSickLeaveDto {
    private LocalDate startDate;
    private int duration;
}
