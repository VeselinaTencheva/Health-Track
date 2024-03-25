package university.medicalrecordsdemo.controller.view;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.dto.appointment.AppointmentDto;
import university.medicalrecordsdemo.dto.appointment.CreateAppointmentDto;
import university.medicalrecordsdemo.dto.diagnosis.DiagnosisDto;
import university.medicalrecordsdemo.dto.patient.PatientDto;
import university.medicalrecordsdemo.dto.physician.PhysicianDto;
import university.medicalrecordsdemo.dto.sickLeave.CreateSickLeaveDto;
import university.medicalrecordsdemo.dto.sickLeave.SickLeaveDto;
import university.medicalrecordsdemo.dto.sickLeave.UpdateSickLeaveDto;
import university.medicalrecordsdemo.dto.user.UserDto;
import university.medicalrecordsdemo.model.binding.appointments.AppointmentViewModel;
import university.medicalrecordsdemo.model.binding.appointments.CreateAppointmentAndSickLeaveAndTreatmentViewModel;
import university.medicalrecordsdemo.model.binding.appointments.CreateAppointmentViewModel;
import university.medicalrecordsdemo.model.binding.appointments.UpdateAppointmentAndSickLeaveAndTreatmentViewModel;
import university.medicalrecordsdemo.model.binding.patients.PatientViewModel;
import university.medicalrecordsdemo.model.binding.physicians.PhysiciansViewModel;
import university.medicalrecordsdemo.model.binding.sickLeaves.CreateSickLeaveViewModel;
import university.medicalrecordsdemo.model.binding.sickLeaves.SickLeaveViewModel;
import university.medicalrecordsdemo.model.binding.sickLeaves.UpdateSickLeaveViewModel;
import university.medicalrecordsdemo.model.entity.DepartmentType;
import university.medicalrecordsdemo.model.entity.SpecialtyType;
import university.medicalrecordsdemo.service.appointment.AppointmentService;
import university.medicalrecordsdemo.service.diagnosis.DiagnosisService;
import university.medicalrecordsdemo.service.patient.PatientService;
import university.medicalrecordsdemo.service.physician.PhysicianService;
import university.medicalrecordsdemo.service.sickLeave.SickLeaveService;
import university.medicalrecordsdemo.service.user.UserService;
import university.medicalrecordsdemo.util.enums.AppointmentTableColumnsEnum;

@Controller
@AllArgsConstructor
@RequestMapping("/appointments")
public class AppointmentController {
    private AppointmentService appointmentService;

    private PhysicianService physicianService;
    private UserService userService;
    private PatientService patientService;
    private PatientController patientController;
    private PhysicianController physicianController;

    private DiagnosisService diagnosisService;
    private SickLeaveService sickLeaveService;
    private ModelMapper modelMapper;

    private static final String DEFAULT_SORT_FIELD = "DATE";

    @GetMapping
    public String getAppointments(Model model,
                                @RequestParam(name = "page", defaultValue = "0") int page,
                                @RequestParam(name = "size", defaultValue = "5") int size,
                                @RequestParam(name = "sortField", defaultValue = DEFAULT_SORT_FIELD) String sortField,
                                @RequestParam(name = "sortDirection", defaultValue = "asc") String sortDirection) {
        Page<AppointmentViewModel> appointmentsPage;
        List<Integer> pageNumbers;

        List<AppointmentViewModel> appointmentsViewModels = appointmentService.findAll()
                            .stream()
                            .map(this::convertToAppointmentViewModel)
                            .collect(Collectors.toList());
                            
        AppointmentTableColumnsEnum sortFieldEnum = AppointmentTableColumnsEnum.valueOf(sortField);
    
        if ("desc".equalsIgnoreCase(sortDirection)) {
            if (AppointmentTableColumnsEnum.DATE.equals(sortFieldEnum)) {
                appointmentsViewModels.sort(Comparator.comparing(AppointmentViewModel::getDate).reversed());
            } else if (AppointmentTableColumnsEnum.PATIENT.equals(sortFieldEnum)) {
                appointmentsViewModels.sort(Comparator.comparing(AppointmentViewModel::getPatientInfo).reversed());
            } else if (AppointmentTableColumnsEnum.PHYSICIAN.equals(sortFieldEnum)) {
                appointmentsViewModels.sort(Comparator.comparing(AppointmentViewModel::getPhysicianInfo).reversed());
            } else if (AppointmentTableColumnsEnum.DIAGNOSIS.equals(sortFieldEnum)) {
                appointmentsViewModels.sort(Comparator.comparing(AppointmentViewModel::getDiagnosisInfo).reversed());
            }
        } else {
            if (AppointmentTableColumnsEnum.DATE.equals(sortFieldEnum)) {
                appointmentsViewModels.sort(Comparator.comparing(AppointmentViewModel::getDate));
            } else if (AppointmentTableColumnsEnum.PATIENT.equals(sortFieldEnum)) {
                appointmentsViewModels.sort(Comparator.comparing(AppointmentViewModel::getPatientInfo));
            } else if (AppointmentTableColumnsEnum.PHYSICIAN.equals(sortFieldEnum)) {
                appointmentsViewModels.sort(Comparator.comparing(AppointmentViewModel::getPhysicianInfo));
            } else if (AppointmentTableColumnsEnum.DIAGNOSIS.equals(sortFieldEnum)) {
                appointmentsViewModels.sort(Comparator.comparing(AppointmentViewModel::getDiagnosisInfo));
            }
        }

        int start = page * size; // Calculate the start index
        int end = Math.min(start + size, appointmentsViewModels.size()); // Calculate the end index
        List<AppointmentViewModel> subset = appointmentsViewModels.subList(start, end); // Get the sublist

        appointmentsPage = new PageImpl<>(subset, PageRequest.of(page, size), appointmentsViewModels.size());

        pageNumbers = IntStream.rangeClosed(0, appointmentsPage.getTotalPages() - 1)
                            .boxed()
                            .collect(Collectors.toList());

        model.addAttribute("appointmentsPage", appointmentsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("size", size);
        model.addAttribute("appointments", appointmentsPage.getContent());
        model.addAttribute("firstPage", 0);
        model.addAttribute("totalPages", appointmentsPage.getTotalPages());
        model.addAttribute("pageNumbers", pageNumbers); 
        model.addAttribute("columnsEnum", AppointmentTableColumnsEnum.values());
        model.addAttribute("contentTemplate", "appointments/all");


        return "layout";
    }

    @GetMapping("/create")
    public String createAppointment(Model model) {
        Set<DiagnosisDto> diagnoses = this.fetchSpecialtiesByLoggedUser();
        model.addAttribute("diagnoses", diagnoses);
        CreateAppointmentAndSickLeaveAndTreatmentViewModel viewModel = new CreateAppointmentAndSickLeaveAndTreatmentViewModel();
    
        // Set default values
        viewModel.setDate(LocalDate.now());
        viewModel.setSickLeaveStartDate(LocalDate.now());

        final UserDto user = fetchLoggedUser();
        if (user != null ) {
            try {
                PhysicianDto physician = physicianService.findById(user.getId());
                viewModel.setPhysicianId(physician.getId());
                model.addAttribute("showSelectPhysician", false);

            } catch (IllegalArgumentException e) {
                Set<PhysicianDto> physicians = physicianService.findAll();
                model.addAttribute("physicians", physicians);
                model.addAttribute("showSelectPhysician", true);
            }
        }


        model.addAttribute("appointment", viewModel);

        Set<PatientDto> patients = patientService.findAll();

        model.addAttribute("patients", patients);
        model.addAttribute("contentTemplate", "appointments/create");

        return "layout";
    }

    @PostMapping("/create")
    public String createAppointment(Model model,
            @Valid @ModelAttribute("appointment") CreateAppointmentAndSickLeaveAndTreatmentViewModel appointment,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("patients", patientService.findAll());
            model.addAttribute("diagnosis", diagnosisService.findAll());
            model.addAttribute("contentTemplate", "appointments/create");
            final UserDto user = fetchLoggedUser();
            if (user != null ) {
                try {
                    physicianService.findById(user.getId());
                    model.addAttribute("showSelectPhysician", false);
                } catch (IllegalArgumentException e) {
                    Set<PhysicianDto> physicians = physicianService.findAll();
                    model.addAttribute("showSelectPhysician", true);
                    model.addAttribute("physicians", physicians);
                }
            }

            Set<DiagnosisDto> diagnoses = this.fetchSpecialtiesByLoggedUser();
            model.addAttribute("diagnoses", diagnoses);
            return "layout";
        }

        SickLeaveDto appointmentSickLeaveDto = null;
        if (appointment.getSickLeaveDuration() > 0) {
            CreateSickLeaveViewModel createSickLeaveViewModel = modelMapper.map(appointment,
                    CreateSickLeaveViewModel.class);
            CreateSickLeaveDto createSickLeaveDTO = modelMapper.map(createSickLeaveViewModel, CreateSickLeaveDto.class);
            appointmentSickLeaveDto = sickLeaveService.create(createSickLeaveDTO);
        }

        final DiagnosisDto diagnosis = diagnosisService.findById(appointment.getDiagnosisId());
        final PatientDto patient = patientService.findById(appointment.getPatientId());
        final PhysicianDto physician = physicianService.findById(appointment.getPhysicianId());
        CreateAppointmentViewModel createAppointmentViewModel = new CreateAppointmentViewModel(
                appointment.getDate(),
                patient,
                physician,
                appointmentSickLeaveDto,
                diagnosis,
                appointment.getTreatment());

        // CreateAppointmentViewModel createAppointmentViewModel = new CreateAppointmentViewModel();

        CreateAppointmentDto appointmentDTO = modelMapper.map(createAppointmentViewModel, CreateAppointmentDto.class);
        appointmentService.create(appointmentDTO);
        return "redirect:/appointments";
    }

    @GetMapping("/{id}")
    public String getAppointment(Model model, @PathVariable Long id) {
        final AppointmentDto appointment = appointmentService.findById(id);
        final AppointmentViewModel appointmentViewModel = modelMapper.map(appointment, AppointmentViewModel.class);
        model.addAttribute("appointment", appointmentViewModel);
        model.addAttribute("contentTemplate", "appointments/view");
        return "layout";
    }


    @GetMapping("/update/{id}")
    public String editAppointment(Model model, @PathVariable Long id) {
        model.addAttribute("diagnosis", this.fetchSpecialtiesByLoggedUser());
        model.addAttribute("patients", patientService.findAll());
        final AppointmentDto appointment = appointmentService.findById(id);
        final UpdateAppointmentAndSickLeaveAndTreatmentViewModel updateAppointment = new UpdateAppointmentAndSickLeaveAndTreatmentViewModel();
        updateAppointment.setDate(appointment.getDate());
        if (appointment.getDiagnosis() != null)  {
            updateAppointment.setDiagnosis(appointment.getDiagnosis().getId());
        }
        updateAppointment.setPatientId(appointment.getPatient().getId());
        updateAppointment.setPhysicianId(appointment.getPhysician().getId());
        if (appointment.getSickLeave() != null) {
            updateAppointment.setSickLeaveStartDate(appointment.getSickLeave().getStartDate());
            updateAppointment.setSickLeaveDuration(appointment.getSickLeave().getDuration());
        }

        final Set<PhysicianDto> physicians = physicianService.findAll();
        model.addAttribute("physicians", physicians);

        final Set<PatientDto> patients = patientService.findAll();
        model.addAttribute("patients", patients);

        model.addAttribute("appointment", updateAppointment);
        model.addAttribute("contentTemplate", "appointments/edit");
        return "layout";
    }

    @PostMapping("/update/{id}")
    public String editAppointment(Model model, @PathVariable long id,
        @Valid @ModelAttribute("appointment")
        UpdateAppointmentAndSickLeaveAndTreatmentViewModel appointment,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("physicians", physicianService.findAll());
            model.addAttribute("patients", patientService.findAll());
            model.addAttribute("diagnosis", diagnosisService.findAll());
            model.addAttribute("contentTemplate", "appointments/edit");

            Set<DiagnosisDto> diagnoses = this.fetchSpecialtiesByLoggedUser();
            model.addAttribute("diagnoses", diagnoses);
            return "layout";
        }


        AppointmentDto currentAppointment = appointmentService.findById(id);

        // Handle sick leave logic
        SickLeaveDto updatedSickLeave = null;
        if (appointment.getSickLeaveDuration() > 0) {
            if (currentAppointment.getSickLeave() == null) {
                // Scenario 1: Adding a new sick leave
                CreateSickLeaveViewModel createSickLeaveViewModel = modelMapper.map(appointment, CreateSickLeaveViewModel.class);
                CreateSickLeaveDto createSickLeaveDTO = modelMapper.map(createSickLeaveViewModel, CreateSickLeaveDto.class);
                updatedSickLeave = sickLeaveService.create(createSickLeaveDTO);
            } else {
                // Scenario 3: Updating existing sick leave
                UpdateSickLeaveViewModel updateSickLeaveViewModel = modelMapper.map(appointment, UpdateSickLeaveViewModel.class);
                UpdateSickLeaveDto updateSickLeaveDTO = modelMapper.map(updateSickLeaveViewModel, UpdateSickLeaveDto.class);
                updatedSickLeave = sickLeaveService.update(currentAppointment.getSickLeave().getId(), updateSickLeaveDTO);
            }
        } else if (currentAppointment.getSickLeave() != null) {
            // Scenario 2: Removing existing sick leave
            sickLeaveService.delete(currentAppointment.getSickLeave().getId());
        }
        return "redirect:/appointments";
    
    // AppointmentViewModel currentAppointment = this.modelMapper
    // .map(this.appointmentService.findById(appointment.getId()),
    // AppointmentViewModel.class);
    // SickLeaveDTO prevAppointmentSickLeave =
    // modelMapper.map(currentAppointment.getSickLeave(), SickLeaveDTO.class);
    // TreatmentDTO prevAppointmentTreatment =
    // modelMapper.map(currentAppointment.getTreatment(), TreatmentDTO.class);
    
    // // Set Sick Leave Object
    // if (prevAppointmentSickLeave == null && appointment.getSickLeaveDuration() >
    // 0) {
    // // create a new sick leave object if there is no sick leave added to the
    // // appointment before edit
    // CreateSickLeaveViewModel createSickLeaveViewModel =
    // modelMapper.map(appointment,
    // CreateSickLeaveViewModel.class);
    // CreateSickLeaveDTO createSickLeaveDTO =
    // modelMapper.map(createSickLeaveViewModel, CreateSickLeaveDTO.class);
    // prevAppointmentSickLeave = sickLeaveService.create(createSickLeaveDTO);
    // } else if (prevAppointmentSickLeave != null &&
    // (appointment.getSickLeaveDuration() > 0)) {
    // // if prevAppointmentSickLeave had a value, but it had been deleted on edit
    // this.sickLeaveService.delete(prevAppointmentSickLeave.getId());
    // prevAppointmentSickLeave = null;
    // } else if (prevAppointmentSickLeave != null &&
    // !(appointment.getSickLeaveDuration() > 0)) {
    // if
    // (!prevAppointmentSickLeave.getStartDate().equals(appointment.getSickLeaveStartDate())
    // || prevAppointmentSickLeave.getDuration() !=
    // appointment.getSickLeaveDuration()) {
    // UpdateSickLeaveViewModel updateSickLeaveViewModel =
    // modelMapper.map(appointment,
    // UpdateSickLeaveViewModel.class);
    // UpdateSickLeaveDTO updateSickLeaveDTO =
    // modelMapper.map(updateSickLeaveViewModel,
    // UpdateSickLeaveDTO.class);
    // prevAppointmentSickLeave =
    // sickLeaveService.update(prevAppointmentSickLeave.getId(),
    // updateSickLeaveDTO);
    // }
    // }
    
    // // Set Treatment Object
    // if (prevAppointmentTreatment == null && appointment.getTreatmentName() !=
    // null
    // && !appointment.getTreatmentName().isEmpty()) {
    // // create a new Treatment object if there is no Treatment added to the
    // // appointment before edit
    // CreateTreatmentViewModel createTreatmentViewModel = new
    // CreateTreatmentViewModel(
    // appointment.getTreatmentName(), appointment.getTreatmentDescription());
    // CreateTreatmentDTO createTreatmentDTO =
    // modelMapper.map(createTreatmentViewModel, CreateTreatmentDTO.class);
    // prevAppointmentTreatment = treatmentService.create(createTreatmentDTO);
    // } else if (prevAppointmentTreatment != null &&
    // appointment.getTreatmentName().isEmpty()) {
    // // if prevAppointmentTreatment had a value, but it had been deleted on edit
    // this.treatmentService.delete(prevAppointmentTreatment.getId());
    // prevAppointmentTreatment = null;
    // } else if (prevAppointmentTreatment != null &&
    // !appointment.getTreatmentName().isEmpty()) {
    // if
    // (!prevAppointmentTreatment.getName().equals(appointment.getTreatmentName())
    // ||
    // prevAppointmentTreatment.getDescription().equals(appointment.getTreatmentDescription()))
    // {
    // UpdateTreatmentViewModel updateTreatmentViewModel = new
    // UpdateTreatmentViewModel(
    // appointment.getTreatmentName(), appointment.getTreatmentDescription());
    // UpdateTreatmentDTO updateTreatmentDTO =
    // modelMapper.map(updateTreatmentViewModel,
    // UpdateTreatmentDTO.class);
    // prevAppointmentTreatment =
    // treatmentService.update(prevAppointmentTreatment.getId(),
    // updateTreatmentDTO);
    // }
    }

    private UserDto fetchLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                UserDto user = userService.findUserByUserName(username);
                return user;
            }

            return null;
        }
        return null;
    }
    

    private Set<DiagnosisDto> fetchSpecialtiesByLoggedUser() {
        // Fetch autheticated user to get the physician's specialties which correspond to the departments and then get diagnoses for those departments
        final UserDto user = fetchLoggedUser();
        if (user != null ) {
            try {
                PhysicianDto physician = physicianService.findById(user.getId());
                Set<SpecialtyType> specialties = physician.getSpecialties();
                Set<DepartmentType> departments = specialties.stream()
                    .map(SpecialtyType::getDepartment)
                    .collect(Collectors.toSet());
            
                return diagnosisService.findAllByCategory(departments);
            } catch (IllegalArgumentException e) {
                return diagnosisService.findAll();
            }
        }

        return null;
    }

    private AppointmentViewModel convertToAppointmentViewModel(AppointmentDto appointmentDTO) {
        // Map AppointmentDTO to AppointmentViewModel
        AppointmentViewModel appointmentViewModel = modelMapper.map(appointmentDTO, AppointmentViewModel.class);
        // Map AppointmentEntity to AppointmentDTO

        PatientViewModel patient = patientController.convertToPatientViewModel(appointmentDTO.getPatient());
        appointmentViewModel.setPatient(patient);

        // Similarly, map physicianEntity to PhysicianDTO
        PhysiciansViewModel physician = physicianController.convertToPhysiciansViewModel(appointmentDTO.getPhysician());
        appointmentViewModel.setPhysician(physician);

        if (appointmentDTO.getSickLeave() != null) {
            SickLeaveViewModel sickLeaveDTO = modelMapper.map(appointmentDTO.getSickLeave(), SickLeaveViewModel.class);
            appointmentViewModel.setSickLeave(sickLeaveDTO);
        }

        appointmentViewModel.setDate(appointmentDTO.getDate().toString());

        return appointmentViewModel;
    }

    @GetMapping("/delete/{id}")
        public String deleteAppointment(@PathVariable long id) {
        appointmentService.delete(id);
        return "redirect:/appointments";
    }
}

