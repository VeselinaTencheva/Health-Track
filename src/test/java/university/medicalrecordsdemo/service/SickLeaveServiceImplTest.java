package university.medicalrecordsdemo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import university.medicalrecordsdemo.dto.sickLeave.CreateSickLeaveDto;
import university.medicalrecordsdemo.dto.sickLeave.SickLeaveDto;
import university.medicalrecordsdemo.dto.sickLeave.UpdateSickLeaveDto;
import university.medicalrecordsdemo.model.entity.SickLeaveEntity;
import university.medicalrecordsdemo.repository.SickLeaveRepository;
import university.medicalrecordsdemo.service.sickLeave.SickLeaveServiceImpl;
import university.medicalrecordsdemo.util.enums.SickLeaveTableColumnsEnum;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class SickLeaveServiceImplTest {

    @Mock
    private SickLeaveRepository sickLeaveRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private SickLeaveServiceImpl sickLeaveService;

    private SickLeaveEntity sickLeaveEntity;
    private SickLeaveDto sickLeaveDto;

    @BeforeEach
    void setUp() {
        sickLeaveEntity = new SickLeaveEntity();
        sickLeaveEntity.setId(1L);
        sickLeaveEntity.setStartDate(LocalDate.of(2022, 1, 1));
        sickLeaveEntity.setDuration(14);

        sickLeaveDto = new SickLeaveDto();
        sickLeaveDto.setId("1");
        sickLeaveDto.setStartDate(LocalDate.of(2022, 1, 1));
        sickLeaveDto.setDuration(14);
    }

    @Test
    void findAll_ShouldReturnAllSickLeaves() {
        when(sickLeaveRepository.findAll()).thenReturn(Arrays.asList(sickLeaveEntity));
        
        Set<SickLeaveDto> result = sickLeaveService.findAll();

        verify(sickLeaveRepository).findAll();
        assertThat(result).hasSize(1);
    }

    @Test
    void findAllByPageAndSort_ShouldReturnPageOfSickLeaves() {
        Page<SickLeaveEntity> sickLeaveEntities = new PageImpl<>(Collections.singletonList(sickLeaveEntity));
        when(sickLeaveRepository.findAll(any(Pageable.class))).thenReturn(sickLeaveEntities);
        
        Page<SickLeaveDto> result = sickLeaveService.findAllByPageAndSort(0, 1, SickLeaveTableColumnsEnum.DURATION, "asc");

        verify(sickLeaveRepository).findAll(any(Pageable.class));
        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    void findById_ShouldReturnSickLeave() {
        when(sickLeaveRepository.findById(1L)).thenReturn(Optional.of(sickLeaveEntity));
        
        SickLeaveDto result = sickLeaveService.findById(1L);

        verify(sickLeaveRepository).findById(1L);
        assertThat(result).isNotNull();
    }

    @Test
    void create_ShouldSaveSickLeave() {
        when(sickLeaveRepository.save(any(SickLeaveEntity.class))).thenReturn(sickLeaveEntity);
        
        SickLeaveDto result = sickLeaveService.create(new CreateSickLeaveDto());

        verify(sickLeaveRepository).save(any(SickLeaveEntity.class));
        assertThat(result).isNotNull();
    }

    @Test
    void update_ShouldUpdateSickLeave() {
        when(sickLeaveRepository.save(any(SickLeaveEntity.class))).thenReturn(sickLeaveEntity);
        
        SickLeaveDto result = sickLeaveService.update(1L, new UpdateSickLeaveDto());

        verify(sickLeaveRepository).save(any(SickLeaveEntity.class));
        assertThat(result).isNotNull();
    }

    @Test
    void delete_ShouldRemoveSickLeave() {
        doNothing().when(sickLeaveRepository).deleteById(1L);
        
        sickLeaveService.delete(1L);

        verify(sickLeaveRepository).deleteById(1L);
    }
}
