package university.medicalrecordsdemo.model.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "diagnosis")
public class DiagnosisEntity extends BaseEntity {

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    private String description;

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    // A field to categorize the diagnosis, such as by medical specialty
    private DepartmentType category;

    @OneToMany(mappedBy = "diagnosis")
    @JsonIgnoreProperties("diagnosis")
    private Set<AppointmentEntity> appointments;

    public DiagnosisEntity(String name, String code, DepartmentType category, String description) {
        this.name = name;
        this.code = code;
        this.category = category;
        this.description = description;
    }

}