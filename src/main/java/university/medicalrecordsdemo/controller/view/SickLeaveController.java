// package com.example.Medical.Records.v10.data.view.controller;

// import
// com.example.Medical.Records.v10.data.view.model.appointments.UpdateAppointmentAndSickLeaveAndTreatmentViewModel;
// import
// com.example.Medical.Records.v10.data.view.model.sickLeaves.CreateSickLeaveViewModel;
// import
// com.example.Medical.Records.v10.data.view.model.sickLeaves.SickLeaveViewModel;
// import
// com.example.Medical.Records.v10.data.view.model.sickLeaves.UpdateSickLeaveViewModel;
// import com.example.Medical.Records.v10.dto.sickLeave.CreateSickLeaveDTO;
// import com.example.Medical.Records.v10.dto.sickLeave.SickLeaveDTO;
// import com.example.Medical.Records.v10.dto.sickLeave.UpdateSickLeaveDTO;
// import com.example.Medical.Records.v10.service.sickLeave.SickLeaveService;
// import lombok.AllArgsConstructor;
// import org.modelmapper.ModelMapper;
// import org.springframework.data.domain.Page;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.validation.BindingResult;
// import org.springframework.web.bind.annotation.*;

// import javax.validation.Valid;
// import java.awt.print.Pageable;
// import java.util.List;
// import java.util.Optional;
// import java.util.stream.Collectors;

// @Controller
// @AllArgsConstructor
// @RequestMapping("/sick-leaves")
// public class SickLeaveController {
// private SickLeaveService sickLeaveService;
// private ModelMapper modelMapper;

// @GetMapping
// public String getSickLeaves(Model model) {
// final List<SickLeaveViewModel> sickLeaves = sickLeaveService.findAll()
// .stream()
// .map(this::convertToSickLeaveViewModel)
// .collect(Collectors.toList());
// model.addAttribute("sickLeaves", sickLeaves);
// return "sick-leaves/all";
// }

// @GetMapping("/{id}")
// public String getSickLeaveById(Model model, @PathVariable Long id) {
// model.addAttribute("sickLeave",
// modelMapper.map(sickLeaveService.findById(id),
// UpdateSickLeaveViewModel.class));
// return "/sick-leaves/view";
// }

// @GetMapping("/create")
// public String createSickLeave(Model model) {
// model.addAttribute("sickLeave", new CreateSickLeaveViewModel());
// return "/sick-leaves/create";
// }

// @PostMapping("/create")
// public String createSickLeave(@Valid @ModelAttribute("sickLeave")
// CreateSickLeaveViewModel sickLeave, BindingResult bindingResult) {
// if (bindingResult.hasErrors()) {
// return "/sick-leaves/create";
// }
// sickLeaveService.create(modelMapper.map(sickLeave,
// CreateSickLeaveDTO.class));
// return "redirect:/sick-leaves";
// }

// @GetMapping("/update/{id}")
// public String editSickLeave(Model model, @PathVariable Long id) {
// model.addAttribute("sickLeave",
// modelMapper.map(sickLeaveService.findById(id),
// UpdateSickLeaveViewModel.class));
// return "/sick-leaves/edit";
// }

// @PostMapping("/update/{id}")
// public String editSickLeave(@PathVariable long id,@Valid
// @ModelAttribute("sickLeave") UpdateSickLeaveViewModel sickLeave,
// BindingResult bindingResult) {
// if (bindingResult.hasErrors()) {
// return "/sick-leaves/edit";
// }
// sickLeaveService.update(id, modelMapper.map(sickLeave,
// UpdateSickLeaveDTO.class));
// return "redirect:/sick-leaves";
// }

// @GetMapping("/delete/{id}")
// public String deleteSickLeave(@PathVariable long id) {
// sickLeaveService.delete(id);
// return "redirect:/sick-leaves";
// }

// private SickLeaveViewModel convertToSickLeaveViewModel(SickLeaveDTO
// sickLeaveDTO) {
// return modelMapper.map(sickLeaveDTO, SickLeaveViewModel.class);
// }
// }
