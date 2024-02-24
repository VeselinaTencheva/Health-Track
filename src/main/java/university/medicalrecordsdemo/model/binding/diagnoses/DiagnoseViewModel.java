package university.medicalrecordsdemo.model.binding.diagnoses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiagnoseViewModel {
    private long id;
    private String code;
    private String name;
    private String description;
    private String category;
    private int patientsCount;
}
