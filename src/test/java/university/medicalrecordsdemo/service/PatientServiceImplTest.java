package university.medicalrecordsdemo.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import university.medicalrecordsdemo.dto.patient.PatientDto;
import university.medicalrecordsdemo.dto.physician.PhysicianDto;
import university.medicalrecordsdemo.model.entity.PatientEntity;
import university.medicalrecordsdemo.model.entity.PhysicianEntity;
import university.medicalrecordsdemo.model.entity.RoleEntity;
import university.medicalrecordsdemo.model.entity.SpecialtyType;
import university.medicalrecordsdemo.repository.PatientRepository;
import university.medicalrecordsdemo.repository.RoleRepository;
import university.medicalrecordsdemo.service.patient.PatientServiceImpl;
import university.medicalrecordsdemo.service.physician.PhysicianService;
import university.medicalrecordsdemo.util.enums.PatientTableColumnsEnum;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PhysicianService physicianService;

    @Mock
    private RoleRepository  roleRepository;

    @Mock
    private ModelMapper modelMapper;


    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private PatientServiceImpl patientService;

    private PatientEntity patientEntity;
    private PatientDto patientDto;

    @BeforeEach
    void setUp() {
         // Set up PatientEntity
        patientEntity = new PatientEntity();
        patientEntity.setId(1L);
        patientEntity.setSsn("123456788");
        patientEntity.setFirstName("John");
        patientEntity.setLastName("Doe");
        patientEntity.setGender("Male");
        patientEntity.setBirthDate(LocalDate.now());
        patientEntity.setUsername("johndoe2@example.com");
        patientEntity.setPassword("password");
        patientEntity.setHasInsurance(true);
        // Assuming you have a role entity for the patient
        RoleEntity patientRole = new RoleEntity();
        // Initialize role as needed
        patientEntity.setRoles(Set.of(patientRole));

        // Set up PatientDto
        patientDto = new PatientDto();
        patientDto.setId(patientEntity.getId());
        patientDto.setFirstName(patientEntity.getFirstName());
        patientDto.setLastName(patientEntity.getLastName());
        patientDto.setBirthDate(patientEntity.getBirthDate());
        patientDto.setHasInsurance(patientEntity.isHasInsurance());
        patientDto.setUsername(patientEntity.getUsername());
        patientDto.setPassword(patientEntity.getPassword());
        patientDto.setSsn(patientEntity.getSsn());

        PhysicianDto physicianDto = new PhysicianDto();
        physicianDto.setSsn("123456789");
        physicianDto.setFirstName("John");
        physicianDto.setLastName("Doe");
        physicianDto.setGender("Male");
        physicianDto.setBirthDate(LocalDate.of(1970, 1, 1));
        physicianDto.setUsername("johndoe@example.com");
        physicianDto.setPassword("password");
        physicianDto.setMedicalLicenseNumber("MED123456");
        physicianDto.setSpecialties(new HashSet<>(Arrays.asList(SpecialtyType.GENERAL_PRACTICE)));
        patientDto.setGeneralPractitioner(physicianDto);
    }

    @Test
    void findAll_ShouldReturnAllPatients() {
        List<PatientEntity> patientEntities = Collections.singletonList(patientEntity);
        when(patientRepository.findAll()).thenReturn(patientEntities);
        when(modelMapper.map(any(PatientEntity.class), eq(PatientDto.class))).thenReturn(patientDto);

        Set<PatientDto> result = patientService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.iterator().next()).isEqualTo(patientDto);
    }

    @Test
    void findById_ShouldReturnPatient() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patientEntity));
        when(modelMapper.map(patientEntity, PatientDto.class)).thenReturn(patientDto);

        PatientDto result = patientService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(patientDto);
    }
    @Test
    void create_ShouldSavePatient() {
        PhysicianEntity physicianEntityMock = new PhysicianEntity();
        when(modelMapper.map(any(PhysicianDto.class), eq(PhysicianEntity.class))).thenReturn(physicianEntityMock);
    
        when(patientRepository.save(any(PatientEntity.class))).thenReturn(patientEntity);
        when(modelMapper.map(any(PatientDto.class), eq(PatientEntity.class))).thenReturn(patientEntity);
        when(modelMapper.map(patientEntity, PatientDto.class)).thenReturn(patientDto);
    
        PatientDto createdPatient = patientService.create(patientDto);
    
        assertThat(createdPatient).isEqualTo(patientDto);
        verify(patientRepository).save(patientEntity);
    }

    @Test
    void delete_ShouldDeletePatient() {
        Long patientId = 1L;
        doNothing().when(patientRepository).deleteById(patientId);

        patientService.delete(patientId);

        verify(patientRepository).deleteById(patientId);
    }

    @Test
    void findAllByPageAndSort_ShouldReturnPagedPatients() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by(PatientTableColumnsEnum.BIRTH_DATE.getColumnName()).ascending());
        List<PatientEntity> patientEntities = Collections.singletonList(patientEntity);
        Page<PatientEntity> patientPage = new PageImpl<>(patientEntities, pageable, patientEntities.size());

        when(patientRepository.findAll(pageable)).thenReturn(patientPage);
        when(modelMapper.map(any(PatientEntity.class), eq(PatientDto.class))).thenReturn(patientDto);

        Page<PatientDto> resultPage = patientService.findAllByPageAndSort(0, 5, PatientTableColumnsEnum.BIRTH_DATE, "ASC");

        assertThat(resultPage.getContent()).hasSize(1);
        assertThat(resultPage.getContent().get(0)).isEqualTo(patientDto);
    }
}
