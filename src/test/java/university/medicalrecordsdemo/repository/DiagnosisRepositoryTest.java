package university.medicalrecordsdemo.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import university.medicalrecordsdemo.model.entity.DepartmentType;
import university.medicalrecordsdemo.model.entity.DiagnosisEntity;

@DataJpaTest
public class DiagnosisRepositoryTest {

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private DiagnosisEntity diagnosis;


    @BeforeEach
    public void setup() {
        diagnosis = new DiagnosisEntity();
        diagnosis.setCategory(DepartmentType.CARDIOLOGY);
        diagnosis.setCode("CARD008");
        diagnosis.setName("Test Diagnosis");
        diagnosis.setDescription("Test Description");
    }

    @Test
    void getDiagnosisByIdTest() {
        // Given
        testEntityManager.persistAndFlush(diagnosis);

        // When
        Optional<DiagnosisEntity> diagnosisById = diagnosisRepository.findById(diagnosis.getId());

        // Then
        assertTrue(diagnosisById.isPresent());
    }

    @Test
    void findAllByCategoryTest() {

       // Generate and persist physicians
        DiagnosisEntity diagnosis1 = new DiagnosisEntity();
        diagnosis1.setCategory(DepartmentType.ANESTHESIOLOGY);
        diagnosis1.setCode("ANES008");
        diagnosis1.setName("Test Diagnosis ANESTHESIOLOGY");
        diagnosis1.setDescription("Test Description ANESTHESIOLOGY");
        testEntityManager.persistAndFlush(diagnosis1);


        DiagnosisEntity diagnosis2 = new DiagnosisEntity();
        diagnosis2.setCategory(DepartmentType.ENDOCRINOLOGY);
        diagnosis2.setCode("END009");
        diagnosis2.setName("Test Diagnosis ENDOCRINOLOGY");
        diagnosis2.setDescription("Test Description ENDOCRINOLOGY");
        testEntityManager.persistAndFlush(diagnosis2);

        
        DiagnosisEntity diagnosis3 = new DiagnosisEntity();
        diagnosis3.setCategory(DepartmentType.ANESTHESIOLOGY);
        diagnosis3.setCode("ANES009");
        diagnosis3.setName("Test Diagnosis ANESTHESIOLOGY 2");
        diagnosis3.setDescription("Test Description ANESTHESIOLOGY 2");
        testEntityManager.persistAndFlush(diagnosis3);

        List<DiagnosisEntity> diagnosisList = Arrays.asList(diagnosis1, diagnosis3);

        assertIterableEquals(diagnosisList, diagnosisRepository.findAllByCategory(DepartmentType.ANESTHESIOLOGY));
    }

    @Test
    void saveDiagnosisTest() {
        DiagnosisEntity diagnosis = new DiagnosisEntity();

        diagnosis.setCategory(DepartmentType.ENDOCRINOLOGY);
        diagnosis.setCode("END008");
        diagnosis.setName("Test Diagnosis ENDOCRINOLOGY");
        diagnosis.setDescription("Test Description ENDOCRINOLOGY");


        DiagnosisEntity savedDiagnosis = diagnosisRepository.save(diagnosis);

        assertThat(savedDiagnosis).isNotNull();
    }


    @Test
    void updateDiagnosisCategoryTest() {
        testEntityManager.persistAndFlush(diagnosis);

        diagnosis.setCategory(DepartmentType.GASTROENTEROLOGY);
        diagnosis.setCode("GAST008");
        diagnosisRepository.save(diagnosis);
        assertThat(diagnosis.getCategory()).isEqualTo(DepartmentType.GASTROENTEROLOGY);
    }

    @Test
    void deleteDiagnosisTest() {
        testEntityManager.persistAndFlush(diagnosis);

        diagnosisRepository.deleteById(diagnosis.getId());
        Optional<DiagnosisEntity> deletedDiagnosis = diagnosisRepository.findById(diagnosis.getId());

        assertTrue(deletedDiagnosis.isEmpty());
    }
}
