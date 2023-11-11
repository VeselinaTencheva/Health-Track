// package com.example.Medical.Records.v10.data.view.controller;

// import com.example.Medical.Records.v10.data.entity.DepartmentType;
// import com.example.Medical.Records.v10.data.entity.physicians.Physician;
// import
// com.example.Medical.Records.v10.data.view.model.patients.PatientViewModel;
// import
// com.example.Medical.Records.v10.data.view.model.physicians.CreatePhysicianAndGPViewModel;
// import
// com.example.Medical.Records.v10.data.view.model.physicians.PhysiciansViewModel;
// import
// com.example.Medical.Records.v10.data.view.model.physicians.UpdatePhysicianAndGPViewModel;
// import com.example.Medical.Records.v10.dto.physician.CreatePhysicianDTO;
// import com.example.Medical.Records.v10.dto.physician.PhysicianDTO;
// import com.example.Medical.Records.v10.dto.physician.UpdatePhysicianDTO;
// import
// com.example.Medical.Records.v10.service.generalPractitioner.GeneralPractitionerService;
// import com.example.Medical.Records.v10.service.physician.PhysicianService;
// import lombok.AllArgsConstructor;
// import org.modelmapper.ModelMapper;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.validation.BindingResult;
// import org.springframework.web.bind.annotation.*;

// import javax.validation.Valid;
// import java.util.List;
// import java.util.Optional;
// import java.util.stream.Collectors;

// @Controller
// @AllArgsConstructor
// @RequestMapping("/physicians")
// public class PhysicianController {
// private PhysicianService physicianService;

// private GeneralPractitionerService generalPractitionerService;
// private ModelMapper modelMapper;

// @GetMapping
// public String getPhysicians(Model model) {
// final List<PhysiciansViewModel> physicians = physicianService.findAll()
// .stream()
// .map(this::convertToPhysiciansViewModel)
// .collect(Collectors.toList());
// model.addAttribute("physicians", physicians);
// return "physicians/all";
// }

// @GetMapping("/{id}")
// public String getPhysicianById(Model model, @PathVariable Long id) {
// PhysiciansViewModel physician =
// convertToPhysiciansViewModel(physicianService.findById(id));
// if (physician.isGP()) {
// model.addAttribute("physician",
// modelMapper.map(generalPractitionerService.findById(id),
// UpdatePhysicianAndGPViewModel.class));
// } else {
// model.addAttribute("physician",
// modelMapper.map(physicianService.findById(id),
// UpdatePhysicianAndGPViewModel.class));
// }
// model.addAttribute("departments", DepartmentType.values());
// return "physicians/view";
// }

// @GetMapping("/create")
// public String showCreatePhysicianForm(Model model) {
// model.addAttribute("departments", DepartmentType.values());
// model.addAttribute("physician", new CreatePhysicianAndGPViewModel());
// return "/physicians/create";
// }

// @PostMapping("/create")
// public String createPhysician(Model model,
// @Valid @ModelAttribute("physician") CreatePhysicianAndGPViewModel physician,
// BindingResult bindingResult) {
// if (bindingResult.hasErrors()) {
// model.addAttribute("departments", DepartmentType.values());
// return "/physicians/create";
// }
// CreatePhysicianDTO createPhysicianDTO = modelMapper.map(physician,
// CreatePhysicianDTO.class);
// physicianService.create(createPhysicianDTO);
// return "redirect:/physicians";
// }

// @GetMapping("/update/{id}")
// public String showEditPhysicianForm(Model model, @PathVariable Long id) {
// boolean isGp = true;
// try {
// generalPractitionerService.findById(id);
// } catch (Exception e) {
// isGp = false;
// }
// if (isGp) {
// model.addAttribute("physician",
// modelMapper.map(generalPractitionerService.findById(id),
// UpdatePhysicianAndGPViewModel.class));
// } else {
// model.addAttribute("physician",
// modelMapper.map(physicianService.findById(id),
// UpdatePhysicianAndGPViewModel.class));
// }
// model.addAttribute("departments", DepartmentType.values());
// return "/physicians/edit";
// }

// @PostMapping("/update/{id}")
// public String updatePhysicians(@PathVariable long id,
// @Valid @ModelAttribute("physician") UpdatePhysicianAndGPViewModel physician,
// BindingResult bindingResult) {
// if (bindingResult.hasErrors()) {
// return "/physicians/edit";
// }
// physicianService.update(id, modelMapper.map(physician,
// UpdatePhysicianDTO.class));
// return "redirect:/physicians";
// }

// @GetMapping("/delete/{id}")
// public String deletePhysician(@PathVariable long id) {
// physicianService.delete(id);
// return "redirect:/physicians";
// }

// private PhysiciansViewModel convertToPhysiciansViewModel(PhysicianDTO
// physicianDTO) {
// return modelMapper.map(physicianDTO, PhysiciansViewModel.class);
// }
// }
