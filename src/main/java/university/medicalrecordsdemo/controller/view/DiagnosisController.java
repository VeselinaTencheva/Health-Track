// package com.example.Medical.Records.v10.data.view.controller;

// import
// com.example.Medical.Records.v10.data.view.model.diagnoses.CreateDiagnoseViewModel;
// import
// com.example.Medical.Records.v10.data.view.model.diagnoses.DiagnoseViewModel;
// import
// com.example.Medical.Records.v10.data.view.model.diagnoses.UpdateDiagnoseViewModel;
// import com.example.Medical.Records.v10.dto.diagnoses.CreateDiagnoseDTO;
// import com.example.Medical.Records.v10.dto.diagnoses.DiagnoseDTO;
// import com.example.Medical.Records.v10.dto.diagnoses.UpdateDiagnoseDTO;
// import com.example.Medical.Records.v10.service.diagnosis.DiagnosisService;
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
// @RequestMapping("/diagnosis")
// public class DiagnosisController {
// private DiagnosisService diagnosisService;
// private ModelMapper modelMapper;

// @GetMapping
// public String getDiagnosis(Model model) {
// final List<DiagnoseViewModel> diagnoses = diagnosisService.findAll()
// .stream()
// .map(this::convertToDiagnoseViewModel)
// .collect(Collectors.toList());
// model.addAttribute("diagnoses", diagnoses);
// return "diagnoses/all";
// }

// @GetMapping("/create")
// public String createDiagnosis(Model model) {
// model.addAttribute("diagnosis", new CreateDiagnoseViewModel());
// return "/diagnoses/create";
// }

// @GetMapping("/{id}")
// public String getDiagnoseById(Model model, @PathVariable Long id) {
// model.addAttribute("diagnose", modelMapper.map(diagnosisService.findById(id),
// UpdateDiagnoseViewModel.class));
// return "diagnoses/view";
// }

// @PostMapping("/create")
// public String createDiagnosis(@Valid @ModelAttribute("diagnosis")
// CreateDiagnoseViewModel diagnosis, BindingResult bindingResult) {
// if (bindingResult.hasErrors()) {
// return "/diagnoses/create";
// }
// diagnosisService.create(modelMapper.map(diagnosis, CreateDiagnoseDTO.class));
// return "redirect:/diagnosis";
// }

// @GetMapping("/update/{id}")
// public String editDiagnosis(Model model, @PathVariable Long id) {
// model.addAttribute("diagnosis",
// modelMapper.map(diagnosisService.findById(id),
// UpdateDiagnoseViewModel.class));
// return "/diagnoses/edit";
// }

// @PostMapping("/update/{id}")
// public String editDiagnosis(Model model, @PathVariable long id,@Valid
// @ModelAttribute("diagnosis") UpdateDiagnoseViewModel diagnosis, BindingResult
// bindingResult) {
// if (bindingResult.hasErrors()) {
// return "/diagnoses/edit";
// }
// diagnosisService.update(id, modelMapper.map(diagnosis,
// UpdateDiagnoseDTO.class));
// return "redirect:/diagnosis";
// }

// @GetMapping("/delete/{id}")
// public String deleteDiagnosis(@PathVariable long id) {
// diagnosisService.delete(id);
// return "redirect:/diagnosis";
// }

// private DiagnoseViewModel convertToDiagnoseViewModel(DiagnoseDTO diagnoseDTO)
// {
// return modelMapper.map(diagnoseDTO, DiagnoseViewModel.class);
// }
// }
