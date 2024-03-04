package university.medicalrecordsdemo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import university.medicalrecordsdemo.model.entity.PrivilegeEntity;
import university.medicalrecordsdemo.model.entity.PrivilegeType;
import university.medicalrecordsdemo.repository.PrivilegeRepository;
import university.medicalrecordsdemo.service.privilege.PrivilegeServiceImpl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PrivilegeServiceImplTest {

    @Mock
    private PrivilegeRepository privilegeRepository;

    @InjectMocks
    private PrivilegeServiceImpl privilegeService;

    private PrivilegeEntity privilege;

    @BeforeEach
    public void setUp() {
        privilege = new PrivilegeEntity();
        privilege.setId(1L);
        privilege.setName(PrivilegeType.READ_DIAGNOSIS);
    }

    @Test
    void findAll_ShouldReturnAllPrivileges() {
        List<PrivilegeEntity> expectedPrivileges = Arrays.asList(privilege);

        // Mock the repository call to return a List instead of a Set
        given(privilegeRepository.findAll()).willReturn(expectedPrivileges);

        // Call the service method, which internally calls repository.findAll()
        Set<PrivilegeEntity> actualPrivileges = privilegeService.findAll();

        // Convert expectedPrivileges to a Set to match the service method return type
        Set<PrivilegeEntity> expectedPrivilegesSet = new HashSet<>(expectedPrivileges);

        // Assert that the actual privileges Set matches the expected privileges Set
        assertThat(actualPrivileges).isEqualTo(expectedPrivilegesSet);
    }


    @Test
    void findById_ShouldReturnPrivilege() {
        given(privilegeRepository.findById(1L)).willReturn(Optional.of(privilege));

        PrivilegeEntity foundPrivilege = privilegeService.findById(1L);

        assertThat(foundPrivilege).isEqualTo(privilege);
    }

    @Test
    void create_ShouldSavePrivilege() {
        given(privilegeRepository.save(privilege)).willReturn(privilege);

        PrivilegeEntity savedPrivilege = privilegeService.create(privilege);

        assertThat(savedPrivilege).isEqualTo(privilege);
    }

    @Test
    void update_ShouldUpdatePrivilege() {
        // Given
        Long privilegeId = 1L;
        PrivilegeEntity existingPrivilege = new PrivilegeEntity();
        existingPrivilege.setId(privilegeId);
        existingPrivilege.setName(PrivilegeType.READ_DIAGNOSIS);
    
        PrivilegeEntity updatedPrivilege = new PrivilegeEntity();
        updatedPrivilege.setName(PrivilegeType.WRITE_DIAGNOSIS);
    
        when(privilegeRepository.save(any(PrivilegeEntity.class))).thenReturn(updatedPrivilege);
    
        // When
        PrivilegeEntity result = privilegeService.update(privilegeId, updatedPrivilege);
    
        // Then
        verify(privilegeRepository).save(updatedPrivilege);
        assertThat(result.getName()).isEqualTo(PrivilegeType.WRITE_DIAGNOSIS);
    }


    @Test
    void delete_ShouldRemovePrivilege() {
        privilegeService.delete(1L);

        verify(privilegeRepository).deleteById(1L);
    }
}
