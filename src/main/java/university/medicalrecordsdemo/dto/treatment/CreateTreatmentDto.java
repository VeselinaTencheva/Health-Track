package university.medicalrecordsdemo.dto.treatment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CreateTreatmentDto {
    private String name;
    private String description;
}
