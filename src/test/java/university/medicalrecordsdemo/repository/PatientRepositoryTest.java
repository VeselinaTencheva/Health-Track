package university.medicalrecordsdemo.repository;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

import university.medicalrecordsdemo.model.entity.PatientEntity;
import university.medicalrecordsdemo.model.entity.PhysicianEntity;
import university.medicalrecordsdemo.model.entity.RoleEntity;
import university.medicalrecordsdemo.model.entity.RoleType;
import university.medicalrecordsdemo.model.entity.SpecialtyType;

import university.medicalrecordsdemo.config.TestConfig;

@DataJpaTest
@Import(TestConfig.class)
public class PatientRepositoryTest {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private PasswordEncoder encoder;

    private PatientEntity patient;

    RoleEntity patientRole;

    RoleEntity gpRoleEntity;

    PhysicianEntity physicianEntity;

    @BeforeEach
    public void setup() {
        // Create and persist test data for RoleEntity
        patientRole = new RoleEntity();
        patientRole.setAuthority(RoleType.ROLE_PATIENT);
        testEntityManager.persistAndFlush(patientRole);

        gpRoleEntity = new RoleEntity();
        gpRoleEntity.setAuthority(RoleType.ROLE_GENERAL_PRACTITIONER);
        testEntityManager.persistAndFlush(gpRoleEntity);

        // Assuming you have a method to create a PhysicianEntity with the required details
        physicianEntity = new PhysicianEntity();
        physicianEntity.setMedicalLicenseNumber("MD654321");
        physicianEntity.setFirstName("Jane");
        physicianEntity.setLastName("Doe");
        physicianEntity.setGender("Female");
        physicianEntity.setBirthDate(LocalDate.of(1985, 5, 15));
        physicianEntity.setUsername("jane.doe@example.com");
        physicianEntity.setPassword(encoder.encode("password123"));
        physicianEntity.setSpecialties(new HashSet<>(Arrays.asList(SpecialtyType.DIABETES_AND_METABOLISM, SpecialtyType.DERMATOPATHOLOGY)));
        physicianEntity.setRoles(new HashSet<>(Arrays.asList(gpRoleEntity)));
        physicianEntity.setAccountNonExpired(false);
        physicianEntity.setAccountNonLocked(false);
        physicianEntity.setCredentialsNonExpired(false);
        physicianEntity.setEnabled(false);
        physicianEntity.setSsn("9805044142");
        testEntityManager.persistAndFlush(physicianEntity);


        patient = new PatientEntity();
        patient.setSsn("9805048560");
        patient.setFirstName("Jane");
        patient.setLastName("Smith");
        patient.setGender("Female");
        patient.setBirthDate(LocalDate.of(1984, 11, 11));
        patient.setUsername("jane.smith@test.com");
        patient.setPassword(encoder.encode("password"));
        patient.setRoles(new HashSet<>(Arrays.asList(patientRole)));
        patient.setPhysician(physicianEntity);

    }

    @Test
    void getPatientByIdTest() {
        // Given
        testEntityManager.persistAndFlush(patient);

        // When
        Optional<PatientEntity> patientById = patientRepository.findById(patient.getId());

        // Then
        assertTrue(patientById.isPresent());
    }

    @Test
    void findAllByPhysicianTest() {

       // Generate and persist physicians
        PatientEntity patient1 = new PatientEntity();
        patient1.setSsn("9805048567");
        patient1.setFirstName("Harry");
        patient1.setLastName("Smith");
        patient1.setGender("Female");
        patient1.setBirthDate(LocalDate.of(1984, 11, 11));
        patient1.setUsername("harry.smith@test.com");
        patient1.setPassword(encoder.encode("password"));
        patient1.setRoles(new HashSet<>(Arrays.asList(patientRole)));
        patient1.setPhysician(physicianEntity);
        testEntityManager.persistAndFlush(patient1);


        PatientEntity patient2 = new PatientEntity();
        patient2.setSsn("9805048568");
        patient2.setFirstName("Alex");
        patient2.setLastName("Smith");
        patient2.setGender("Female");
        patient2.setBirthDate(LocalDate.of(1984, 11, 11));
        patient2.setUsername("alex.smith@test.com");
        patient2.setPassword(encoder.encode("password"));
        patient2.setRoles(new HashSet<>(Arrays.asList(patientRole)));
        patient2.setPhysician(physicianEntity);
        testEntityManager.persistAndFlush(patient2);

        PatientEntity patient3 = new PatientEntity();
        patient3.setSsn("9805048569");
        patient3.setFirstName("Luke");
        patient3.setLastName("Smith");
        patient3.setGender("Female");
        patient3.setBirthDate(LocalDate.of(1984, 11, 11));
        patient3.setUsername("luke.smith@test.com");
        patient3.setPassword(encoder.encode("password"));
        patient3.setRoles(new HashSet<>(Arrays.asList(patientRole)));
        patient3.setPhysician(physicianEntity);
        testEntityManager.persistAndFlush(patient3);

        List<PatientEntity> patientsList = Arrays.asList(patient1, patient2, patient3);

        assertIterableEquals(patientsList, patientRepository.findByPhysician(physicianEntity));
    }

    @Test
    void savePatientTest() {
        PatientEntity patient = new PatientEntity();

        patient.setSsn("9805048561");
        patient.setFirstName("Mary");
        patient.setLastName("Smith");
        patient.setGender("Female");
        patient.setBirthDate(LocalDate.of(1984, 10, 11));
        patient.setUsername("mary.smith@test.com");
        patient.setPassword(encoder.encode("password"));
        patient.setRoles(new HashSet<>(Arrays.asList(patientRole)));
        patient.setPhysician(physicianEntity);


        PatientEntity savedPatient = patientRepository.save(patient);

        assertThat(savedPatient).isNotNull();
    }


    @Test
    void updatePatienGPTest() {
        testEntityManager.persistAndFlush(patient);

        PhysicianEntity physicianEntity = new PhysicianEntity();
        physicianEntity.setMedicalLicenseNumber("MD654322");
        physicianEntity.setFirstName("Jane");
        physicianEntity.setLastName("Doe");
        physicianEntity.setGender("Female");
        physicianEntity.setBirthDate(LocalDate.of(1985, 5, 15));
        physicianEntity.setUsername("jane.doe2@example.com");
        physicianEntity.setPassword(encoder.encode("password123"));
        physicianEntity.setSpecialties(new HashSet<>(Arrays.asList(SpecialtyType.GENERAL_PRACTICE)));
        physicianEntity.setRoles(new HashSet<>(Arrays.asList(gpRoleEntity)));
        physicianEntity.setSsn("9805044143");
        testEntityManager.persistAndFlush(physicianEntity);
    
        patient.setPhysician(physicianEntity);
        patientRepository.save(patient);
    
        assertThat(patient.getPhysician()).isEqualTo(physicianEntity);
    }

    @Test
    void deletePatientTest() {
        testEntityManager.persistAndFlush(patient);

        patientRepository.deleteById(patient.getId());
        Optional<PatientEntity> deletedPatient = patientRepository.findById(patient.getId());

        assertTrue(deletedPatient.isEmpty());
    }
}
