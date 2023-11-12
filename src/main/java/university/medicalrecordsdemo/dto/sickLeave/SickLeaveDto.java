package university.medicalrecordsdemo.dto.sickLeave;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import university.medicalrecordsdemo.dto.appointment.AppointmentDto;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class SickLeaveDto {
    private String id;
    private LocalDate startDate;
    private int duration;
    private AppointmentDto appointmentDto;
}