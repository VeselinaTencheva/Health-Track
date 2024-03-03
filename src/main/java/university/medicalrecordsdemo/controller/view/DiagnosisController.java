package university.medicalrecordsdemo.controller.view;

import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.dto.diagnosis.DiagnosisDto;
import university.medicalrecordsdemo.dto.diagnosis.UpdateDiagnosisDto;
import university.medicalrecordsdemo.model.binding.diagnoses.CreateDiagnoseViewModel;
import university.medicalrecordsdemo.model.binding.diagnoses.DiagnoseViewModel;
import university.medicalrecordsdemo.model.binding.diagnoses.UpdateDiagnoseViewModel;
import university.medicalrecordsdemo.model.entity.DepartmentType;
import university.medicalrecordsdemo.service.diagnosis.DiagnosisService;
import university.medicalrecordsdemo.util.enums.DiagnosisTableColumnsEnum;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
@RequestMapping("/diagnosis")
public class DiagnosisController {
    private DiagnosisService diagnosisService;
    private ModelMapper modelMapper;

    private static final String DEFAULT_SORT_FIELD = "NAME";

    @GetMapping
    public String getDiagnosis(Model model,
                                @RequestParam(name = "page", defaultValue = "0") int page,
                                @RequestParam(name = "size", defaultValue = "5") int size,
                                @RequestParam(name = "sortField", defaultValue = DEFAULT_SORT_FIELD) String sortField,
                                @RequestParam(name = "sortDirection", defaultValue = "asc") String sortDirection) {
        Page<DiagnoseViewModel> diagnosisPage;
        List<Integer> pageNumbers;

        DiagnosisTableColumnsEnum sortFieldEnum = DiagnosisTableColumnsEnum.valueOf(sortField);

        diagnosisPage = diagnosisService.findAllByPageAndSort(page, size, sortFieldEnum, sortDirection)
                                        .map(this::convertToDiagnoseViewModel);


        
        pageNumbers = IntStream.rangeClosed(0, diagnosisPage.getTotalPages() - 1)
                            .boxed()
                            .collect(Collectors.toList());

        model.addAttribute("diagnosisPage", diagnosisPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("size", size);
        model.addAttribute("diagnoses", diagnosisPage.getContent());
        model.addAttribute("firstPage", 0);
        model.addAttribute("totalPages", diagnosisPage.getTotalPages());
        model.addAttribute("pageNumbers", pageNumbers); 
        model.addAttribute("columnsEnum", DiagnosisTableColumnsEnum.values());
        model.addAttribute("contentTemplate", "diagnoses/all");

        return "layout";
    }

    @GetMapping("/create")
    public String createDiagnosis(Model model) {
        model.addAttribute("departments", DepartmentType.values());
        model.addAttribute("diagnosis", new CreateDiagnoseViewModel());
        model.addAttribute("contentTemplate", "diagnoses/create");

        return "layout";
    }

    @GetMapping("/{id}")
    public String getDiagnoseById(Model model, @PathVariable Long id) {
        model.addAttribute("diagnose", modelMapper.map(diagnosisService.findById(id),
                DiagnoseViewModel.class));
        model.addAttribute("contentTemplate", "diagnoses/view");

        return "layout";
    }

    @PostMapping("/create")
    public String createDiagnosis(Model model, @Valid @ModelAttribute("diagnosis") CreateDiagnoseViewModel diagnosis,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("departments", DepartmentType.values());
            model.addAttribute("contentTemplate", "diagnoses/create");

            return "layout";
        }
        diagnosisService.create(modelMapper.map(diagnosis, DiagnosisDto.class));
        return "redirect:/diagnosis";
    }

    @GetMapping("/update/{id}")
    public String editDiagnosis(Model model, @PathVariable Long id) {
        model.addAttribute("departments", DepartmentType.values());
        model.addAttribute("diagnosis",
                modelMapper.map(diagnosisService.findById(id),
                        UpdateDiagnoseViewModel.class));
        model.addAttribute("contentTemplate", "diagnoses/edit");

        return "layout";
    }

    @PostMapping("/update/{id}")
    public String editDiagnosis(Model model, @PathVariable long id,
    @Valid @ModelAttribute("diagnosis") UpdateDiagnoseViewModel diagnosis, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("departments", DepartmentType.values());
            model.addAttribute("contentTemplate", "diagnoses/edit");

            return "layout";
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
