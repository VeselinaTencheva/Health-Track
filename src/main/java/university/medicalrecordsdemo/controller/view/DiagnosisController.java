package university.medicalrecordsdemo.controller.view;

import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.dto.diagnosis.DiagnosisDto;
import university.medicalrecordsdemo.dto.diagnosis.UpdateDiagnosisDto;
import university.medicalrecordsdemo.model.binding.diagnoses.CreateDiagnoseViewModel;
import university.medicalrecordsdemo.model.binding.diagnoses.DiagnoseViewModel;
import university.medicalrecordsdemo.service.diagnosis.DiagnosisService;

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
@RequestMapping("/diagnosis")
public class DiagnosisController {
    private DiagnosisService diagnosisService;
    private ModelMapper modelMapper;

    @GetMapping
    public String getDiagnosis(Model model) {
        final List<DiagnoseViewModel> diagnoses = diagnosisService.findAll()
                .stream()
                .map(this::convertToDiagnoseViewModel)
                .collect(Collectors.toList());
        model.addAttribute("diagnoses", diagnoses);
        return "diagnoses/all";
    }

    @GetMapping("/create")
    public String createDiagnosis(Model model) {
        model.addAttribute("diagnosis", new CreateDiagnoseViewModel());
        return "/diagnoses/create";
    }

    @GetMapping("/{id}")
    public String getDiagnoseById(Model model, @PathVariable Long id) {
        model.addAttribute("diagnose", modelMapper.map(diagnosisService.findById(id),
                DiagnoseViewModel.class));
        return "diagnoses/view";
    }

    @PostMapping("/create")
    public String createDiagnosis(@ModelAttribute("diagnosis") CreateDiagnoseViewModel diagnosis,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/diagnoses/create";
        }
        diagnosisService.create(modelMapper.map(diagnosis, DiagnosisDto.class));
        return "redirect:/diagnosis";
    }

    @GetMapping("/update/{id}")
    public String editDiagnosis(Model model, @PathVariable Long id) {
        model.addAttribute("diagnosis",
                modelMapper.map(diagnosisService.findById(id),
                        UpdateDiagnosisDto.class));
        return "/diagnoses/edit";
    }

    @PostMapping("/update/{id}")
    public String editDiagnosis(Model model, @PathVariable long id,
            @ModelAttribute("diagnosis") DiagnoseViewModel diagnosis, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/diagnoses/edit";
        }
        diagnosisService.update(id, modelMapper.map(diagnosis,
                UpdateDiagnosisDto.class));
        return "redirect:/diagnosis";
    }

    @GetMapping("/delete/{id}")
    public String deleteDiagnosis(@PathVariable long id) {
        diagnosisService.delete(id);
        return "redirect:/diagnosis";
    }

    private DiagnoseViewModel convertToDiagnoseViewModel(DiagnosisDto DiagnosisDto) {
        return modelMapper.map(DiagnosisDto, DiagnoseViewModel.class);
    }
}
