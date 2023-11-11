package university.medicalrecordsdemo.model.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "diagnosis")
public class DiagnosisEntity extends BaseEntity {

    private String name;

    private String description;

    @OneToMany(mappedBy = "diagnosis")
    @JsonIgnoreProperties("diagnosis")
    private Set<AppointmentEntity> appointments;

}