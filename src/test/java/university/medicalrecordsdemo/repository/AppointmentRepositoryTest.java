package university.medicalrecordsdemo.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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

import university.medicalrecordsdemo.model.entity.DiagnosisEntity;
import university.medicalrecordsdemo.model.entity.PatientEntity;
import university.medicalrecordsdemo.model.entity.PhysicianEntity;
import university.medicalrecordsdemo.model.entity.RoleEntity;
import university.medicalrecordsdemo.model.entity.RoleType;
import university.medicalrecordsdemo.model.entity.SpecialtyType;
import university.medicalrecordsdemo.config.TestConfig;
import university.medicalrecordsdemo.model.entity.AppointmentEntity;
import university.medicalrecordsdemo.model.entity.DepartmentType;

@DataJpaTest
@Import(TestConfig.class)
public class AppointmentRepositoryTest {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private PasswordEncoder encoder;

    private AppointmentEntity appointment;
    private DiagnosisEntity diagnosis1;
    private DiagnosisEntity diagnosis2;
    private PhysicianEntity physicianEntity;
    private RoleEntity gpRole;
    private RoleEntity patientRole;
    private PatientEntity patient;

    @BeforeEach
    public void setup() {

        diagnosis1 = new DiagnosisEntity();
        diagnosis1.setCategory(DepartmentType.ANESTHESIOLOGY);
        diagnosis1.setCode("ANES008");
        diagnosis1.setName("Test Diagnosis ANESTHESIOLOGY");
        testEntityManager.persistAndFlush(diagnosis1);

        diagnosis2 = new DiagnosisEntity();
        diagnosis2.setCategory(DepartmentType.ANESTHESIOLOGY);
        diagnosis2.setCode("ANES009");
        diagnosis2.setName("Test Diagnosis ANESTHESIOLOGY 2");
        testEntityManager.persistAndFlush(diagnosis2);

        patientRole = new RoleEntity();
        patientRole.setAuthority(RoleType.ROLE_PATIENT);
        testEntityManager.persistAndFlush(patientRole);

        gpRole = new RoleEntity();
        gpRole.setAuthority(RoleType.ROLE_GENERAL_PRACTITIONER);
        testEntityManager.persistAndFlush(gpRole);

        physicianEntity = new PhysicianEntity();
        physicianEntity.setMedicalLicenseNumber("MD654321");
        physicianEntity.setFirstName("Jane");
        physicianEntity.setLastName("Doe");
        physicianEntity.setGender("Female");
        physicianEntity.setBirthDate(LocalDate.of(1985, 5, 15));
        physicianEntity.setUsername("jane.doe@example.com");
        physicianEntity.setPassword(encoder.encode("password123"));
        physicianEntity.setSpecialties(new HashSet<>(Arrays.asList(SpecialtyType.GENERAL_PRACTICE, SpecialtyType.DERMATOPATHOLOGY)));
        physicianEntity.setRoles(new HashSet<>(Arrays.asList(gpRole)));
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
        testEntityManager.persistAndFlush(patient);


        appointment = new AppointmentEntity();
        appointment.setDate(LocalDate.of(2021, 11, 11));
        appointment.setPatient(patient);
        appointment.setPhysician(physicianEntity);
        appointment.setDiagnosis(diagnosis1);
        appointment.setTreatment("Test Treatment");

    }

    @Test
    void getAppointmentByIdTest() {
        // Given
        testEntityManager.persistAndFlush(appointment);

        // When
        Optional<AppointmentEntity> appointmentById = appointmentRepository.findById(appointment.getId());

        // Then
        assertTrue(appointmentById.isPresent());
    }

    @Test
    void findAllByDiagnosisTest() {

        AppointmentEntity appointment1 = new AppointmentEntity();
        appointment1.setDate(LocalDate.of(2021, 11, 11));
        appointment1.setPatient(patient);
        appointment1.setPhysician(physicianEntity);
        appointment1.setDiagnosis(diagnosis1);
        appointment1.setTreatment("Test Treatment");
        testEntityManager.persistAndFlush(appointment1);


        AppointmentEntity appointment2 = new AppointmentEntity();
        appointment2.setDate(LocalDate.of(2021, 11, 11));
        appointment2.setPatient(patient);
        appointment2.setPhysician(physicianEntity);
        appointment2.setDiagnosis(diagnosis2);
        appointment2.setTreatment("Test Treatment");
        testEntityManager.persistAndFlush(appointment2);

        
        AppointmentEntity appointment3 = new AppointmentEntity();
        appointment3.setDate(LocalDate.of(2021, 11, 11));
        appointment3.setPatient(patient);
        appointment3.setPhysician(physicianEntity);
        appointment3.setDiagnosis(diagnosis1);
        appointment3.setTreatment("Test Treatment");
        testEntityManager.persistAndFlush(appointment3);
        List<AppointmentEntity> appointmentList = Arrays.asList(appointment1, appointment3);

        assertIterableEquals(appointmentList, appointmentRepository.findByDiagnosisId(diagnosis1.getId()));
    }

    @Test
    void findAllByPatientTest() {

        PatientEntity patient1 = new PatientEntity();
        patient1.setSsn("9805048145");
        patient1.setFirstName("Jane");
        patient1.setLastName("Smith");
        patient1.setGender("Female");
        patient1.setBirthDate(LocalDate.of(1984, 11, 11));
        patient1.setUsername("jane.smith34@test.com");
        patient1.setPassword(encoder.encode("password"));
        patient1.setRoles(new HashSet<>(Arrays.asList(patientRole)));
        patient1.setPhysician(physicianEntity);
        testEntityManager.persistAndFlush(patient1);

        AppointmentEntity appointment1 = new AppointmentEntity();
        appointment1.setDate(LocalDate.of(2021, 11, 11));
        appointment1.setPatient(patient);
        appointment1.setPhysician(physicianEntity);
        appointment1.setDiagnosis(diagnosis1);
        appointment1.setTreatment("Test Treatment");
        testEntityManager.persistAndFlush(appointment1);


        AppointmentEntity appointment2 = new AppointmentEntity();
        appointment2.setDate(LocalDate.of(2021, 11, 11));
        appointment2.setPatient(patient);
        appointment2.setPhysician(physicianEntity);
        appointment2.setDiagnosis(diagnosis1);
        appointment2.setTreatment("Test Treatment");
        testEntityManager.persistAndFlush(appointment2);

        
        AppointmentEntity appointment3 = new AppointmentEntity();
        appointment3.setDate(LocalDate.of(2021, 11, 11));
        appointment3.setPatient(patient1);
        appointment3.setPhysician(physicianEntity);
        appointment3.setDiagnosis(diagnosis2);
        appointment3.setTreatment("Test Treatment");
        testEntityManager.persistAndFlush(appointment3);
        List<AppointmentEntity> appointmentList = Arrays.asList(appointment1, appointment2);

        assertIterableEquals(appointmentList, appointmentRepository.findAllByPatient(patient));
    }

    @Test
    void saveAppointmentTest() {
        appointment = new AppointmentEntity();
        appointment.setDate(LocalDate.of(2021, 11, 11));
        appointment.setPatient(patient);
        appointment.setPhysician(physicianEntity);
        appointment.setDiagnosis(diagnosis2);
        appointment.setTreatment("Test Treatment");

        AppointmentEntity savedAppointment = appointmentRepository.save(appointment);

        assertThat(savedAppointment).isNotNull();
    }


    @Test
    void updateAppointmentAuthorityTest() {
        testEntityManager.persistAndFlush(appointment);

        appointment.setDiagnosis(diagnosis2);
        appointmentRepository.save(appointment);
        assertThat(appointment.getDiagnosis()).isEqualTo(diagnosis2);
    }

    @Test
    void deleteAppointmentTest() {
        // Given: An existing appointment is set up
        Long appointmentId = appointment.getId();
        
        // When: The appointment is deleted
        appointmentRepository.deleteById(appointmentId);
        testEntityManager.flush();  // Ensure deletion is flushed to the database

        // Then: The appointment should no longer exist
        AppointmentEntity deletedAppointment = testEntityManager.find(AppointmentEntity.class, appointmentId);
        assertNull(deletedAppointment, "Appointment should be deleted");
    }
}
