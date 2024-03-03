package university.medicalrecordsdemo.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import university.medicalrecordsdemo.model.entity.PrivilegeEntity;
import university.medicalrecordsdemo.model.entity.PrivilegeType;

@DataJpaTest
public class PrivilegeRepositoryTest {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private PrivilegeEntity privilege;


    @BeforeEach
    public void setup() {
        privilege = new PrivilegeEntity();
        privilege.setName(PrivilegeType.READ_PATIENT);
    }

    @Test
    void getPrivilegeByIdTest() {
        // Given
        testEntityManager.persistAndFlush(privilege);

        // When
        Optional<PrivilegeEntity> privilegeById = privilegeRepository.findById(privilege.getId());

        // Then
        assertTrue(privilegeById.isPresent());
    }

    @Test
    void findAllByAuthorityTest() {

        PrivilegeEntity privilege1 = new PrivilegeEntity();
        privilege1.setName(PrivilegeType.READ_PHYSICIAN);
        testEntityManager.persistAndFlush(privilege1);


        PrivilegeEntity privilege2 = new PrivilegeEntity();
        privilege2.setName(PrivilegeType.READ_SICK_LEAVE);
        testEntityManager.persistAndFlush(privilege2);

        
        PrivilegeEntity privilege3 = new PrivilegeEntity();
        privilege3.setName(PrivilegeType.READ_VISITATION);
        testEntityManager.persistAndFlush(privilege3);

        List<PrivilegeEntity> privilegeList = Arrays.asList(privilege1, privilege2, privilege3);

        assertThat(privilegeList)
        .extracting(PrivilegeEntity::getName)
        .contains(PrivilegeType.READ_PHYSICIAN);
    }

    @Test
    void savePrivilegeTest() {
        PrivilegeEntity privilege = new PrivilegeEntity();
        privilege.setName(PrivilegeType.WRITE_DIAGNOSIS);

        PrivilegeEntity savedPrivilege = privilegeRepository.save(privilege);

        assertThat(savedPrivilege).isNotNull();
    }


    @Test
    void updatePrivilegeAuthorityTest() {
        testEntityManager.persistAndFlush(privilege);

        privilege.setName(PrivilegeType.READ_DIAGNOSIS);

        privilegeRepository.save(privilege);
        assertThat(privilege.getName()).isEqualTo(PrivilegeType.READ_DIAGNOSIS);
    }

    @Test
    void deletePrivilegeTest() {
        testEntityManager.persistAndFlush(privilege);

        privilegeRepository.deleteById(privilege.getId());
        Optional<PrivilegeEntity> deletedPrivilege = privilegeRepository.findById(privilege.getId());

        assertTrue(deletedPrivilege.isEmpty());
    }
}
