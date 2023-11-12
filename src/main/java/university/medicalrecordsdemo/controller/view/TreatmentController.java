package university.medicalrecordsdemo.controller.view;

import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.dto.treatment.CreateTreatmentDto;
import university.medicalrecordsdemo.dto.treatment.TreatmentDto;
import university.medicalrecordsdemo.dto.treatment.UpdateTreatmentDto;
import university.medicalrecordsdemo.dto.treatment.CreateTreatmentDto;
import university.medicalrecordsdemo.model.binding.treatments.CreateTreatmentViewModel;
import university.medicalrecordsdemo.model.binding.treatments.TreatmentViewModel;
import university.medicalrecordsdemo.model.binding.treatments.UpdateTreatmentViewModel;
import university.medicalrecordsdemo.service.treatment.TreatmentService;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

// import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/treatments")
public class TreatmentController {

    private TreatmentService treatmentService;
    private ModelMapper modelMapper;

    @GetMapping
    public String getTreatments(Model model) {
        final List<TreatmentViewModel> treatments = treatmentService.findAll()
                .stream()
                .map(this::convertToTreatmentViewModel)
                .collect(Collectors.toList());
        model.addAttribute("treatments", treatments);
        return "treatments/all";
    }

    @GetMapping("/{id}")
    public String getTreatmentById(Model model, @PathVariable Long id) {
        model.addAttribute("treatment",
                modelMapper.map(treatmentService.findById(id),
                        UpdateTreatmentViewModel.class));
        return "/treatments/view";
    }

    @GetMapping("/create")
    public String createTreatment(Model model) {
        model.addAttribute("treatment", new CreateTreatmentViewModel());
        return "/treatments/create";
    }

    @PostMapping("/create")
    public String createTreatment(@ModelAttribute("treatment") CreateTreatmentViewModel treatment,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/treatments/create";
        }
        treatmentService.create(modelMapper.map(treatment,
                CreateTreatmentDto.class));
        return "redirect:/treatments";
    }

    @GetMapping("/update/{id}")
    public String editTreatment(Model model, @PathVariable Long id) {
        model.addAttribute("treatment",
                modelMapper.map(treatmentService.findById(id),
                        UpdateTreatmentViewModel.class));
        return "/treatments/edit";
    }

    @PostMapping("/update/{id}")
    public String editTreatment(@PathVariable long id, @ModelAttribute("treatment") UpdateTreatmentViewModel treatment,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/treatments/edit";
        }
        treatmentService.update(id, modelMapper.map(treatment,
                UpdateTreatmentDto.class));
        return "redirect:/treatments";
    }

    @GetMapping("/delete/{id}")
    public String deleteTreatment(@PathVariable long id) {
        treatmentService.delete(id);
        return "redirect:/treatments";
    }

    private TreatmentViewModel convertToTreatmentViewModel(TreatmentDto TreatmentDto) {
        return modelMapper.map(TreatmentDto, TreatmentViewModel.class);
    }
}
