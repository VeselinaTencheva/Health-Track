package university.medicalrecordsdemo.controller.view;

import java.util.List;
import java.util.stream.Collectors;

// import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
// import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
// import org.springframework.validation.BindingResult;
// import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

// import javax.validation.Valid;
// import java.util.List;
// import java.util.Optional;
// import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.dto.physician.PhysicianDto;
import university.medicalrecordsdemo.model.binding.physicians.CreatePhysicianViewModel;
import university.medicalrecordsdemo.model.binding.physicians.PhysiciansViewModel;
import university.medicalrecordsdemo.model.binding.physicians.UpdatePhysicianViewModel;
import university.medicalrecordsdemo.model.entity.DepartmentType;
import university.medicalrecordsdemo.model.entity.SpecialtyType;
import university.medicalrecordsdemo.service.physician.PhysicianService;
import university.medicalrecordsdemo.service.user.UserService;

@Controller
@AllArgsConstructor
@RequestMapping("/physicians")
public class PhysicianController {

    private PhysicianService physicianService;

    private UserService usersService;

    // private GeneralPractitionerService generalPractitionerService;
    private ModelMapper modelMapper;

    @GetMapping
    public String getPhysicians(Model model) {
        final List<PhysiciansViewModel> physicians = physicianService.findAll()
                .stream()
                .map(this::convertToPhysiciansViewModel)
                .collect(Collectors.toList());
        model.addAttribute("physicians", physicians);
        return "physicians/all";
    }

    @GetMapping("/{id}")
    public String getPhysicianById(Model model, @PathVariable Long id) {
        PhysiciansViewModel physician = convertToPhysiciansViewModel(physicianService.findById(id));
        model.addAttribute("physician", physician);
        model.addAttribute("departments", DepartmentType.values());
        return "physicians/view";
    }

    @GetMapping("/create")
    public String showCreatePhysicianForm(Model model) {
        model.addAttribute("specialities", SpecialtyType.values());
        model.addAttribute("physician", new CreatePhysicianViewModel());
        return "/physicians/create";
    }

    @PostMapping("/create")
    public String createPhysician(Model model, @ModelAttribute("physician") CreatePhysicianViewModel physician,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("specialities", SpecialtyType.values());
            return "/physicians/create";
        }
        PhysicianDto createPhysicianDTO = modelMapper.map(physician,
                PhysicianDto.class);
        physicianService.create(createPhysicianDTO);
        return "redirect:/physicians";
    }

    @GetMapping("/update/{id}")
    public String showEditPhysicianForm(Model model, @PathVariable Long id) {
        model.addAttribute("physician", modelMapper.map(physicianService.findById(id), UpdatePhysicianViewModel.class));
        model.addAttribute("specialities", SpecialtyType.values());
        return "/physicians/edit";
    }

    @PostMapping("/update/{id}")
    public String updatePhysicians(@PathVariable long id,
            @ModelAttribute("physician") UpdatePhysicianViewModel physician,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/physicians/edit";
        }
        physicianService.update(id, modelMapper.map(physician,
                PhysicianDto.class));
        return "redirect:/physicians";
    }

    @GetMapping("/delete/{id}")
    public String deletePhysician(@PathVariable long id) {
        physicianService.delete(id);
        return "redirect:/physicians";
    }

    private PhysiciansViewModel convertToPhysiciansViewModel(PhysicianDto physicianDTO) {
        final PhysiciansViewModel physiciansViewModel = modelMapper.map(physicianDTO, PhysiciansViewModel.class);
        physiciansViewModel.setGP(physicianDTO.getSpecialties().contains(SpecialtyType.GENERAL_PRACTICE));
        physiciansViewModel.setPatientsCount(physicianDTO.getPatients().size());

        return physiciansViewModel;
    }
}
