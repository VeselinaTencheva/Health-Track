package university.medicalrecordsdemo.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import university.medicalrecordsdemo.dto.diagnosis.DiagnosisDto;
import university.medicalrecordsdemo.model.entity.DiagnosisEntity;
import university.medicalrecordsdemo.model.entity.AppointmentEntity;
import university.medicalrecordsdemo.model.entity.DepartmentType;
import university.medicalrecordsdemo.repository.DiagnosisRepository;
import university.medicalrecordsdemo.service.diagnosis.DiagnosisServiceImpl;
import university.medicalrecordsdemo.util.enums.DiagnosisTableColumnsEnum;

@ExtendWith(MockitoExtension.class)
class DiagnosisServiceImplTest {

    @Mock
    private DiagnosisRepository diagnosisRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private DiagnosisServiceImpl diagnosisService;

    private DiagnosisEntity diagnosisEntity;
    private DiagnosisDto diagnosisDto;

    @BeforeEach
    void setUp() {
        diagnosisEntity = new DiagnosisEntity();
        diagnosisEntity.setId(1L);
        diagnosisEntity.setCode("D001");
        diagnosisEntity.setName("Test Diagnosis");
        diagnosisEntity.setCategory(DepartmentType.CARDIOLOGY);

        diagnosisDto = new DiagnosisDto();
        diagnosisDto.setId(diagnosisEntity.getId());
        diagnosisDto.setCode(diagnosisEntity.getCode());
        diagnosisDto.setName(diagnosisEntity.getName());
        diagnosisDto.setCategory(diagnosisEntity.getCategory().name());
        
        lenient().when(modelMapper.map(diagnosisEntity, DiagnosisDto.class)).thenReturn(diagnosisDto);
        lenient().when(modelMapper.map(diagnosisDto, DiagnosisEntity.class)).thenReturn(diagnosisEntity);
    }

    @Test
    void findAll_ShouldReturnAllDiagnoses() {
        when(diagnosisRepository.findAll()).thenReturn(Collections.singletonList(diagnosisEntity));

        Set<DiagnosisDto> result = diagnosisService.findAll();

        assertThat(result).hasSize(1);
        verify(diagnosisRepository).findAll();
    }

    @Test
    void findAllByCategory_ShouldReturnDiagnosesForGivenCategories() {
        Set<DepartmentType> categories = Set.of(DepartmentType.CARDIOLOGY, DepartmentType.NEUROLOGY);
        List<DiagnosisEntity> cardiologyDiagnoses = List.of(new DiagnosisEntity(/* set properties */));
        List<DiagnosisEntity> neurologyDiagnoses = List.of(new DiagnosisEntity(/* set properties */));
        when(diagnosisRepository.findAllByCategory(DepartmentType.CARDIOLOGY)).thenReturn(cardiologyDiagnoses);
        when(diagnosisRepository.findAllByCategory(DepartmentType.NEUROLOGY)).thenReturn(neurologyDiagnoses);
        when(modelMapper.map(any(DiagnosisEntity.class), eq(DiagnosisDto.class)))
                .thenAnswer(invocation -> new DiagnosisDto(/* set properties based on entity */));

        Set<DiagnosisDto> result = diagnosisService.findAllByCategory(categories);

        assertEquals(2, result.size());
    }

    @Test
    void findAllByPageAndSort_ShouldReturnPagedDiagnoses() {
        int page = 0;
        int size = 2;
        DiagnosisTableColumnsEnum sortField = DiagnosisTableColumnsEnum.NAME;
        String sortDirection = "ASC";
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortField.getColumnName()));
        Page<DiagnosisEntity> diagnosisPage = new PageImpl<>(List.of(new DiagnosisEntity(), new DiagnosisEntity()));
        when(diagnosisRepository.findAll(pageRequest)).thenReturn(diagnosisPage);
        when(modelMapper.map(any(DiagnosisEntity.class), eq(DiagnosisDto.class)))
                .thenAnswer(invocation -> new DiagnosisDto(/* set properties based on entity */));

        Page<DiagnosisDto> result = diagnosisService.findAllByPageAndSort(page, size, sortField, sortDirection);

        assertEquals(2, result.getContent().size());
    }

    @Test
    void findAllByAppointments_ShouldReturnDiagnosesForGivenAppointments() {
        AppointmentEntity appointment1 = new AppointmentEntity();
        AppointmentEntity appointment2 = new AppointmentEntity();
        Set<AppointmentEntity> appointments = Set.of(appointment1, appointment2);
        DiagnosisEntity diagnosis1 = new DiagnosisEntity();
        DiagnosisEntity diagnosis2 = new DiagnosisEntity();
        when(diagnosisRepository.findByAppointments(appointment1)).thenReturn(diagnosis1);
        when(diagnosisRepository.findByAppointments(appointment2)).thenReturn(diagnosis2);
        when(modelMapper.map(any(DiagnosisEntity.class), eq(DiagnosisDto.class)))
                .thenAnswer(invocation -> new DiagnosisDto());

        Set<DiagnosisDto> result = diagnosisService.findAllByAppointments(appointments);

        assertEquals(2, result.size());
    }

    @Test
    void findById_ShouldReturnDiagnosis() {
        when(diagnosisRepository.findById(1L)).thenReturn(Optional.of(diagnosisEntity));

        DiagnosisDto result = diagnosisService.findById(1L);

        assertThat(result).isEqualTo(diagnosisDto);
        verify(diagnosisRepository).findById(1L);
    }

    @Test
    void create_ShouldSaveDiagnosis() {
        when(diagnosisRepository.save(any(DiagnosisEntity.class))).thenReturn(diagnosisEntity);

        DiagnosisDto createdDiagnosis = diagnosisService.create(diagnosisDto);

        assertThat(createdDiagnosis).isEqualTo(diagnosisDto);
        verify(diagnosisRepository).save(diagnosisEntity);
    }

    @Test
    void delete_ShouldDeleteDiagnosis() {
        diagnosisService.delete(1L);

        verify(diagnosisRepository).deleteById(1L);
    }

}
