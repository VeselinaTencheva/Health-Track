package university.medicalrecordsdemo.model.binding.physicians;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import university.medicalrecordsdemo.model.binding.users.UserViewModel;
import university.medicalrecordsdemo.model.entity.SpecialtyType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhysiciansViewModel extends UserViewModel {
    private String medicalLicenseNumber;
    private Set<SpecialtyType> specialties;
    private boolean isGP;
    private int patientsCount;

    public String getFullname() {
        return this.getFirstName() + " " + this.getLastName();
    }
}
