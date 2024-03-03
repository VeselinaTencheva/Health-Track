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

import university.medicalrecordsdemo.model.entity.RoleEntity;
import university.medicalrecordsdemo.model.entity.RoleType;

@DataJpaTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private RoleEntity role;


    @BeforeEach
    public void setup() {
        role = new RoleEntity();
        role.setAuthority(RoleType.ROLE_ADMIN);
    }

    @Test
    void getRoleByIdTest() {
        // Given
        testEntityManager.persistAndFlush(role);

        // When
        Optional<RoleEntity> roleById = roleRepository.findById(role.getId());

        // Then
        assertTrue(roleById.isPresent());
    }

    @Test
    void findAllByAuthorityTest() {

        RoleEntity role1 = new RoleEntity();
        role1.setAuthority(RoleType.ROLE_ADMIN);
        testEntityManager.persistAndFlush(role1);
    
        RoleEntity role2 = new RoleEntity();
        role2.setAuthority(RoleType.ROLE_PATIENT);
        testEntityManager.persistAndFlush(role2);
    
        RoleEntity role3 = new RoleEntity();
        role3.setAuthority(RoleType.ROLE_GENERAL_PRACTITIONER);
        testEntityManager.persistAndFlush(role3);
    
        List<RoleEntity> roleList = Arrays.asList(role1, role2, role3);
    
        // Then
        assertThat(roleList)
        .extracting(RoleEntity::getAuthority)
        .contains(RoleType.ROLE_GENERAL_PRACTITIONER.name());
    }

    @Test
    void saveRoleTest() {
        RoleEntity role = new RoleEntity();
        role.setAuthority(RoleType.ROLE_PATIENT);

        RoleEntity savedRole = roleRepository.save(role);

        assertThat(savedRole).isNotNull();
    }


    @Test
    void updateRoleAuthorityTest() {
        testEntityManager.persistAndFlush(role);
    
        role.setAuthority(RoleType.ROLE_GENERAL_PRACTITIONER);
        
        RoleEntity updatedRole = roleRepository.save(role);
        
        testEntityManager.flush();
    
        RoleEntity refetchedRole = testEntityManager.find(RoleEntity.class, updatedRole.getId());
    
        assertThat(refetchedRole.getAuthority()).isEqualTo(RoleType.ROLE_GENERAL_PRACTITIONER.name());
    }
    
    @Test
    void deleteRoleTest() {
        testEntityManager.persistAndFlush(role);

        roleRepository.deleteById(role.getId());
        Optional<RoleEntity> deletedRole = roleRepository.findById(role.getId());

        assertTrue(deletedRole.isEmpty());
    }
}
