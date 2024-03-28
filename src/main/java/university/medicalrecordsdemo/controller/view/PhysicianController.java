package university.medicalrecordsdemo.controller.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.modelmapper.ModelMapper;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.dto.physician.PhysicianDto;
import university.medicalrecordsdemo.model.binding.physicians.CreatePhysicianViewModel;
import university.medicalrecordsdemo.model.binding.physicians.PhysiciansViewModel;
import university.medicalrecordsdemo.model.binding.physicians.UpdatePhysicianViewModel;
import university.medicalrecordsdemo.model.entity.DepartmentType;
import university.medicalrecordsdemo.model.entity.SpecialtyType;
import university.medicalrecordsdemo.service.physician.PhysicianService;
import university.medicalrecordsdemo.util.enums.PhysicianTableColumnsEnum;

@Controller
@AllArgsConstructor
@RequestMapping("/physicians")
public class PhysicianController {

    private static final String DEFAULT_SORT_FIELD = "FULL_NAME";

    private PhysicianService physicianService;

    private ModelMapper modelMapper;

    @GetMapping
    public String getPhysicians(Model model,
                                @RequestParam(name = "page", defaultValue = "0") int page,
                                @RequestParam(name = "size", defaultValue = "5") int size,
                                @RequestParam(name = "sortField", defaultValue = DEFAULT_SORT_FIELD) String sortField,
                                @RequestParam(name = "sortDirection", defaultValue = "asc") String sortDirection) {
        Page<PhysiciansViewModel> physiciansPage;
        List<Integer> pageNumbers;

        // PhysicianTableColumnsEnum sortFieldEnum = PhysicianTableColumnsEnum.valueOf(sortField.toUpperCase());
        PhysicianTableColumnsEnum sortFieldEnum = PhysicianTableColumnsEnum.valueOf(sortField);

        if (!sortFieldEnum.equals(PhysicianTableColumnsEnum.IS_GP) && !sortFieldEnum.equals(PhysicianTableColumnsEnum.FULL_NAME) &&  !sortFieldEnum.equals(PhysicianTableColumnsEnum.PATIENTS_COUNT)) {
            physiciansPage = physicianService.findAllByPageAndSort(page, size, sortFieldEnum, sortDirection)
                                            .map(this::convertToPhysiciansViewModel);
        } else {
            List<PhysiciansViewModel> physiciansViewModels = physicianService.findAll()
                                                                            .stream()
                                                                            .map(this::convertToPhysiciansViewModel)
                                                                            .collect(Collectors.toList());

            if ("desc".equalsIgnoreCase(sortDirection)) {
                if (PhysicianTableColumnsEnum.IS_GP.equals(sortFieldEnum)) {
                    physiciansViewModels.sort(Comparator.comparing(PhysiciansViewModel::isGP).reversed());
                } else if (PhysicianTableColumnsEnum.PATIENTS_COUNT.equals(sortFieldEnum)) {
                    physiciansViewModels.sort(Comparator.comparingInt(PhysiciansViewModel::getPatientsCount).reversed());
                } else {
                    physiciansViewModels.sort(Comparator.comparing(PhysiciansViewModel::getFullname).reversed());
                }
            } else {
                if (PhysicianTableColumnsEnum.IS_GP.equals(sortFieldEnum)) {
                    physiciansViewModels.sort(Comparator.comparing(PhysiciansViewModel::isGP));
                } else if (PhysicianTableColumnsEnum.PATIENTS_COUNT.equals(sortFieldEnum)) {
                    physiciansViewModels.sort(Comparator.comparingInt(PhysiciansViewModel::getPatientsCount));
                } else {
                    physiciansViewModels.sort(Comparator.comparing(PhysiciansViewModel::getFullname));
                }
            }

            int start = (int) (page * size);
            int end = Math.min((start + size), physiciansViewModels.size());
            physiciansPage = new PageImpl<>(physiciansViewModels.subList(start, end), PageRequest.of(page, size), physiciansViewModels.size());
        }
        
        pageNumbers = IntStream.rangeClosed(0, physiciansPage.getTotalPages() - 1)
                            .boxed()
                            .collect(Collectors.toList());

        model.addAttribute("physiciansPage", physiciansPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("size", size);
        model.addAttribute("physicians", physiciansPage.getContent());
        model.addAttribute("firstPage", 0);
        model.addAttribute("totalPages", physiciansPage.getTotalPages());
        model.addAttribute("pageNumbers", pageNumbers); 
        model.addAttribute("columnsEnum", PhysicianTableColumnsEnum.values());
        model.addAttribute("contentTemplate", "physicians/all");

        return "layout";
    }
    

    @GetMapping("/{id}")
    public String getPhysicianById(Model model, @PathVariable Long id) {
        PhysiciansViewModel physician = convertToPhysiciansViewModel(physicianService.findById(id));
        model.addAttribute("physician", physician);
        model.addAttribute("departments", DepartmentType.values());
        model.addAttribute("contentTemplate", "physicians/view");

        return "layout";
    }

    @GetMapping("/create")
    public String showCreatePhysicianForm(Model model) {
        model.addAttribute("specialities", SpecialtyType.values());
        model.addAttribute("physician", new CreatePhysicianViewModel());
        model.addAttribute("contentTemplate", "physicians/create");

        return "layout";
    }

    @PostMapping("/create")
    public String createPhysician(Model model, @Valid @ModelAttribute("physician") CreatePhysicianViewModel physician,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("specialities", SpecialtyType.values());
            model.addAttribute("contentTemplate", "physicians/create");
            return "layout";
        }

        try {
            LocalDate birthDate = physician.getBirthDate() == null || physician.getBirthDate().isEmpty() ? null :
                LocalDate.parse(physician.getBirthDate(), DateTimeFormatter.ISO_LOCAL_DATE);
            
            PhysicianDto createPhysicianDTO = modelMapper.map(physician, PhysicianDto.class);
            createPhysicianDTO.setBirthDate(birthDate); // Set the converted birthDate to the DTO
            physicianService.create(createPhysicianDTO);
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("specialities", SpecialtyType.values());
            model.addAttribute("error", "A physician with the same username or ssn or medical license number already exists.");
            model.addAttribute("physician", physician); // To repopulate the form with submitted values
            model.addAttribute("contentTemplate", "physicians/create");
            return "layout";
        }

        return "redirect:/physicians";
    }

    @GetMapping("/update/{id}")
    public String showEditPhysicianForm(Model model, @PathVariable Long id) {
        model.addAttribute("physician", modelMapper.map(physicianService.findById(id), UpdatePhysicianViewModel.class));
        model.addAttribute("specialities", SpecialtyType.values());
        model.addAttribute("contentTemplate", "physicians/edit");

        return "layout";
    }

    @PostMapping("/update/{id}")
    public String updatePhysicians(Model model, @PathVariable long id,
            @Valid @ModelAttribute("physician") UpdatePhysicianViewModel physician,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("specialities", SpecialtyType.values());
            model.addAttribute("contentTemplate", "physicians/edit");

        return "layout";
        }
        LocalDate birthDate = physician.getBirthDate() == null || physician.getBirthDate().isEmpty() ? null :
        LocalDate.parse(physician.getBirthDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        
        // Map the view model to DTO
        PhysicianDto updatePhysicianDTO = modelMapper.map(physician,
                PhysicianDto.class);
        updatePhysicianDTO.setBirthDate(birthDate); // Set the converted birthDate to the DTO
        physicianService.update(id, updatePhysicianDTO);
        return "redirect:/physicians";
    }

    @GetMapping("/delete/{id}")
    public String deletePhysician(@PathVariable long id) {
        physicianService.delete(id);
        return "redirect:/physicians";
    }

    public PhysiciansViewModel convertToPhysiciansViewModel(PhysicianDto physicianDTO) {
        final PhysiciansViewModel physiciansViewModel = modelMapper.map(physicianDTO, PhysiciansViewModel.class);
        physiciansViewModel.setGP(physicianDTO.getSpecialties().contains(SpecialtyType.GENERAL_PRACTICE));
        physiciansViewModel.setPatientsCount(physicianDTO.getPatients().size());

        return physiciansViewModel;
    }
}
