package university.medicalrecordsdemo.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

import university.medicalrecordsdemo.config.TestConfig;
import university.medicalrecordsdemo.model.entity.PhysicianEntity;
import university.medicalrecordsdemo.model.entity.RoleEntity;
import university.medicalrecordsdemo.model.entity.RoleType;
import university.medicalrecordsdemo.model.entity.SpecialtyType;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(TestConfig.class)
public class PhysicianRepositoryTest {

    @Autowired
    private PhysicianRepository physicianRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private PasswordEncoder encoder;


    private PhysicianEntity physician;

    RoleEntity physicianRole;


    @BeforeEach
    public void setup() {
        physicianRole = new RoleEntity();
        physicianRole.setAuthority(RoleType.ROLE_PHYSICIAN);
        testEntityManager.persistAndFlush(physicianRole);

        physician = new PhysicianEntity();
        physician.setMedicalLicenseNumber("MD323536");
        physician.setFirstName("John");
        physician.setLastName("Doe");
        physician.setGender("Male");
        physician.setBirthDate(LocalDate.of(1980, 1, 1));
        physician.setUsername("john.doe4@example.com");
        physician.setPassword(encoder.encode("password"));
        physician.setSsn("123-45-6785");
        physician.setSpecialties(new HashSet<>(Arrays.asList(SpecialtyType.COLORECTAL_SURGERY, SpecialtyType.DERMATOPATHOLOGY)));
        physician.setRoles(new HashSet<>(Arrays.asList(physicianRole)));
        testEntityManager.persistAndFlush(physician);
    }

    @Test
    void getPhysicianByIdTest() {
        // Given
        testEntityManager.persistAndFlush(physician);

        // When
        Optional<PhysicianEntity> physicianById = physicianRepository.findById(physician.getId());

        // Then
        assertTrue(physicianById.isPresent());
    }

    @Test
    void findAllBySpecialtiesTest() {
        Set<SpecialtyType> specialities = new HashSet<>(Arrays.asList(SpecialtyType.DERMATOPATHOLOGY, SpecialtyType.GENERAL_PRACTICE));

       // Generate and persist physicians
        PhysicianEntity physician1 = new PhysicianEntity();
        physician1.setMedicalLicenseNumber("MD323536");
        physician1.setFirstName("John");
        physician1.setLastName("Doe");
        physician1.setGender("Male");
        physician1.setBirthDate(LocalDate.of(1980, 1, 1));
        physician1.setUsername("john.doe@example.com");
        physician1.setPassword(encoder.encode("password"));
        physician1.setSsn("123-45-6789");
        physician1.setSpecialties(new HashSet<>(Arrays.asList(SpecialtyType.COLORECTAL_SURGERY, SpecialtyType.DERMATOPATHOLOGY)));
        physician1.setRoles(new HashSet<>(Arrays.asList(physicianRole)));
        testEntityManager.persistAndFlush(physician1);

        PhysicianEntity physician2 = new PhysicianEntity();
        physician2.setMedicalLicenseNumber("MD654321");
        physician2.setFirstName("Jane");
        physician2.setLastName("Doe");
        physician2.setGender("Female");
        physician2.setBirthDate(LocalDate.of(1985, 5, 15));
        physician2.setUsername("jane.doe31@example.com");
        physician2.setPassword(encoder.encode("password123"));
        physician2.setSsn("457-45-6788");
        physician2.setSpecialties(new HashSet<>(Arrays.asList(SpecialtyType.DIABETES_AND_METABOLISM, SpecialtyType.GENERAL_PRACTICE)));
        physician2.setRoles(new HashSet<>(Arrays.asList(physicianRole)));
        testEntityManager.persistAndFlush(physician2);

        PhysicianEntity physician3 = new PhysicianEntity();
        physician3.setMedicalLicenseNumber("MD789012");
        physician3.setSsn("472-45-6787");
        physician3.setFirstName("Alex");
        physician3.setLastName("Smith");
        physician3.setGender("Non-Binary");
        physician3.setBirthDate(LocalDate.of(1975, 3, 22));
        physician3.setUsername("alex.smith@example.com");
        physician3.setPassword(encoder.encode("securePassword"));
        physician3.setSpecialties(new HashSet<>(Arrays.asList(SpecialtyType.ANESTHESIOLOGIST)));
        physician3.setRoles(new HashSet<>(Arrays.asList(physicianRole)));
        testEntityManager.persistAndFlush(physician3);

        List<PhysicianEntity> result = physicianRepository.findAllBySpecialtiesIn(specialities);

        assertTrue(result.contains(physician1), "Result should contain physician1");
        assertTrue(result.contains(physician2), "Result should contain physician2");
        assertFalse(result.contains(physician3), "Result should not contain physician3");
    }

    @Test
    void findAllByEmptySpecialtiesTest() {
        testEntityManager.persistAndFlush(physician);
        Set<SpecialtyType> specialties = new HashSet<>();
        assertThat(physicianRepository.findAllBySpecialtiesIn(specialties).size()).isEqualTo(0);
    }

    @Test
    void savePhysicianTest() {
        PhysicianEntity physician = new PhysicianEntity("747574757475", "Test", "Testov", "Male", LocalDate.of(1984, 11, 11), "create_test_physician@testov.test", encoder.encode("password"), "MD747574", new HashSet<>(Arrays.asList(SpecialtyType.CLINICAL_IMMUNOLOGY)), physicianRole);

        PhysicianEntity savedPhysician = physicianRepository.save(physician);

        assertThat(savedPhysician).isNotNull();
    }


    @Test
    void updatePhysicianSpecialtyTest() {
        testEntityManager.persistAndFlush(physician);

        Set<SpecialtyType> specialities = new HashSet<>(Arrays.asList(SpecialtyType.CLINICAL_IMMUNOLOGY, SpecialtyType.ANATOMIC_PATHOLOGY));
        physician.setSpecialties(specialities);
        physicianRepository.save(physician);

        assertThat(physician.getSpecialties().size()).isEqualTo(2);
    }

    @Test
    void deletePhysicianTest() {
        testEntityManager.persistAndFlush(physician);

        physicianRepository.deleteById(physician.getId());
        Optional<PhysicianEntity> deletedPhysician = physicianRepository.findById(physician.getId());

        assertTrue(deletedPhysician.isEmpty());
    }
}
