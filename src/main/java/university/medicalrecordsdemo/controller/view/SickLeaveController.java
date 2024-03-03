package university.medicalrecordsdemo.controller.view;

import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.dto.sickLeave.CreateSickLeaveDto;
import university.medicalrecordsdemo.dto.sickLeave.SickLeaveDto;
import university.medicalrecordsdemo.dto.sickLeave.UpdateSickLeaveDto;
import university.medicalrecordsdemo.model.binding.sickLeaves.CreateSickLeaveViewModel;
import university.medicalrecordsdemo.model.binding.sickLeaves.SickLeaveViewModel;
import university.medicalrecordsdemo.model.binding.sickLeaves.UpdateSickLeaveViewModel;
import university.medicalrecordsdemo.service.sickLeave.SickLeaveService;
import university.medicalrecordsdemo.util.enums.SickLeaveTableColumnsEnum;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
@RequestMapping("/sick-leaves")
public class SickLeaveController {
    private SickLeaveService sickLeaveService;
    private ModelMapper modelMapper;
    private static final String DEFAULT_SORT_FIELD = "START_DATE";

    @GetMapping
    public String getSickLeaves(Model model,
                                @RequestParam(name = "page", defaultValue = "0") int page,
                                @RequestParam(name = "size", defaultValue = "5") int size,
                                @RequestParam(name = "sortField", defaultValue = DEFAULT_SORT_FIELD) String sortField,
                                @RequestParam(name = "sortDirection", defaultValue = "asc") String sortDirection) {
        Page<SickLeaveViewModel> sickLeavePage;
        List<Integer> pageNumbers;

        // SickLeaveTableColumnsEnum sortFieldEnum = SickLeaveTableColumnsEnum.valueOf(sortField.toUpperCase());
        SickLeaveTableColumnsEnum sortFieldEnum = SickLeaveTableColumnsEnum.valueOf(sortField);

        if (sortFieldEnum.equals(SickLeaveTableColumnsEnum.START_DATE) || sortFieldEnum.equals(SickLeaveTableColumnsEnum.DURATION)) {
            sickLeavePage = sickLeaveService.findAllByPageAndSort(page, size, sortFieldEnum, sortDirection)
                                            .map(this::convertToSickLeaveViewModel);
        } else {
            List<SickLeaveViewModel> SickLeaveViewModels = sickLeaveService.findAll()
                                                                            .stream()
                                                                            .map(this::convertToSickLeaveViewModel)
                                                                            .collect(Collectors.toList());

            if ("desc".equalsIgnoreCase(sortDirection)) {
                if (SickLeaveTableColumnsEnum.PATIENT_FULL_NAME.equals(sortFieldEnum)) {
                    SickLeaveViewModels.sort(Comparator.comparing(SickLeaveViewModel::getPatientFullName).reversed());
                } else if (SickLeaveTableColumnsEnum.PHYSICIAN_FULL_NAME.equals(sortFieldEnum)) {
                    SickLeaveViewModels.sort(Comparator.comparing(SickLeaveViewModel::getPhysicianFullName).reversed());
                }
            } else {
                if (SickLeaveTableColumnsEnum.PATIENT_FULL_NAME.equals(sortFieldEnum)) {
                    SickLeaveViewModels.sort(Comparator.comparing(SickLeaveViewModel::getPatientFullName));
                } else if (SickLeaveTableColumnsEnum.PHYSICIAN_FULL_NAME.equals(sortFieldEnum)) {
                    SickLeaveViewModels.sort(Comparator.comparing(SickLeaveViewModel::getPhysicianFullName));
                }
            }

            int start = (int) (page * size);
            int end = Math.min((start + size), SickLeaveViewModels.size());
            sickLeavePage = new PageImpl<>(SickLeaveViewModels.subList(start, end), PageRequest.of(page, size), SickLeaveViewModels.size());
        }
        
        pageNumbers = IntStream.rangeClosed(0, sickLeavePage.getTotalPages() - 1)
                            .boxed()
                            .collect(Collectors.toList());

        model.addAttribute("sickLeavePage", sickLeavePage);
        model.addAttribute("currentPage", page);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("size", size);
        model.addAttribute("sickLeaves", sickLeavePage.getContent());
        model.addAttribute("firstPage", 0);
        model.addAttribute("totalPages", sickLeavePage.getTotalPages());
        model.addAttribute("pageNumbers", pageNumbers); 
        model.addAttribute("columnsEnum", SickLeaveTableColumnsEnum.values());

        model.addAttribute("contentTemplate", "sick-leaves/all");

        return "layout";
    }

    @GetMapping("/{id}")
    public String getSickLeaveById(Model model, @PathVariable Long id) {
        SickLeaveViewModel sickLeave = convertToSickLeaveViewModel(sickLeaveService.findById(id));
        model.addAttribute("sickLeave", sickLeave);
        model.addAttribute("contentTemplate", "sick-leaves/view");
        return "layout";
    }

    @GetMapping("/create")
    public String createSickLeave(Model model) {
        model.addAttribute("sickLeave", new CreateSickLeaveViewModel());
        model.addAttribute("contentTemplate", "sick-leaves/create");
        return "layout";
    }

    @PostMapping("/create")
    public String createSickLeave(Model model, @Valid @ModelAttribute("sickLeave") CreateSickLeaveViewModel sickLeave,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("contentTemplate", "sick-leaves/create");
            return "layout";
        }
        sickLeaveService.create(modelMapper.map(sickLeave,
                CreateSickLeaveDto.class));
        return "redirect:/sick-leaves";
    }

    @GetMapping("/update/{id}")
    public String editSickLeave(Model model, @PathVariable Long id) {
        UpdateSickLeaveViewModel sickLeave = modelMapper.map(sickLeaveService.findById(id),
        UpdateSickLeaveViewModel.class);
        model.addAttribute("sickLeave", sickLeave);
        model.addAttribute("contentTemplate", "sick-leaves/edit");

        return "layout";
    }

    @PostMapping("/update/{id}")
    public String editSickLeave(Model model, @PathVariable long id,@Valid @ModelAttribute("sickLeave") UpdateSickLeaveViewModel sickLeave,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("contentTemplate", "sick-leaves/edit");
            return "layout";
        }
         
        UpdateSickLeaveDto updateSickLeaveDto = modelMapper.map(sickLeave, UpdateSickLeaveDto.class);
        updateSickLeaveDto.setStartDate(LocalDate.parse(sickLeave.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        sickLeaveService.update(id, updateSickLeaveDto);
        return "redirect:/sick-leaves";
    }

    @GetMapping("/delete/{id}")
    public String deleteSickLeave(@PathVariable long id) {
        sickLeaveService.delete(id);
        return "redirect:/sick-leaves";
    }

    private SickLeaveViewModel convertToSickLeaveViewModel(SickLeaveDto sickLeaveDto) {
        SickLeaveViewModel sickLeaveViewModel = modelMapper.map(sickLeaveDto, SickLeaveViewModel.class);
        if (sickLeaveDto.getAppointmentDto() != null) {
            sickLeaveViewModel.setPatientId(sickLeaveDto.getAppointmentDto().getPatient().getId());
            sickLeaveViewModel.setPatientFirstName(sickLeaveDto.getAppointmentDto().getPatient().getFirstName());
            sickLeaveViewModel.setPatientLastName(sickLeaveDto.getAppointmentDto().getPatient().getLastName());
            sickLeaveViewModel.setPhysicianId(sickLeaveDto.getAppointmentDto().getPhysician().getId());
            sickLeaveViewModel.setPhysicianFirstName(sickLeaveDto.getAppointmentDto().getPhysician().getFirstName());
            sickLeaveViewModel.setPhysicianLastName(sickLeaveDto.getAppointmentDto().getPatient().getLastName());
        }
        return sickLeaveViewModel;
    }
}
