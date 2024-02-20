package university.medicalrecordsdemo.dto.diagnosis;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DiagnosisDto {
    private String id;
    private String code;
    private String name;
    private String description;
    private String category;
}