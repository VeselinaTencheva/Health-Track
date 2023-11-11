package university.medicalrecordsdemo.dto.treatment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateTreatmentDto {
    private String name;
    private String description;
}
