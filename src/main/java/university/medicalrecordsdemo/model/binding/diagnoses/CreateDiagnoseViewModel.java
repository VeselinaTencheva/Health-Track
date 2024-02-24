package university.medicalrecordsdemo.model.binding.diagnoses;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateDiagnoseViewModel {
    @NotEmpty(message = "Code is required")
    private String code;

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Description is required")
    private String description;

    @NotEmpty(message = "Department is required")
    private String category;
}
