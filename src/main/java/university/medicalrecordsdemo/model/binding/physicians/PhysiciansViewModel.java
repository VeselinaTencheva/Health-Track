package university.medicalrecordsdemo.model.binding.physicians;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import university.medicalrecordsdemo.model.binding.users.UserViewModel;
import university.medicalrecordsdemo.model.entity.DepartmentType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhysiciansViewModel extends UserViewModel {
    private String medicalLicenseNumber;
    private DepartmentType departmentType;
    private boolean isGP;
    private int patientsCount;
}
