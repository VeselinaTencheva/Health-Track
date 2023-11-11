package university.medicalrecordsdemo.model.binding.patients;

import lombok.Getter;
import lombok.Setter;
import university.medicalrecordsdemo.model.entity.PhysicianEntity;

@Getter
@Setter
public class PatientViewModel {
    private Long id;
    private String name;
    private String ssn;
    private boolean hasInsurance;
    private PhysicianEntity generalPractitioner;
}
