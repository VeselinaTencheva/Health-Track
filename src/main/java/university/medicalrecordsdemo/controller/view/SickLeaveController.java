package university.medicalrecordsdemo.controller.view;

import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.dto.sickLeave.CreateSickLeaveDto;
import university.medicalrecordsdemo.dto.sickLeave.SickLeaveDto;
import university.medicalrecordsdemo.dto.sickLeave.UpdateSickLeaveDto;
import university.medicalrecordsdemo.model.binding.sickLeaves.CreateSickLeaveViewModel;
import university.medicalrecordsdemo.model.binding.sickLeaves.SickLeaveViewModel;
import university.medicalrecordsdemo.model.binding.sickLeaves.UpdateSickLeaveViewModel;
import university.medicalrecordsdemo.model.entity.RoleType;
import university.medicalrecordsdemo.service.sickLeave.SickLeaveService;
import university.medicalrecordsdemo.service.user.UserService;
import university.medicalrecordsdemo.util.enums.SickLeaveTableColumnsEnum;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
@RequestMapping("/sick-leaves")
public class SickLeaveController {
    private SickLeaveService sickLeaveService;
    private UserService userService;
    private ModelMapper modelMapper;
    private static final String DEFAULT_SORT_FIELD = "START_DATE";

    @GetMapping
    public String getSickLeaves(Authentication authentication, Model model,
                                @RequestParam(name = "page", defaultValue = "0") int page,
                                @RequestParam(name = "size", defaultValue = "5") int size,
                                @RequestParam(name = "sortField", defaultValue = DEFAULT_SORT_FIELD) String sortField,
                                @RequestParam(name = "sortDirection", defaultValue = "asc") String sortDirection) {
        
        Page<SickLeaveViewModel> sickLeavePage = fetchSickLeavesBasedOnRole(authentication, page, size, sortField, sortDirection);
        
        List<Integer> pageNumbers = IntStream.rangeClosed(0, sickLeavePage.getTotalPages() - 1)
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
            sickLeaveViewModel.setPhysicianLastName(sickLeaveDto.getAppointmentDto().getPhysician().getLastName());
        }
        return sickLeaveViewModel;
    }

    private Long fetchUserIdFromUsername(String username) {
        return userService.fetchUserIdFromUsername(username);
    }

    private Page<SickLeaveViewModel> fetchSickLeavesBasedOnRole(Authentication authentication, int page, int size, String sortField, String sortDirection) {
        Page<SickLeaveDto> sickLeaveDtoPage;
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(RoleType.ROLE_ADMIN.name()))) {
            // Fetch all sick leaves for admins
            sickLeaveDtoPage = sickLeaveService.findAllByPageAndSort(page, size, SickLeaveTableColumnsEnum.valueOf(sortField), sortDirection);
        } else {
            Long userId = fetchUserIdFromUsername(authentication.getName());
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(RoleType.ROLE_GENERAL_PRACTITIONER.name())) || 
                authentication.getAuthorities().contains(new SimpleGrantedAuthority(RoleType.ROLE_PHYSICIAN.name()))) {
                // Fetch sick leaves for the logged-in physician
                return sickLeaveService.findAllByPhysicianId(userId, page, size, SickLeaveTableColumnsEnum.valueOf(sortField), sortDirection).map(this::convertToSickLeaveViewModel);
            } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(RoleType.ROLE_PATIENT.name()))) {
                // Fetch sick leaves for the logged-in patient
                return sickLeaveService.findAllByPatientId(userId, page, size, SickLeaveTableColumnsEnum.valueOf(sortField), sortDirection).map(this::convertToSickLeaveViewModel);
            } else {
                return Page.empty();
            }
        }
        return sickLeaveDtoPage.map(this::convertToSickLeaveViewModel);
    }

}
