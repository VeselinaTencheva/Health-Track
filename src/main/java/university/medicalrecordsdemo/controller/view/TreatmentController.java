// package com.example.Medical.Records.v10.data.view.controller;

// import com.example.Medical.Records.v10.data.entity.Treatment;
// import
// com.example.Medical.Records.v10.data.view.model.sickLeaves.SickLeaveViewModel;
// import
// com.example.Medical.Records.v10.data.view.model.sickLeaves.UpdateSickLeaveViewModel;
// import
// com.example.Medical.Records.v10.data.view.model.treatments.CreateTreatmentViewModel;
// import
// com.example.Medical.Records.v10.data.view.model.treatments.TreatmentViewModel;
// import
// com.example.Medical.Records.v10.data.view.model.treatments.UpdateTreatmentViewModel;
// import com.example.Medical.Records.v10.dto.sickLeave.SickLeaveDTO;
// import com.example.Medical.Records.v10.dto.treatment.CreateTreatmentDTO;
// import com.example.Medical.Records.v10.dto.treatment.TreatmentDTO;
// import com.example.Medical.Records.v10.dto.treatment.UpdateTreatmentDTO;
// import com.example.Medical.Records.v10.service.treatment.TreatmentService;
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
// @RequestMapping("/treatments")
// public class TreatmentController {

// private TreatmentService treatmentService;
// private ModelMapper modelMapper;

// @GetMapping
// public String getTreatments(Model model) {
// final List<TreatmentViewModel> treatments = treatmentService.findAll()
// .stream()
// .map(this::convertToTreatmentViewModel)
// .collect(Collectors.toList());
// model.addAttribute("treatments", treatments);
// return "treatments/all";
// }

// @GetMapping("/{id}")
// public String getTreatmentById(Model model, @PathVariable Long id) {
// model.addAttribute("treatment",
// modelMapper.map(treatmentService.findById(id),
// UpdateTreatmentViewModel.class));
// return "/treatments/view";
// }

// @GetMapping("/create")
// public String createTreatment(Model model) {
// model.addAttribute("treatment", new CreateTreatmentViewModel());
// return "/treatments/create";
// }

// @PostMapping("/create")
// public String createTreatment(@Valid @ModelAttribute("treatment")
// CreateTreatmentViewModel treatment, BindingResult bindingResult) {
// if (bindingResult.hasErrors()) {
// return "/treatments/create";
// }
// treatmentService.create(modelMapper.map(treatment,
// CreateTreatmentDTO.class));
// return "redirect:/treatments";
// }

// @GetMapping("/update/{id}")
// public String editTreatment(Model model, @PathVariable Long id) {
// model.addAttribute("treatment",
// modelMapper.map(treatmentService.findById(id),
// UpdateTreatmentViewModel.class));
// return "/treatments/edit";
// }

// @PostMapping("/update/{id}")
// public String editTreatment(@PathVariable long id, @Valid
// @ModelAttribute("treatment") UpdateTreatmentViewModel treatment,
// BindingResult bindingResult) {
// if (bindingResult.hasErrors()) {
// return "/treatments/edit";
// }
// treatmentService.update(id, modelMapper.map(treatment,
// UpdateTreatmentDTO.class));
// return "redirect:/treatments";
// }

// @GetMapping("/delete/{id}")
// public String deleteTreatment(@PathVariable long id) {
// treatmentService.delete(id);
// return "redirect:/treatments";
// }

// private TreatmentViewModel convertToTreatmentViewModel(TreatmentDTO
// treatmentDTO) {
// return modelMapper.map(treatmentDTO, TreatmentViewModel.class);
// }
// }
