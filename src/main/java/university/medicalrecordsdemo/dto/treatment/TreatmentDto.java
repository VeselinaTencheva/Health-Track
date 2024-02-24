package university.medicalrecordsdemo.dto.treatment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import university.medicalrecordsdemo.dto.appointment.AppointmentDto;

@Getter
@Setter
@NoArgsConstructor
public class TreatmentDto {
    private Long id;
    private String name;
    private String description;
    private AppointmentDto appointmentDto;
}
