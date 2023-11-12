package university.medicalrecordsdemo.controller.view;

import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.dto.sickLeave.CreateSickLeaveDto;
import university.medicalrecordsdemo.dto.sickLeave.SickLeaveDto;
import university.medicalrecordsdemo.dto.sickLeave.UpdateSickLeaveDto;
import university.medicalrecordsdemo.model.binding.sickLeaves.CreateSickLeaveViewModel;
import university.medicalrecordsdemo.model.binding.sickLeaves.SickLeaveViewModel;
import university.medicalrecordsdemo.model.binding.sickLeaves.UpdateSickLeaveViewModel;
import university.medicalrecordsdemo.service.sickLeave.SickLeaveService;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/sick-leaves")
public class SickLeaveController {
    private SickLeaveService sickLeaveService;
    private ModelMapper modelMapper;

    @GetMapping
    public String getSickLeaves(Model model) {
        final List<SickLeaveViewModel> sickLeaves = sickLeaveService.findAll()
                .stream()
                .map(this::convertToSickLeaveViewModel)
                .collect(Collectors.toList());
        model.addAttribute("sickLeaves", sickLeaves);
        return "sick-leaves/all";
    }

    @GetMapping("/{id}")
    public String getSickLeaveById(Model model, @PathVariable Long id) {
        model.addAttribute("sickLeave",
                modelMapper.map(sickLeaveService.findById(id),
                        UpdateSickLeaveViewModel.class));
        return "/sick-leaves/view";
    }

    @GetMapping("/create")
    public String createSickLeave(Model model) {
        model.addAttribute("sickLeave", new CreateSickLeaveViewModel());
        return "/sick-leaves/create";
    }

    @PostMapping("/create")
    public String createSickLeave(@ModelAttribute("sickLeave") CreateSickLeaveViewModel sickLeave,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/sick-leaves/create";
        }
        sickLeaveService.create(modelMapper.map(sickLeave,
                CreateSickLeaveDto.class));
        return "redirect:/sick-leaves";
    }

    @GetMapping("/update/{id}")
    public String editSickLeave(Model model, @PathVariable Long id) {
        model.addAttribute("sickLeave",
                modelMapper.map(sickLeaveService.findById(id),
                        UpdateSickLeaveViewModel.class));
        return "/sick-leaves/edit";
    }

    @PostMapping("/update/{id}")
    public String editSickLeave(@PathVariable long id, @ModelAttribute("sickLeave") UpdateSickLeaveViewModel sickLeave,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/sick-leaves/edit";
        }
        sickLeaveService.update(id, modelMapper.map(sickLeave,
                UpdateSickLeaveDto.class));
        return "redirect:/sick-leaves";
    }

    @GetMapping("/delete/{id}")
    public String deleteSickLeave(@PathVariable long id) {
        sickLeaveService.delete(id);
        return "redirect:/sick-leaves";
    }

    private SickLeaveViewModel convertToSickLeaveViewModel(SickLeaveDto SickLeaveDto) {
        return modelMapper.map(SickLeaveDto, SickLeaveViewModel.class);
    }
}
