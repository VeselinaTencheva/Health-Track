package university.medicalrecordsdemo.controller.view;

import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.dto.patient.PatientDto;
import university.medicalrecordsdemo.dto.patient.UpdatePatientDto;
import university.medicalrecordsdemo.dto.physician.PhysicianDto;
import university.medicalrecordsdemo.model.binding.patients.CreatePatientViewModel;
import university.medicalrecordsdemo.model.binding.patients.PatientViewModel;
import university.medicalrecordsdemo.model.binding.patients.UpdatePatientViewModel;
import university.medicalrecordsdemo.model.entity.SpecialtyType;
import university.medicalrecordsdemo.service.patient.PatientService;
import university.medicalrecordsdemo.service.physician.PhysicianService;
import university.medicalrecordsdemo.util.enums.PatientTableColumnsEnum;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
@RequestMapping("/patients")
public class PatientController {

    private static final String DEFAULT_SORT_FIELD = "FULL_NAME";

    private PatientService patientService;

    private PhysicianService physicianService;

    private final ModelMapper modelMapper;

    @GetMapping
    public String getPatients(Model model,
                                @RequestParam(name = "page", defaultValue = "0") int page,
                                @RequestParam(name = "size", defaultValue = "5") int size,
                                @RequestParam(name = "sortField", defaultValue = DEFAULT_SORT_FIELD) String sortField,
                                @RequestParam(name = "sortDirection", defaultValue = "asc") String sortDirection) {
        Page<PatientViewModel> patientsPage;
        List<Integer> pageNumbers;

        // PatientTableColumnsEnum sortFieldEnum = PatientTableColumnsEnum.valueOf(sortField.toUpperCase());
        PatientTableColumnsEnum sortFieldEnum = PatientTableColumnsEnum.valueOf(sortField);

        if (!sortFieldEnum.equals(PatientTableColumnsEnum.FULL_NAME) && !sortFieldEnum.equals(PatientTableColumnsEnum.GP)) {
            patientsPage = patientService.findAllByPageAndSort(page, size, sortFieldEnum, sortDirection)
                                            .map(this::convertToPatientViewModel);
        } else {
            List<PatientViewModel> patientViewModels = patientService.findAll()
                                                                            .stream()
                                                                            .map(this::convertToPatientViewModel)
                                                                            .collect(Collectors.toList());

            if ("desc".equalsIgnoreCase(sortDirection)) {
                if (PatientTableColumnsEnum.GP.equals(sortFieldEnum)) {
                    patientViewModels.sort(Comparator.comparing(PatientViewModel::getPhysicianFullName).reversed());
                } else {
                    patientViewModels.sort(Comparator.comparing(PatientViewModel::getFullname).reversed());
                }
            } else {
                if (PatientTableColumnsEnum.GP.equals(sortFieldEnum)) {
                    patientViewModels.sort(Comparator.comparing(PatientViewModel::getPhysicianFullName));
                } else {
                    patientViewModels.sort(Comparator.comparing(PatientViewModel::getFullname));
                }
            }

            int start = (int) (page * size);
            int end = Math.min((start + size), patientViewModels.size());
            patientsPage = new PageImpl<>(patientViewModels.subList(start, end), PageRequest.of(page, size), patientViewModels.size());
        }
        
        pageNumbers = IntStream.rangeClosed(0, patientsPage.getTotalPages() - 1)
                            .boxed()
                            .collect(Collectors.toList());

        model.addAttribute("patientsPage", patientsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("size", size);
        model.addAttribute("patients", patientsPage.getContent());
        model.addAttribute("firstPage", 0);
        model.addAttribute("totalPages", patientsPage.getTotalPages());
        model.addAttribute("pageNumbers", pageNumbers); 
        model.addAttribute("columnsEnum", PatientTableColumnsEnum.values());
        model.addAttribute("url", "patients");


        return "patients/patients";
    }

    @GetMapping("/diagnose/{diagnoseId}")
    public String getPatientsByDiagnose(Model model,
                                @PathVariable Long diagnoseId,
                                @RequestParam(name = "page", defaultValue = "0") int page,
                                @RequestParam(name = "size", defaultValue = "5") int size,
                                @RequestParam(name = "sortField", defaultValue = DEFAULT_SORT_FIELD) String sortField,
                                @RequestParam(name = "sortDirection", defaultValue = "asc") String sortDirection) {
        Page<PatientViewModel> patientsPage;
        List<Integer> pageNumbers;

        // PatientTableColumnsEnum sortFieldEnum = PatientTableColumnsEnum.valueOf(sortField.toUpperCase());
        PatientTableColumnsEnum sortFieldEnum = PatientTableColumnsEnum.valueOf(sortField);

        if (!sortFieldEnum.equals(PatientTableColumnsEnum.FULL_NAME) && !sortFieldEnum.equals(PatientTableColumnsEnum.GP)) {
            patientsPage = patientService.findAllByDiagnoseAndPageAndSort(diagnoseId, page, size, sortFieldEnum, sortDirection)
                                            .map(this::convertToPatientViewModel);
        } else {
            List<PatientViewModel> patientViewModels = patientService.findAllByDiagnose(diagnoseId)
                                                                            .stream()
                                                                            .map(this::convertToPatientViewModel)
                                                                            .collect(Collectors.toList());

            if ("desc".equalsIgnoreCase(sortDirection)) {
                if (PatientTableColumnsEnum.GP.equals(sortFieldEnum)) {
                    patientViewModels.sort(Comparator.comparing(PatientViewModel::getPhysicianFullName).reversed());
                } else {
                    patientViewModels.sort(Comparator.comparing(PatientViewModel::getFullname).reversed());
                }
            } else {
                if (PatientTableColumnsEnum.GP.equals(sortFieldEnum)) {
                    patientViewModels.sort(Comparator.comparing(PatientViewModel::getPhysicianFullName));
                } else {
                    patientViewModels.sort(Comparator.comparing(PatientViewModel::getFullname));
                }
            }

            int start = (int) (page * size);
            int end = Math.min((start + size), patientViewModels.size());
            patientsPage = new PageImpl<>(patientViewModels.subList(start, end), PageRequest.of(page, size), patientViewModels.size());
        }
        
        pageNumbers = IntStream.rangeClosed(0, patientsPage.getTotalPages() - 1)
                            .boxed()
                            .collect(Collectors.toList());

        model.addAttribute("patientsPage", patientsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("size", size);
        model.addAttribute("patients", patientsPage.getContent());
        model.addAttribute("firstPage", 0);
        model.addAttribute("totalPages", patientsPage.getTotalPages());
        model.addAttribute("pageNumbers", pageNumbers); 
        model.addAttribute("columnsEnum", PatientTableColumnsEnum.values());
        model.addAttribute("url", "patients/diagnose/" + diagnoseId);


        return "patients/patients";
    }

    @GetMapping("/{id}")
    public String getPatientById(Model model, @PathVariable Long id) {
        PatientViewModel patient = convertToPatientViewModel(patientService.findById(id));
        
        model.addAttribute("patient", patient);

        return "patients/view-patient";
    }

    @GetMapping("/create")
    public String showCreatePatientForm(Model model) {
        model.addAttribute("patient", new CreatePatientViewModel());
        Set<PhysicianDto> gpPhysicians = physicianService.findAllBySpecialty(SpecialtyType.GENERAL_PRACTICE);
        model.addAttribute("physicians", gpPhysicians);
        return "/patients/create";
    }

    @PostMapping("/create")
    public String createPatient(Model model, @Valid @ModelAttribute("patient") CreatePatientViewModel patient,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Set<PhysicianDto> gpPhysicians = physicianService.findAllBySpecialty(SpecialtyType.GENERAL_PRACTICE);
            model.addAttribute("physicians", gpPhysicians);
            return "/patients/create";
        }

        LocalDate birthDate = patient.getBirthDate() == null || patient.getBirthDate().isEmpty() ? null :
        LocalDate.parse(patient.getBirthDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        
        // Map the view model to DTO
        PatientDto createPatientDTO = modelMapper.map(patient, PatientDto.class);
        createPatientDTO.setBirthDate(birthDate); // Set the converted birthDate to the DTO
        patientService.create(createPatientDTO);
        return "redirect:/patients";
    }

    @GetMapping("/edit-patient/{id}")
    public String showEditPatientForm(Model model, @PathVariable Long id) {
        final PatientDto patientDto = patientService.findById(id);
        final UpdatePatientViewModel updatePatientViewModel = modelMapper.map(patientDto,
                UpdatePatientViewModel.class);
        if (patientDto.getGeneralPractitioner() != null) {
            updatePatientViewModel.setPhysicianId(String.valueOf(patientDto.getGeneralPractitioner().getId()));
        }

        model.addAttribute("patient", updatePatientViewModel);
        model.addAttribute("physicians", physicianService.findAllBySpecialty(SpecialtyType.GENERAL_PRACTICE));
        return "/patients/edit-patient";
    }

    @PostMapping("/update/{id}")
    public String updatePatient(Model model, @PathVariable long id,
            @Valid @ModelAttribute("patient") UpdatePatientViewModel patient, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("physicians", physicianService.findAllBySpecialty(SpecialtyType.GENERAL_PRACTICE));
            return "/patients/edit-patient";
        }

        patientService.update(id, modelMapper.map(patient, UpdatePatientDto.class));
        return "redirect:/patients";
    }

    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable long id) {
        patientService.delete(id);
        return "redirect:/patients";
    }

    public PatientViewModel convertToPatientViewModel(PatientDto patientDTO) {
        final PatientViewModel patientViewModel = modelMapper.map(patientDTO, PatientViewModel.class);
        if (patientDTO.getGeneralPractitioner() != null) {
            patientViewModel.setPhysicianId(patientDTO.getGeneralPractitioner().getId());
            patientViewModel.setPhysicianFullName(patientDTO.getGeneralPractitioner().getFirstName() + ' '
                    + patientDTO.getGeneralPractitioner().getLastName());
        }
        return patientViewModel;
    }
}
