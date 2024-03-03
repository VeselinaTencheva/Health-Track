package university.medicalrecordsdemo.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

import university.medicalrecordsdemo.config.TestConfig;
import university.medicalrecordsdemo.model.entity.AppointmentEntity;
import university.medicalrecordsdemo.model.entity.DepartmentType;
import university.medicalrecordsdemo.model.entity.DiagnosisEntity;
import university.medicalrecordsdemo.model.entity.PatientEntity;
import university.medicalrecordsdemo.model.entity.PhysicianEntity;
import university.medicalrecordsdemo.model.entity.RoleEntity;
import university.medicalrecordsdemo.model.entity.RoleType;
import university.medicalrecordsdemo.model.entity.SickLeaveEntity;
import university.medicalrecordsdemo.model.entity.SpecialtyType;

@DataJpaTest
@Import(TestConfig.class)
public class SickLeaveRepositoryTest {

    @Autowired
    private SickLeaveRepository sickLeaveRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private PasswordEncoder encoder;

    private SickLeaveEntity sickLeave;

    private AppointmentEntity appointment;


    @BeforeEach
    public void setup() {
        DiagnosisEntity diagnosis = new DiagnosisEntity();
        diagnosis.setCategory(DepartmentType.ANESTHESIOLOGY);
        diagnosis.setCode("ANES009");
        diagnosis.setName("Test Diagnosis ANESTHESIOLOGY 2");
        testEntityManager.persistAndFlush(diagnosis);

        RoleEntity patientRole = new RoleEntity();
        patientRole.setAuthority(RoleType.ROLE_PATIENT);
        testEntityManager.persistAndFlush(patientRole);

        RoleEntity gpRole = new RoleEntity();
        gpRole.setAuthority(RoleType.ROLE_GENERAL_PRACTITIONER);
        testEntityManager.persistAndFlush(gpRole);

        PhysicianEntity physicianEntity = new PhysicianEntity();
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

        PatientEntity patient = new PatientEntity();
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
        appointment.setDiagnosis(diagnosis);
        appointment.setTreatment("Test Treatment");
        testEntityManager.persistAndFlush(appointment);

        sickLeave = new SickLeaveEntity();
        sickLeave.setAppointment(appointment);
        sickLeave.setDuration(5);
        sickLeave.setStartDate(LocalDate.of(2024, 03, 04));
    }

    @Test
    void getSickLeaveByIdTest() {
        // Given
        testEntityManager.persistAndFlush(sickLeave);

        // When
        Optional<SickLeaveEntity> sickLeaveById = sickLeaveRepository.findById(sickLeave.getId());

        // Then
        assertTrue(sickLeaveById.isPresent());
    }


    @Test
    void saveSickLeaveTest() {
        SickLeaveEntity sickLeave = new SickLeaveEntity();
        sickLeave.setAppointment(appointment);
        sickLeave.setDuration(5);
        sickLeave.setStartDate(LocalDate.of(2024, 03, 01));

        SickLeaveEntity savedSickLeave = sickLeaveRepository.save(sickLeave);

        assertThat(savedSickLeave).isNotNull();
    }


    @Test
    void updateSickLeaveAuthorityTest() {
        testEntityManager.persistAndFlush(sickLeave);
    
        sickLeave.setDuration(4);
        
        SickLeaveEntity updatedSickLeave = sickLeaveRepository.save(sickLeave);
        
        testEntityManager.flush();
    
        SickLeaveEntity refetchedSickLeave = testEntityManager.find(SickLeaveEntity.class, updatedSickLeave.getId());
    
        assertThat(refetchedSickLeave.getDuration()).isEqualTo(4);
    }
    
    @Test
    void deleteSickLeaveTest() {
        testEntityManager.persistAndFlush(sickLeave);

        sickLeaveRepository.deleteById(sickLeave.getId());
        Optional<SickLeaveEntity> deletedSickLeave = sickLeaveRepository.findById(sickLeave.getId());

        assertTrue(deletedSickLeave.isEmpty());
    }
}
