package university.medicalrecordsdemo.dto.diagnosis;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateDiagnosisDto {
    private String code;
    private String name;
    private String description;
    private String category;
}
