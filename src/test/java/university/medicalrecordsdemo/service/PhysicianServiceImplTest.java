package university.medicalrecordsdemo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import university.medicalrecordsdemo.dto.physician.PhysicianDto;
import university.medicalrecordsdemo.model.entity.PhysicianEntity;
import university.medicalrecordsdemo.model.entity.RoleEntity;
import university.medicalrecordsdemo.model.entity.RoleType;
import university.medicalrecordsdemo.model.entity.SpecialtyType;
import university.medicalrecordsdemo.repository.PatientRepository;
import university.medicalrecordsdemo.repository.PhysicianRepository;
import university.medicalrecordsdemo.repository.RoleRepository;
import university.medicalrecordsdemo.service.physician.PhysicianServiceImpl;
import university.medicalrecordsdemo.util.enums.PhysicianTableColumnsEnum;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PhysicianServiceImplTest {

    @Mock
    private PhysicianRepository physicianRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PhysicianServiceImpl physicianService;

    private PhysicianEntity physicianEntity;
    private PhysicianDto physicianDto;

    @BeforeEach
    void setUp() {
        physicianEntity = new PhysicianEntity();
        physicianEntity.setSsn("123456789");
        physicianEntity.setFirstName("John");
        physicianEntity.setLastName("Doe");
        physicianEntity.setGender("Male");
        physicianEntity.setBirthDate(LocalDate.of(1970, 1, 1));
        physicianEntity.setUsername("johndoe@example.com");
        physicianEntity.setPassword("password");
        physicianEntity.setMedicalLicenseNumber("MED123456");
        physicianEntity.setSpecialties(new HashSet<>(Arrays.asList(SpecialtyType.ANESTHESIOLOGIST, SpecialtyType.COAGULATION_DISORDERS)));

        // Assuming you have a role entity for the physician
        RoleEntity physicianRole = new RoleEntity();
        physicianRole.setAuthority(RoleType.ROLE_PHYSICIAN);
        physicianEntity.setRoles(new HashSet<>(Arrays.asList(physicianRole)));


        physicianDto = new PhysicianDto();
        physicianDto.setSsn("123456789");
        physicianDto.setFirstName("John");
        physicianDto.setLastName("Doe");
        physicianDto.setGender("Male");
        physicianDto.setBirthDate(LocalDate.of(1970, 1, 1));
        physicianDto.setUsername("johndoe@example.com");
        physicianDto.setPassword("password");
        physicianDto.setMedicalLicenseNumber("MED123456");
        physicianDto.setSpecialties(new HashSet<>(Arrays.asList(SpecialtyType.ANESTHESIOLOGIST, SpecialtyType.COAGULATION_DISORDERS)));
        // Initialize your entity and DTO with test data
    }

    @Test
    void findAll_ShouldReturnAllPhysicians() {
        given(physicianRepository.findAll()).willReturn(Collections.singletonList(physicianEntity));
        given(modelMapper.map(physicianEntity, PhysicianDto.class)).willReturn(physicianDto);

        Set<PhysicianDto> result = physicianService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.iterator().next()).isEqualTo(physicianDto);
    }

    @Test
    void findById_ShouldReturnPhysician() {
        given(physicianRepository.findById(anyLong())).willReturn(Optional.of(physicianEntity));
        given(modelMapper.map(physicianEntity, PhysicianDto.class)).willReturn(physicianDto);

        PhysicianDto result = physicianService.findById(1L);

        assertThat(result).isEqualTo(physicianDto);
    }

    @Test
    void findAllByPageAndSort_ShouldReturnPagedPhysicians() {
        PageRequest pageRequest = PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC, PhysicianTableColumnsEnum.BIRTH_DATE.getColumnName()));
        Page<PhysicianEntity> page = new PageImpl<>(Collections.singletonList(physicianEntity));
        
        given(physicianRepository.findAll(pageRequest)).willReturn(page);
        given(modelMapper.map(any(PhysicianEntity.class), eq(PhysicianDto.class))).willReturn(physicianDto);

        Page<PhysicianDto> result = physicianService.findAllByPageAndSort(0, 1, PhysicianTableColumnsEnum.BIRTH_DATE, "ASC");

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(physicianDto);
    }

    @Test
    void findAllByPage_ShouldReturnPagedPhysicians() {
        PageRequest pageRequest = PageRequest.of(0, 1);
        Page<PhysicianEntity> page = new PageImpl<>(Collections.singletonList(physicianEntity));
        
        given(physicianRepository.findAll(pageRequest)).willReturn(page);
        given(modelMapper.map(any(PhysicianEntity.class), eq(PhysicianDto.class))).willReturn(physicianDto);

        Page<PhysicianDto> result = physicianService.findAllByPage(0, 1);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(physicianDto);
    }

    @Test
    void findAllBySpecialty_ShouldReturnPhysiciansWithGivenSpecialty() {
        SpecialtyType specialty = SpecialtyType.GENERAL_PRACTICE;
        Set<SpecialtyType> specialtySet = new HashSet<>();
        specialtySet.add(specialty);
        
        given(physicianRepository.findAllBySpecialtiesIn(specialtySet)).willReturn(Collections.singletonList(physicianEntity));
        given(modelMapper.map(any(PhysicianEntity.class), eq(PhysicianDto.class))).willReturn(physicianDto);

        Set<PhysicianDto> result = physicianService.findAllBySpecialty(specialty);

        assertThat(result).hasSize(1);
        assertThat(result.iterator().next()).isEqualTo(physicianDto);
    }


    @Test
    void create_ShouldSavePhysician() {
        given(physicianRepository.save(any(PhysicianEntity.class))).willReturn(physicianEntity);
        given(modelMapper.map(physicianDto, PhysicianEntity.class)).willReturn(physicianEntity);
        given(modelMapper.map(physicianEntity, PhysicianDto.class)).willReturn(physicianDto);

        PhysicianDto result = physicianService.create(physicianDto);

        assertThat(result).isEqualTo(physicianDto);
        verify(physicianRepository).save(physicianEntity);
    }

    @Test
    void update_ShouldUpdatePhysician() {
        given(physicianRepository.findById(any())).willReturn(Optional.of(physicianEntity));
        given(physicianRepository.save(any(PhysicianEntity.class))).willReturn(physicianEntity);
        given(modelMapper.map(physicianDto, PhysicianEntity.class)).willReturn(physicianEntity);
        given(modelMapper.map(physicianEntity, PhysicianDto.class)).willReturn(physicianDto);

        PhysicianDto result = physicianService.update(1L, physicianDto);

        assertThat(result).isEqualTo(physicianDto);
        verify(physicianRepository).save(physicianEntity);
    }

    @Test
    void delete_ShouldDeletePhysician() {
        given(physicianRepository.findById(1L)).willReturn(Optional.of(physicianEntity));

        physicianService.delete(1L);

        verify(physicianRepository).deleteById(1L);
    }

}
