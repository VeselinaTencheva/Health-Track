package university.medicalrecordsdemo.controller.view;

import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.dto.treatment.CreateTreatmentDto;
import university.medicalrecordsdemo.dto.treatment.TreatmentDto;
import university.medicalrecordsdemo.dto.treatment.UpdateTreatmentDto;
import university.medicalrecordsdemo.model.binding.treatments.CreateTreatmentViewModel;
import university.medicalrecordsdemo.model.binding.treatments.TreatmentViewModel;
import university.medicalrecordsdemo.model.binding.treatments.UpdateTreatmentViewModel;
import university.medicalrecordsdemo.service.treatment.TreatmentService;
import university.medicalrecordsdemo.util.enums.TreatmentTableColumnsEnum;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
@RequestMapping("/treatments")
public class TreatmentController {

    private TreatmentService treatmentService;
    private ModelMapper modelMapper;

    private static final String DEFAULT_SORT_FIELD = "NAME";

    @GetMapping
    public String getTreatments(Model model,
                                @RequestParam(name = "page", defaultValue = "0") int page,
                                @RequestParam(name = "size", defaultValue = "5") int size,
                                @RequestParam(name = "sortField", defaultValue = DEFAULT_SORT_FIELD) String sortField,
                                @RequestParam(name = "sortDirection", defaultValue = "asc") String sortDirection) {
        Page<TreatmentViewModel> treatmentPage;
        List<Integer> pageNumbers;

        // TreatmentTableColumnsEnum sortFieldEnum = TreatmentTableColumnsEnum.valueOf(sortField.toUpperCase());
        TreatmentTableColumnsEnum sortFieldEnum = TreatmentTableColumnsEnum.valueOf(sortField);

        treatmentPage = treatmentService.findAllByPageAndSort(page, size, sortFieldEnum, sortDirection)
                                        .map(this::convertToTreatmentViewModel);
        
        pageNumbers = IntStream.rangeClosed(0, treatmentPage.getTotalPages() - 1)
                            .boxed()
                            .collect(Collectors.toList());

        model.addAttribute("treatmentPage", treatmentPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("size", size);
        model.addAttribute("treatments", treatmentPage.getContent());
        model.addAttribute("firstPage", 0);
        model.addAttribute("totalPages", treatmentPage.getTotalPages());
        model.addAttribute("pageNumbers", pageNumbers); 
        model.addAttribute("columnsEnum", TreatmentTableColumnsEnum.values());


        return "/treatments/all";
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
