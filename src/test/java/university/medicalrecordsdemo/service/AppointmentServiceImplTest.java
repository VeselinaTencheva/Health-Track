package university.medicalrecordsdemo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import university.medicalrecordsdemo.dto.appointment.AppointmentDto;
import university.medicalrecordsdemo.dto.appointment.CreateAppointmentDto;
import university.medicalrecordsdemo.dto.appointment.UpdateAppointmentDto;
import university.medicalrecordsdemo.dto.diagnosis.DiagnosisDto;
import university.medicalrecordsdemo.dto.patient.PatientDto;
import university.medicalrecordsdemo.dto.physician.PhysicianDto;
import university.medicalrecordsdemo.model.entity.AppointmentEntity;
import university.medicalrecordsdemo.model.entity.DepartmentType;
import university.medicalrecordsdemo.model.entity.DiagnosisEntity;
import university.medicalrecordsdemo.model.entity.PatientEntity;
import university.medicalrecordsdemo.model.entity.PhysicianEntity;
import university.medicalrecordsdemo.model.entity.RoleEntity;
import university.medicalrecordsdemo.model.entity.RoleType;
import university.medicalrecordsdemo.model.entity.SpecialtyType;
import university.medicalrecordsdemo.repository.AppointmentRepository;
import university.medicalrecordsdemo.service.appointment.AppointmentServiceImpl;
import university.medicalrecordsdemo.service.patient.PatientServiceImpl;
import university.medicalrecordsdemo.service.physician.PhysicianServiceImpl;
import university.medicalrecordsdemo.util.enums.AppointmentTableColumnsEnum;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceImplTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @Mock
    private PatientServiceImpl patientServiceImpl;

    @Mock
    private PhysicianServiceImpl physicianServiceImpl;

    private AppointmentEntity appointmentEntity;
    private AppointmentDto appointmentDto;

    @BeforeEach
    void setUp() {
        PhysicianEntity physicianEntity = new PhysicianEntity();
        physicianEntity.setSsn("123456789");
        physicianEntity.setFirstName("John");
        physicianEntity.setLastName("Doe");
        physicianEntity.setGender("Male");
        physicianEntity.setBirthDate(LocalDate.of(1970, 1, 1));
        physicianEntity.setUsername("johndoe@example.com");
        physicianEntity.setPassword("password");
        physicianEntity.setMedicalLicenseNumber("MED123456");
        physicianEntity.setSpecialties(new HashSet<>(Arrays.asList(SpecialtyType.GENERAL_PRACTICE)));
        RoleEntity gpRole = new RoleEntity();
        gpRole.setAuthority(RoleType.ROLE_GENERAL_PRACTITIONER);
        physicianEntity.setRoles(Set.of(gpRole));


        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setId(1L);
        patientEntity.setSsn("123456788");
        patientEntity.setFirstName("John");
        patientEntity.setLastName("Doe");
        patientEntity.setGender("Male");
        patientEntity.setBirthDate(LocalDate.now());
        patientEntity.setUsername("johndoe2@example.com");
        patientEntity.setPassword("password");
        patientEntity.setHasInsurance(true);
        RoleEntity patientRole = new RoleEntity();
        patientRole.setAuthority(RoleType.ROLE_PATIENT);
        patientEntity.setRoles(Set.of(patientRole));


        DiagnosisEntity diagnosisEntity = new DiagnosisEntity();
        diagnosisEntity.setId(3L);
        diagnosisEntity.setCode("J10.1");
        diagnosisEntity.setCategory(DepartmentType.ANESTHESIOLOGY);
        diagnosisEntity.setName("Flu");

        appointmentEntity = new AppointmentEntity();
        appointmentEntity.setId(5L);
        appointmentEntity.setDate(LocalDate.now());
        appointmentEntity.setPatient(patientEntity);
        appointmentEntity.setPhysician(physicianEntity);
        appointmentEntity.setDiagnosis(diagnosisEntity);
        appointmentEntity.setTreatment("Rest and hydration");

        DiagnosisDto diagnosisDto = new DiagnosisDto(); 
        lenient().when(modelMapper.map(any(DiagnosisEntity.class), eq(DiagnosisDto.class))).thenReturn(diagnosisDto);

        PatientDto patientDto = new PatientDto(); 
        lenient().when(modelMapper.map(any(PatientEntity.class), eq(PatientDto.class))).thenReturn(patientDto);

        PhysicianDto physicianDto = new PhysicianDto(); 
        lenient().when(modelMapper.map(any(PhysicianEntity.class), eq(PhysicianDto.class))).thenReturn(physicianDto);

        appointmentDto = new AppointmentDto(); 
        lenient().when(modelMapper.map(any(AppointmentEntity.class), eq(AppointmentDto.class))).thenReturn(appointmentDto);

    }

    @Test
    void findAll_ShouldReturnAllAppointments() {
        when(appointmentRepository.findAll()).thenReturn(List.of(appointmentEntity));

        Set<AppointmentDto> result = appointmentService.findAll();

        assertThat(result).hasSize(1);
        verify(appointmentRepository).findAll();
    }

    @Test
    void findAllByPageAndSort_ShouldReturnPagedAppointments() {
        int page = 0;
        int size = 10;
        String sortField = "date";
        String sortDirection = "ASC";
        AppointmentTableColumnsEnum sortFieldEnum = AppointmentTableColumnsEnum.valueOf(sortField.toUpperCase());
        
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sortFieldEnum.getColumnName());
        
        List<AppointmentEntity> appointments = List.of(appointmentEntity);
        
        Page<AppointmentEntity> appointmentPage = new PageImpl<>(appointments, pageRequest, appointments.size());
        
        when(appointmentRepository.findAll(any(PageRequest.class))).thenReturn(appointmentPage);
        
        when(modelMapper.map(any(AppointmentEntity.class), eq(AppointmentDto.class))).thenReturn(appointmentDto);
        
        Page<AppointmentDto> result = appointmentService.findAllByPageAndSort(page, size, sortFieldEnum, sortDirection);
        
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(appointmentDto);
        assertThat(result.getPageable().getPageNumber()).isEqualTo(page);
        assertThat(result.getPageable().getPageSize()).isEqualTo(size);
        verify(appointmentRepository).findAll(pageRequest);
    }

    @Test
    void findById_ShouldReturnAppointment() {
        when(appointmentRepository.findById(anyLong())).thenReturn(Optional.of(appointmentEntity));
        when(modelMapper.map(appointmentEntity, AppointmentDto.class)).thenReturn(appointmentDto);

        AppointmentDto result = appointmentService.findById(1L);

        assertThat(result).isEqualTo(appointmentDto);
        verify(appointmentRepository).findById(1L);
    }

    @Test
    void create_ShouldSaveAppointment() {
        CreateAppointmentDto createAppointmentDto = new CreateAppointmentDto();
        // Setup createAppointmentDto as needed

        when(modelMapper.map(createAppointmentDto, AppointmentEntity.class)).thenReturn(appointmentEntity);
        when(appointmentRepository.save(any(AppointmentEntity.class))).thenReturn(appointmentEntity);
        when(modelMapper.map(appointmentEntity, AppointmentDto.class)).thenReturn(appointmentDto);

        AppointmentDto result = appointmentService.create(createAppointmentDto);

        assertThat(result).isEqualTo(appointmentDto);
        verify(appointmentRepository).save(appointmentEntity);
    }

    @Test
    void update_ShouldUpdateAppointment() {
        UpdateAppointmentDto updateAppointmentDto = new UpdateAppointmentDto();
        // Setup updateAppointmentDto as needed

        when(appointmentRepository.saveAndFlush(any(AppointmentEntity.class))).thenReturn(appointmentEntity);
        when(modelMapper.map(updateAppointmentDto, AppointmentEntity.class)).thenReturn(appointmentEntity);
        when(modelMapper.map(appointmentEntity, AppointmentDto.class)).thenReturn(appointmentDto);

        AppointmentDto result = appointmentService.update(1L, updateAppointmentDto);

        assertThat(result).isEqualTo(appointmentDto);
        verify(appointmentRepository).saveAndFlush(appointmentEntity);
    }
}

