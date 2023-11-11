// package com.example.Medical.Records.v10.data.view.controller;

// import com.example.Medical.Records.v10.data.entity.Patient;
// import
// com.example.Medical.Records.v10.data.view.model.diagnoses.DiagnoseViewModel;
// import
// com.example.Medical.Records.v10.data.view.model.patients.CreatePatientViewModel;
// import
// com.example.Medical.Records.v10.data.view.model.patients.PatientViewModel;
// import
// com.example.Medical.Records.v10.data.view.model.patients.UpdatePatientViewModel;
// import com.example.Medical.Records.v10.dto.patient.CreatePatientDTO;
// import com.example.Medical.Records.v10.dto.patient.PatientDTO;
// import com.example.Medical.Records.v10.dto.patient.UpdatePatientDTO;
// import
// com.example.Medical.Records.v10.service.generalPractitioner.GeneralPractitionerService;
// import com.example.Medical.Records.v10.service.patient.PatientService;
// import lombok.AllArgsConstructor;
// import org.modelmapper.ModelMapper;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.validation.BindingResult;
// import org.springframework.web.bind.annotation.*;

// import javax.validation.Valid;
// import java.util.List;
// import java.util.stream.Collectors;

// @Controller
// @AllArgsConstructor
// @RequestMapping("/patients")
// public class PatientController {

// private PatientService patientService;

// private GeneralPractitionerService generalPractitionerService;

// private final ModelMapper modelMapper;

// @GetMapping
// public String getPatients(Model model) {
// final List<PatientViewModel> patients = patientService.findAll()
// .stream()
// .map(this::convertToPatientViewModel)
// .collect(Collectors.toList());
// model.addAttribute("patients", patients);

// return "patients/patients";
// }

// @GetMapping("/{id}")
// public String getPatientById(Model model, @PathVariable Long id) {
// PatientViewModel patient =
// convertToPatientViewModel(patientService.findById(id));
// model.addAttribute("patient", patient);

// return "patients/view-patient";
// }

// @GetMapping("/{id}/diagnoses")
// public String getPatientsDiagnoses(Model model, @PathVariable Long id) {
// final List<DiagnoseViewModel> diagnoses =
// patientService.findAllDiagnosesPerPatient(id).stream()
// .map((el) -> this.modelMapper.map(el, DiagnoseViewModel.class))
// .collect(Collectors.toList());
// model.addAttribute("diagnoses", diagnoses);
// return "/diagnoses/all";
// }

// @GetMapping("/create")
// public String showCreatePatientForm(Model model) {
// model.addAttribute("patient", new CreatePatientViewModel());
// model.addAttribute("physicians", generalPractitionerService.findAll());
// return "/patients/create";
// }

// @PostMapping("/create")
// public String createPatient(Model model, @Valid @ModelAttribute("patient")
// CreatePatientViewModel patient, BindingResult bindingResult) {
// if (bindingResult.hasErrors()) {
// model.addAttribute("physicians", generalPractitionerService.findAll());
// return "/patients/create";
// }
// CreatePatientDTO createPatientDTO = modelMapper.map(patient,
// CreatePatientDTO.class);
// patientService.create(createPatientDTO);
// return "redirect:/patients";
// }

// @GetMapping("/edit-patient/{id}")
// public String showEditPatientForm(Model model, @PathVariable Long id) {
// model.addAttribute("patient", modelMapper.map(patientService.findById(id),
// UpdatePatientViewModel.class));
// model.addAttribute("physicians", generalPractitionerService.findAll());
// return "/patients/edit-patient";
// }

// @PostMapping("/update/{id}")
// public String updatePatient(Model model, @PathVariable long id, @Valid
// @ModelAttribute("patient") UpdatePatientViewModel patient, BindingResult
// bindingResult) {
// if (bindingResult.hasErrors()) {
// model.addAttribute("physicians", generalPractitionerService.findAll());
// return "/patients/edit-patient";
// }
// patientService.update(id, modelMapper.map(patient, UpdatePatientDTO.class));
// return "redirect:/patients";
// }

// @GetMapping("/delete/{id}")
// public String deletePatient(@PathVariable long id) {
// patientService.delete(id);
// return "redirect:/patients";
// }

// private PatientViewModel convertToPatientViewModel(PatientDTO patientDTO) {
// return modelMapper.map(patientDTO, PatientViewModel.class);
// }
// }
