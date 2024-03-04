package university.medicalrecordsdemo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import university.medicalrecordsdemo.model.entity.RoleEntity;
import university.medicalrecordsdemo.model.entity.RoleType;
import university.medicalrecordsdemo.repository.RoleRepository;
import university.medicalrecordsdemo.service.role.RoleServiceImpl;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    private RoleEntity role;

    @BeforeEach
    void setUp() {
        role = new RoleEntity();
        role.setId(1L);
        role.setAuthority(RoleType.ROLE_ADMIN);
    }

    @Test
    void findAll_ShouldReturnAllRoles() {
        Set<RoleEntity> expectedRoles = new HashSet<>();
        expectedRoles.add(role);
        
        given(roleRepository.findAll()).willReturn(new ArrayList<>(expectedRoles));

        Set<RoleEntity> actualRoles = roleService.findAll();

        assertThat(actualRoles).hasSameElementsAs(expectedRoles);
    }

    @Test
    void findById_ShouldReturnRole() {
        given(roleRepository.findById(1L)).willReturn(Optional.of(role));

        RoleEntity foundRole = roleService.findById(1L);

        assertThat(foundRole).isEqualTo(role);
    }

    @Test
    void create_ShouldSaveAndReturnRole() {
        given(roleRepository.save(role)).willReturn(role);

        RoleEntity savedRole = roleService.create(role);

        assertThat(savedRole).isEqualTo(role);
    }

    @Test
    void update_ShouldSaveAndReturnUpdatedRole() {
        RoleEntity updatedRole = new RoleEntity();
        updatedRole.setId(role.getId());
        updatedRole.setAuthority(RoleType.ROLE_PATIENT);

        given(roleRepository.save(updatedRole)).willReturn(updatedRole);

        RoleEntity result = roleService.update(role.getId(), updatedRole);

        assertThat(result).isEqualTo(updatedRole);
    }

    @Test
    void delete_ShouldCallDeleteById() {
        roleService.delete(role.getId());

        verify(roleRepository).deleteById(role.getId());
    }
}
