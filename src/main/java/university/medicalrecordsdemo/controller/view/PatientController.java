package university.medicalrecordsdemo.controller.view;

import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.dto.patient.PatientDto;
import university.medicalrecordsdemo.dto.patient.UpdatePatientDto;
import university.medicalrecordsdemo.model.binding.patients.CreatePatientViewModel;
import university.medicalrecordsdemo.model.binding.patients.PatientViewModel;
import university.medicalrecordsdemo.model.binding.patients.UpdatePatientViewModel;
import university.medicalrecordsdemo.model.entity.RoleType;
import university.medicalrecordsdemo.service.patient.PatientService;
import university.medicalrecordsdemo.service.physician.PhysicianService;
import university.medicalrecordsdemo.service.user.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
// import org.springframework.validation.BindingResult;
// import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

// import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/patients")
public class PatientController {

    private PatientService patientService;

    private PhysicianService physicianService;

    private UserService userService;

    private final ModelMapper modelMapper;

    @GetMapping
    public String getPatients(Model model) {
        final List<PatientViewModel> patients = patientService.findAll()
                .stream()
                .map(this::convertToPatientViewModel)
                .collect(Collectors.toList());
        model.addAttribute("patients", patients);

        return "patients/patients";
    }

    @GetMapping("/{id}")
    public String getPatientById(Model model, @PathVariable Long id) {
        PatientViewModel patient = convertToPatientViewModel(patientService.findById(id));
        model.addAttribute("patient", patient);

        return "patients/view-patient";
    }

    // @GetMapping("/{id}/diagnoses")
    // public String getPatientsDiagnoses(Model model, @PathVariable Long id) {
    // final List<DiagnoseViewModel> diagnoses =
    // patientService.findAllDiagnosesPerPatient(id).stream()
    // .map((el) -> this.modelMapper.map(el, DiagnoseViewModel.class))
    // .collect(Collectors.toList());
    // model.addAttribute("diagnoses", diagnoses);
    // return "/diagnoses/all";
    // }

    @GetMapping("/create")
    public String showCreatePatientForm(Model model) {
        model.addAttribute("patient", new CreatePatientViewModel());
        model.addAttribute("physicians", userService.findAllByRoleType(RoleType.ROLE_GENERAL_PRACTITIONER));
        return "/patients/create";
    }

    @PostMapping("/create")
    public String createPatient(Model model, @ModelAttribute("patient") CreatePatientViewModel patient,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("physicians", physicianService.findAll());
            return "/patients/create";
        }
        PatientDto createPatientDTO = modelMapper.map(patient,
                PatientDto.class);
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
        model.addAttribute("physicians", userService.findAllByRoleType(RoleType.ROLE_GENERAL_PRACTITIONER));
        return "/patients/edit-patient";
    }

    @PostMapping("/update/{id}")
    public String updatePatient(Model model, @PathVariable long id,
            @ModelAttribute("patient") UpdatePatientViewModel patient, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("physicians", userService.findAllByRoleType(RoleType.ROLE_GENERAL_PRACTITIONER));
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

    private PatientViewModel convertToPatientViewModel(PatientDto patientDTO) {
        final PatientViewModel patientViewModel = modelMapper.map(patientDTO, PatientViewModel.class);
        if (patientDTO.getGeneralPractitioner() != null) {
            patientViewModel.setPhysicianId(patientDTO.getGeneralPractitioner().getId());
            patientViewModel.setPhysicianFullName(patientDTO.getGeneralPractitioner().getFirstName() + ' '
                    + patientDTO.getGeneralPractitioner().getLastName());
        }
        return patientViewModel;
    }
}
