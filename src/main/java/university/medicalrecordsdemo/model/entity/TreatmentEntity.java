package university.medicalrecordsdemo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "treatment")
public class TreatmentEntity extends BaseEntity {

    @Column(name = "name", nullable = false)
    // @NotBlank(message = "- Error: Name is mandatory")
    // @Size(min = 5, max=30, message = " - Error: The name must be between 5 and 30
    // symbols")
    private String name;

    @Column(name = "description", nullable = false)
    // @NotBlank(message = "- Error: Description is mandatory")
    // @Size(min = 5, max=50, message = " - Error: The description must be between 5
    // and 50 symbols")
    private String description;

    @OneToMany(mappedBy = "treatment")
    @JsonIgnoreProperties("treatment")
    private Set<AppointmentEntity> appointments;
}
