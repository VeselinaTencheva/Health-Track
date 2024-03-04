package university.medicalrecordsdemo.dto.diagnosis;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import university.medicalrecordsdemo.dto.BaseDto;

@Getter
@Setter
@NoArgsConstructor
public class UpdateDiagnosisDto extends BaseDto {
    private String code;
    private String name;
    private String description;
    private String category;
}
